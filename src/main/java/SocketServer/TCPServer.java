package SocketServer;

import Controllers.MainController;
import javafx.application.Platform;
import staticUtils.UtilStaticVariables;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class TCPServer implements Runnable {
    private MainController mainController;
    ArrayList clientOutputStreams;
    private volatile boolean isRunning = true;
    private Thread listener;
    private ServerSocket serverSock;
    private List<Socket> clients = new ArrayList<>();

    public TCPServer(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        clientOutputStreams = new ArrayList();

        try {
            serverSock = new ServerSocket(UtilStaticVariables.SOCKET_SERVER_PORT);

            while (isRunning) {
                Socket clientSock = serverSock.accept();
                clients.add(clientSock);
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);

                listener = new Thread(new ClientHandler(clientSock, writer, this));
                listener.start();
                setKitchenConnected(true);
                mainController.log("Kitchen has connected!");

                tellEveryone("HELLO KITCHEN \n");
            }
        } catch (Exception ex) {
            mainController.log("TCP Server: Server Stopped!", UtilStaticVariables.LEVEL_WARNING);
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                //mainController.log("Sending: " + message);
                writer.flush();

            } catch (Exception ex) {
                mainController.log("Error telling everyone.");
            }
        }
    }

    public void log(String msg) {
        mainController.log(msg);
    }

    public void log(String msg, String lvl) {
        mainController.log(msg, lvl);
    }

    public void closeServer() throws IOException {
        isRunning = false;
        serverSock.close();
        for (Socket client: clients) {
            client.close();
        }
        clients.clear();
    }

    public void setKitchenConnected(boolean b) {
        Platform.runLater(() -> {
            mainController.setKitchenConnected(b);
        });
    }
}
