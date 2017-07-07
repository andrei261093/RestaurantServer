package WebServices;

/**
 * Created by andreiiorga on 14/06/2017.
 */

import Controllers.MainController;
import Controllers.TaskAssigner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import model.Product;
import model.RestaurantTable;
import model.Task;
import model.Waiter;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.json.JSONObject;
import repositories.MotherOfRepositories;
import spark.Request;
import spark.Response;
import staticUtils.UtilStaticVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static spark.Spark.*;

public class RestServer {

    private MainController mainController;
    private MotherOfRepositories motherOfRepositories;
    private Gson gson;
    private TaskAssigner taskAssigner;

    public RestServer(MainController mainController, MotherOfRepositories motherOfRepositories, TaskAssigner taskAssigner) {
        this.mainController = mainController;
        this.motherOfRepositories = motherOfRepositories;
        this.gson = new Gson();
        this.taskAssigner = taskAssigner;
    }

    public void startServer() {
        port(UtilStaticVariables.REST_SERVER_PORT);

        //routes
        get("/helloworld", (request, response) -> {
            mainController.log("Route /helloworld has been accessed");
            return "Hello World!";
        });

        get("/hello", RestServer::hello);

        get("/getTable/:name", (request, response) -> {
            RestaurantTable restaurantTable = motherOfRepositories.getRestaurantTableRepository().getByName(request.params(":name"));
            mainController.log("GET: /getTable/" + request.params(":name"));
            //mainController.log(gson.toJson(restaurantTable));
            return gson.toJson(restaurantTable);
        });

        get("/getTasks/:zone", (request, response) -> {
            List<Task> tasks = motherOfRepositories.getTaskRepository().getByZone(request.params(":zone"));
            Collections.reverse(tasks);
            return gson.toJson(tasks);
        });

        get("/callWaiter/:tableId", (request, response) -> {
            RestaurantTable table = motherOfRepositories.getRestaurantTableRepository().getByName(request.params(":tableId"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableNo", table.getTableNo());
            jsonObject.put("tableZone", table.getZone() + "");

            taskAssigner.sendToWaiter(jsonObject, UtilStaticVariables.TASK_GO_TO_TABLE);

            return 200;
        });

        get("/getCategories", (request, response) -> {
            String json = gson.toJson(motherOfRepositories.getCategoryRepository().findAll());
            return json;
        });

        get("/getProducts", (request, response) -> {
            //   mainController.log("GET: /getProducts");
            String json = null;
            try{
                List<Product> products = motherOfRepositories.getProductRepository().findAll();
                json = gson.toJson(products);
            }catch (Exception e){
                e.printStackTrace();
            }
            return json;
        });

        post("/checkOrder", (request, response) -> {
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeTo(request.body(), params, "UTF-8");
            String order = params.getString("order");
            JSONObject jsonObject = new JSONObject(order);

            //  mainController.log("POST: /checkOrder");
            taskAssigner.checkOrder(jsonObject);

            return "200";
        });

        post("/markAsDone/:id", (request, response) -> {
            String a = request.params(":id");
            Task task = null;
            try {
                int id = Integer.parseInt(request.params(":id"));
                task = motherOfRepositories.getTaskRepository().findOne(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (task.getDone()) {
                task.setDone(false);
            } else {
                task.setDone(true);
            }
            motherOfRepositories.getTaskRepository().update(task);

            return 200;
        });

        post("/newOrder", (request, response) -> {
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeTo(request.body(), params, "UTF-8");
            String order = params.getString("order");
            JSONObject jsonObject = new JSONObject(order);

            // mainController.log("POST: /newOrder");
            taskAssigner.newOrder(jsonObject);

            return "200";
        });

        post("/registerToken", (request, response) -> {
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeTo(request.body(), params, "UTF-8");

            try {
                int id = Integer.parseInt(params.getString("id"));
                Waiter waiter = motherOfRepositories.getWaiterRepository().findOne(id);
                waiter.setToken(params.getString("token"));

                motherOfRepositories.getWaiterRepository().update(waiter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "200";
        });

        get("/getWaiter/:username", (request, response) -> {
            Waiter waiter = motherOfRepositories.getWaiterRepository().getByName(request.params(":username"));
            return gson.toJson(waiter);
        });

        //stop server
        after((request, response) -> {
            if (response.raw().getStatus() == 404) {
                // here run the code that will report the error e.g.
                mainController.log("An error has occurred!!", UtilStaticVariables.LEVEL_ERROR);
            }
        });

        get("/stopserver", (request, response) -> {
            stop();
            return "sf";
        });

        mainController.log("Server is up and running...");
    }

    public void stopServer() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:" + UtilStaticVariables.REST_SERVER_PORT + "/stopserver");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        Platform.runLater(() -> {
            mainController.log("Rest Server: Server Stopped!", UtilStaticVariables.LEVEL_WARNING);
        });
    }

    private static String hello(Request request, Response response) {
        return "hello world!";
    }
}