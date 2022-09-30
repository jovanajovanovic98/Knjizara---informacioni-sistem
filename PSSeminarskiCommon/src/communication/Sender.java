/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Korisnik
 */
public class Sender {
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    public void send(Object object) throws Exception {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ERROR sending object!\n" + ex.getMessage());
        }

    }
}
