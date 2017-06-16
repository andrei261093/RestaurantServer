package Controllers;

import SocketServer.TCPServer;
import WebServices.RestServer;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Category;
import repositories.MotherOfRepositories;
import staticUtils.UtilStaticVariables;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class MainController {

    @FXML
    TextArea logArea;

    @FXML
    Label ipLabel;

    @FXML
    Button startStopServerBtn;

    @FXML
    TextField restPortInput;

    @FXML
    TextField socketPortInput;

    private Boolean isServerRunning = false;
    private RestServer restServer;
    private MotherOfRepositories motherOfRepositories;
    private TCPServer tcpServer;
    private Thread myThread;

    @FXML
    public void initialize() {
        //updateIP();

        if (!isServerRunning) {
            startStopServerBtn.setText("Start Server");
        } else {
            startStopServerBtn.setText("Stop Server");
        }
        restPortInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    restPortInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        socketPortInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    restPortInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        motherOfRepositories = new MotherOfRepositories();

        /*Category mancare = new Category("mancare");
        Category bautura = new Category("bautura");

        motherOfRepositories.getCategoryRepository().save(mancare);
        motherOfRepositories.getCategoryRepository().save(bautura);*/

        restServer = new RestServer(this, motherOfRepositories);
        tcpServer = new TCPServer(this);
        myThread = new Thread(tcpServer);
        myThread.start();

    }

    public void startStopServer(ActionEvent actionEvent) {
        if (!isServerRunning) {
            UtilStaticVariables.REST_SERVER_PORT = Integer.parseInt(restPortInput.getText());
            UtilStaticVariables.SOCKET_SERVER_PORT = Integer.parseInt(socketPortInput.getText());
            updateIP();
            restServer.startServer();
            startStopServerBtn.setText("Stop Server");
            isServerRunning = true;
        } else {
            try {
                restServer.stopServer();
            } catch (IOException e) {
                log(e.getMessage());
            }
            startStopServerBtn.setText("Start Server");
            isServerRunning = false;
            ipLabel.setText("Server's IP: NONE");
        }

    }

    public void log(String message) {
        logArea.appendText(message + "\n");
    }

    public void clearConsole(ActionEvent actionEvent) {
        logArea.clear();
    }

    public void updateIP(ActionEvent actionEvent) {
        updateIP();
    }

    public void updateIP() {
        String ip = "Server's IP: ";
        try {
            ip += Inet4Address.getLocalHost().getHostAddress() + ":" + UtilStaticVariables.REST_SERVER_PORT;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ip += "unknown";
        }
        ipLabel.setText(ip);
    }

}
