package Controllers;

import SocketServer.TCPServer;
import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.FinalOrder;
import model.TableSession;
import model.Task;
import model.Waiter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import repositories.MotherOfRepositories;
import staticUtils.UtilStaticVariables;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by andreiiorga on 24/06/2017.
 */
public class TaskAssigner {
    private MainController mainController;
    private MotherOfRepositories motherOfRepositories;

    private List<TableSession> tables;


    public TaskAssigner(MainController mainController, MotherOfRepositories motherOfRepositories) {
        this.mainController = mainController;
        this.motherOfRepositories = motherOfRepositories;
        tables = new ArrayList<>();
    }

    public List<TableSession> getTables() {
        return tables;
    }

    public void setTables(List<TableSession> tables) {
        this.tables = tables;
    }

    public void checkOrder(JSONObject finalOrder) {
        mainController.log("Check for table " + finalOrder.getString("tableNo") + "! Total price: " + finalOrder.getInt("totalPrice"));
        getTableSession(finalOrder.getString("tableNo")).setRecepit(finalOrder);
        notifyCheckOrderWaiter(finalOrder);
        saveOrderToDB(finalOrder);
        removeOrder(finalOrder.getString("tableNo"));

    }

    private void notifyCheckOrderWaiter(JSONObject finalOrder) {
        sendToWaiter(finalOrder, UtilStaticVariables.TASK_TYPE_CHECK);
    }

    private void removeOrder(String tableNo) {
        for (Iterator<TableSession> iter = tables.listIterator(); iter.hasNext(); ) {
            TableSession a = iter.next();
            if (a.getTableNo().equals(tableNo)) {
                iter.remove();
            }
        }
    }

    private void saveOrderToDB(JSONObject jsonObject) {
        try{
            int tableNo = Integer.parseInt(jsonObject.getString("tableNo"));
            int waiterId = jsonObject.getJSONObject("waiter").getInt("id");
            int price = jsonObject.getInt("totalPrice");

            FinalOrder finalOrder = new FinalOrder(jsonObject.toString(), tableNo, waiterId, price);

            motherOfRepositories.getFinalOrderRepository().save(finalOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void assignOrder(JSONObject order) {
        List<JSONObject> toKitchenProducts = new ArrayList<>();
        List<JSONObject> toWaiterProducts = new ArrayList<>();


        JSONArray products = order.getJSONArray("products");
        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            if (product.getBoolean("needsPreparation")) {
                toKitchenProducts.add(product);
            } else {
                toWaiterProducts.add(product);
            }
        }

        JSONObject orderForKitchen = new JSONObject();
        JSONObject orderForWaiter = new JSONObject();

        orderForKitchen.put("tableNo", order.getString("tableNo"));
        orderForKitchen.put("tableZone", order.getString("tableZone"));
        orderForKitchen.put("products", toKitchenProducts);

        orderForWaiter.put("tableNo", order.getString("tableNo"));
        orderForWaiter.put("tableZone", order.getString("tableZone"));
        orderForWaiter.put("products", toWaiterProducts);

        if (orderForKitchen.getJSONArray("products").length() != 0) {
            sendToKitchen(orderForKitchen);
        }

        if (orderForWaiter.getJSONArray("products").length() != 0) {
            sendToWaiter(orderForWaiter, UtilStaticVariables.TASK_TYPE_ORDER);
        }
    }

    private void sendToKitchen(JSONObject orderForKitchen) {
        mainController.sendToKitchen(orderForKitchen);
    }

    public void sendToWaiter(JSONObject orderForWaiter, String taskType) {

        sendNotification(orderForWaiter);
        String zone = orderForWaiter.getString("tableZone");
        Waiter waiter = motherOfRepositories.getWaiterRepository().getByZone(zone);
        JSONObject waiterJson = new JSONObject(new Gson().toJson(waiter));
        orderForWaiter.put("waiter", waiterJson);
        orderForWaiter.put("taskType", taskType);
        Task task = new Task(orderForWaiter);
        try {
            motherOfRepositories.getTaskRepository().save(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendNotification(JSONObject orderForWaiter) {
        Waiter waiter = null;
        try {
            waiter = motherOfRepositories.getWaiterRepository().getByZone(orderForWaiter.getString("tableZone"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainController.log("Order for table " + orderForWaiter.getString("tableNo") + " assigned to " + waiter.getName());
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "key=" + UtilStaticVariables.FIREBASE_PROJECT_KEY);

        JSONObject message = new JSONObject();
        message.put("to", waiter.getToken());
        message.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("message", "New order");
        notification.put("title", "FinalOrder for Table " + orderForWaiter.getString("tableNo"));

        message.put("data", notification);

        post.setEntity(new StringEntity(message.toString(), "UTF-8"));
        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        System.out.println(message);

    }

    public void newOrder(JSONObject order) {
        if (this.tables.size() != 0) {
            for (TableSession tableSession : this.tables) {
                if (tableSession.getTableNo().equals(order.getString("tableNo"))) {
                    tableSession.addChunk(order);
                }
            }
        } else {
            TableSession newTableSession = new TableSession(order.getString("tableNo"));
            newTableSession.addChunk(order);
            this.tables.add(newTableSession);
        }
        assignOrder(order);
    }

    public TableSession getTableSession(String id) {
        for (TableSession tableSession : tables) {
            if (tableSession.getTableNo().equals(id)) {
                return tableSession;
            }
        }
        return null;
    }


}
