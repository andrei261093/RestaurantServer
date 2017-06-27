package SocketServer;

import staticUtils.UtilStaticVariables;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by andreiiorga on 15/06/2017.
 */


public class ClientHandler implements Runnable {
    BufferedReader reader;

    Socket sock;
    PrintWriter client;
    TCPServer tcpServer;

    public ClientHandler(Socket clientSocket, PrintWriter user, TCPServer tcpServer) {
        client = user;
        this.tcpServer = tcpServer;
        try {
            sock = clientSocket;
            InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(isReader);
        } catch (Exception ex) {
            tcpServer.log("Unexpected error...", UtilStaticVariables.LEVEL_ERROR);
        }

    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {

                String[] msg = message.split(":");
                if(msg[2].equals("Disconnect")){
                    sock.close();
                    tcpServer.setKitchenConnected(false);
                }else{
                    tcpServer.log("From KITCHEN: " + msg[1]);
                    tcpServer.sendToTaskAssigner(message);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            tcpServer.log("Kitchen has disconnected!", UtilStaticVariables.LEVEL_WARNING);
            tcpServer.setKitchenConnected(false);
            tcpServer.clientOutputStreams.remove(client);
        }
    }
}

