/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;


import java.net.ServerSocket;
import java.net.Socket;
import server.Server;

/**
 *
 * @author Korisnik
 */
public class ServerThread extends Thread{
    Server server;
    ServerSocket serverSocket;

    public ServerThread(Server server) {
        this.server = server;
        serverSocket = server.getServerSocket();
    }

    @Override
    public void run() {
        startServer();
        System.out.println("Server stopped!");
    }

    private void startServer() {
         try {
            while (server.isSignal()) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                server.handleClient(socket);
            }
        } catch (Exception ex) {
            
        }
    }
}
