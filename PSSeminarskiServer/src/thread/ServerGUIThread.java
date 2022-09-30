/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;


import server.Server;
import server.view.controller.ServerViewController;

/**
 *
 * @author Korisnik
 */
public class ServerGUIThread extends Thread{
    Server server;
   ServerViewController controller;

    public ServerGUIThread(Server server) {
        this.server = server;
    }
    
    public void addClient(ProcessClientsRequests clientHandler) {
       controller.addClient(clientHandler);
    }
    
     public void removeClient(ProcessClientsRequests clientHandler) {
       controller.removeClient(clientHandler);
    }
     
    public void editClient(ProcessClientsRequests clientHandler){
       controller.editClient(clientHandler);
    }

   @Override
    public void run() {
        controller = new ServerViewController(server);
         try {
            while(true){
                Thread.sleep(5000);
                controller.updateView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
   }
    
}
