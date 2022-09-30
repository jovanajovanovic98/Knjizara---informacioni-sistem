/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import thread.ProcessClientsRequests;
import thread.ServerGUIThread;
import thread.ServerThread;

/**
 *
 * @author Korisnik
 */
public class Server {
    ServerSocket serverSocket;
    ServerGUIThread serverGUIThread;
    List<ProcessClientsRequests> handlerList;
    boolean signal;
    
    public Server() {
        handlerList = new ArrayList<>();
        signal = true;
        
        serverGUIThread = new ServerGUIThread(this);
        serverGUIThread.start();
    }
    
   public void startServer() {
        try {
            serverSocket = new ServerSocket(9000);
            signal = true;
            new ServerThread(this).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
   
    public void handleClient(Socket socket) throws Exception {
       ProcessClientsRequests clientHandler = new ProcessClientsRequests(this, socket);
        handlerList.add(clientHandler);
        serverGUIThread.addClient(clientHandler);
        clientHandler.start();
    }
    
    public void stopClient(ProcessClientsRequests clientHandler) {
        handlerList.remove(clientHandler);
        serverGUIThread.removeClient(clientHandler);
    }
    
    
    public void loggedIn(ProcessClientsRequests clientHandler) {
        for(int i = 0; i < handlerList.size(); i++){
            if(handlerList.get(i).getTimeOfJoining() == clientHandler.getTimeOfJoining()){
                handlerList.remove(i);
                handlerList.add(i, clientHandler);
            }
        }
       serverGUIThread.editClient(clientHandler);
    }
    public void stopSocket(){
        try {
            signal = false;
            for(ProcessClientsRequests ch : handlerList){
                ch.getSocket().close();
            }
            handlerList.removeAll(handlerList);
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public List<ProcessClientsRequests> getHandlerList() {
        return handlerList;
    }

    public boolean isSignal() {
        return signal;
    }
}
