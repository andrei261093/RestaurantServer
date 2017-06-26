package Controllers;

import SocketServer.TCPServer;
import model.TableSession;
import org.json.JSONArray;
import org.json.JSONObject;
import repositories.MotherOfRepositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        orderForKitchen.put("products", toKitchenProducts);
        orderForWaiter.put("tableNo", order.getString("tableNo"));
        orderForWaiter.put("products", toWaiterProducts);

        if(orderForKitchen.getJSONArray("products").length() != 0){
            sendToKitchen(orderForKitchen);
        }

        if(orderForWaiter.getJSONArray("products").length() != 0){
            sendToWaiter(orderForWaiter);
        }
    }

    private void sendToKitchen(JSONObject orderForKitchen) {
        mainController.sendToKitchen(orderForKitchen);
    }

    private void sendToWaiter(JSONObject orderForWaiter) {
        mainController.log("Order for table " + orderForWaiter.getString("tableNo") + " sent to waiter");
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
