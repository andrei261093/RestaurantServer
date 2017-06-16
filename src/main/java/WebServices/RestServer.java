package WebServices;

/**
 * Created by andreiiorga on 14/06/2017.
 */
import Controllers.MainController;
import com.google.gson.Gson;
import repositories.MotherOfRepositories;
import staticUtils.UtilStaticVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class RestServer {

    private MainController mainController;
    private MotherOfRepositories motherOfRepositories;
    private Gson gson;

    public RestServer(MainController mainController, MotherOfRepositories motherOfRepositories) {
        this.mainController = mainController;
        this.motherOfRepositories = motherOfRepositories;
        this.gson = new Gson();
    }

    public void startServer() {
        port(UtilStaticVariables.REST_SERVER_PORT);

        get("/helloworld", (request, response) -> {
            return "Hello World!";
        });

        get("/stopserver", (request, response) -> {
            stop();
            return  "sf";
        });

        get("/getCategories", (request, response) -> {
            mainController.log("Route /getCategories has been accessed");
            String json = gson.toJson(motherOfRepositories.getCategoryRepository().findAll());
            mainController.log(json);
            return json;
        });

        get("/getProducts", (request, response) -> {
            mainController.log("Route /getProducts has been accessed");
            String json = gson.toJson(motherOfRepositories.getProductRepository().findAll());
            mainController.log(json);
            return json;
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

        mainController.log("Server stoped.");

    }

}