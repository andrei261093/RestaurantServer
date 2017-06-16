package SocketServer;

import Controllers.MainController;
import staticUtils.UtilStaticVariables;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class TCPServer implements Runnable {
    private MainController mainController;
    ArrayList clientOutputStreams;
    ArrayList<String> users;
    private volatile boolean isRunning = true;
    private  Thread listener;

    public TCPServer(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        clientOutputStreams = new ArrayList();
        users = new ArrayList();

        try {
            ServerSocket serverSock = new ServerSocket(UtilStaticVariables.SOCKET_SERVER_PORT);

            while (isRunning) {
                Socket clientSock = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);

                listener = new Thread(new ClientHandler(clientSock, writer, this));
                listener.start();
                mainController.log("Got a connection.");
                tellEveryone("mesaj de la server");
            }
        } catch (Exception ex) {
            mainController.log("Error making a connection.");
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                mainController.log("Sending: " + message );
                writer.flush();

            } catch (Exception ex) {
                mainController.log("Error telling everyone.");
            }
        }
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        mainController.log("Before " + name + " added.");
        users.add(name);
        mainController.log("After " + name + " added.");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void log(String msg) {
        mainController.log(msg);
    }

    public void userRemove (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void kill() {
        listener.interrupt();
    }

}
