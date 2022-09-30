/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Korisnik
 */
public class Receiver {
    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    public Object receive() throws Exception{
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ERROR receiving object!\n" + ex.getMessage());
        }
        
    }
}
