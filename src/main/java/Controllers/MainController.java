package Controllers;

import SocketServer.TCPServer;
import WebServices.RestServer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.TableSession;
import org.json.JSONObject;
import repositories.MotherOfRepositories;
import staticUtils.UtilStaticVariables;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

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

    @FXML
    Label tcpIpLabel;

    @FXML
    Label kitchenStatusLabel;

    @FXML
    Label systemStatusLabel;

    private Boolean isServerRunning = false;
    private RestServer restServer;
    private MotherOfRepositories motherOfRepositories;
    private TCPServer tcpServer;
    private Thread myThread;
    private TaskAssigner taskAssigner;

    @FXML
    public void initialize() {
        //updateIP();

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
                    socketPortInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        motherOfRepositories = new MotherOfRepositories();
     //   motherOfRepositories.seed();
        taskAssigner = new TaskAssigner(this, motherOfRepositories);
        restServer = new RestServer(this, motherOfRepositories, taskAssigner);
    }

    public void startStopServer(ActionEvent actionEvent) {
        if (!isServerRunning) {
            //update ports
            UtilStaticVariables.REST_SERVER_PORT = Integer.parseInt(restPortInput.getText());
            UtilStaticVariables.SOCKET_SERVER_PORT = Integer.parseInt(socketPortInput.getText());

            //rest server
            restServer.startServer();

            //socket server
            tcpServer = new TCPServer(this, taskAssigner);
            myThread = new Thread(tcpServer);
            myThread.start();

            //update ui
            startStopServerBtn.setText("Stop Server");
            isServerRunning = true;
            updateIP();
            restPortInput.setDisable(true);
            socketPortInput.setDisable(true);
            systemStatusLabel.setText("Online!");
            systemStatusLabel.setTextFill(Color.GREEN);
        } else {
            try {
                restServer.stopServer();
            } catch (IOException e) {
                log("Rest Server: Server forced stopped!", UtilStaticVariables.LEVEL_WARNING);
            }
            try {
                tcpServer.closeServer();
            } catch (IOException e) {
                System.out.println("TCP Server error while closing!");
            }

            //update GUI
            startStopServerBtn.setText("Start Server");
            isServerRunning = false;
            ipLabel.setText("Rest Server Address: NONE");
            tcpIpLabel.setText("TCP Server Address: NONE");
            restPortInput.setDisable(false);
            socketPortInput.setDisable(false);
            setKitchenConnected(false);
            systemStatusLabel.setText("Offline!");
            systemStatusLabel.setTextFill(Color.RED);
        }

    }

    public void log(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        logArea.appendText(UtilStaticVariables.LEVEL_LOG + " " + dtf.format(now) + ":    " + message + "\n");
    }

    public void log(String message, String level) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        logArea.appendText(level + " " + dtf.format(now) + ":    " + message + "\n");
    }

    public void clearConsole(ActionEvent actionEvent) {
        logArea.clear();
    }

    public void updateIP(ActionEvent actionEvent) {
        updateIP();
    }

    public void updateIP() {
        if (isServerRunning) {
            String ip = "";
            try {
                ip += Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                ip += "unknown";
            }
            ipLabel.setText("Rest Server Address: " + ip + ":" + UtilStaticVariables.REST_SERVER_PORT);
            tcpIpLabel.setText("TCP Server Address: " + ip + ":" + UtilStaticVariables.SOCKET_SERVER_PORT);
        } else {
            ipLabel.setText("Rest Server is not running");
            tcpIpLabel.setText("TCP Server is not running");
        }

    }

    public void setKitchenConnected(Boolean ok) {
        if (ok) {
            kitchenStatusLabel.setText("Online!");
            kitchenStatusLabel.setTextFill(Color.GREEN);
        } else {
            kitchenStatusLabel.setText("Offline!");
            kitchenStatusLabel.setTextFill(Color.RED);
        }
    }

    public void sendToKitchen(JSONObject order) {
        tcpServer.tellEveryone(order.toString());
        log("FinalOrder for table " + order.getString("tableNo") + " sent to kitchen");
    }

    public void showTableSessions(ActionEvent actionEvent) {
            Parent root;
            try {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/tableSessions.fxml"));
                Stage primaryStage = new Stage();
                Parent root2 = loader.load();
                primaryStage.setTitle("Licenta Andrei Iorga 2017");
                primaryStage.setScene(new Scene(root2));
                primaryStage.setResizable(false);
                TableSessionController tableSessionController = loader.getController();
                tableSessionController.setTableSessions(taskAssigner.getTables());
                primaryStage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}
