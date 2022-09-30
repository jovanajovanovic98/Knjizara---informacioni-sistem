/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.Controller;
import domain.Book;
import domain.Bookstore;
import domain.Copy;
import domain.Order;
import domain.PurchaseOrderItem;
import domain.Receipt;
import domain.User;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author Klara
 */
public class ProcessClientsRequests extends Thread {

    Server server;
    Socket socket;
    Sender sender;
    Receiver receiver;
    User user;
    Date timeOfJoining;
    boolean signal;

    public ProcessClientsRequests(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        signal = true;
    }

    @Override
    public void run() {
        timeOfJoining = new Date(System.currentTimeMillis());

        while (signal) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    switch (request.getOperation()) {
                        case LOGIN:
                            User userLogged = (User) request.getArgument();
                            user = userLogged;
                            server.loggedIn(this);
                            response.setResult(Controller.getInstanca().login(userLogged));
                            if (response.getException() == null) {
                                user = Controller.getInstanca().login(userLogged);
                            }
                            break;
                        case ADD_BOOKSTORE:
                            Bookstore bookstore = (Bookstore) request.getArgument();
                            Controller.getInstanca().addBookstore(bookstore);
                            response.setResult(bookstore);
                            break;
                        case GET_ALL_BOOKSTORE:
                            response.setResult(Controller.getInstanca().getAllBookstores());
                            break;
                        case DELETE_BOOKSTORE:
                            Bookstore bookstoreDelete = (Bookstore) request.getArgument();
                            Controller.getInstanca().deleteBookstore(bookstoreDelete);
                            break;
                        case EDIT_BOOKSTORE:
                            Bookstore bookstoreEdit = (Bookstore) request.getArgument();
                            Controller.getInstanca().editBookstore(bookstoreEdit);
                            break;
                        case GET_VAT:
                            response.setResult(Controller.getInstanca().getVAT());
                            break;
                        case GET_ALL_GENRES_OF_BOOKS:
                            response.setResult(Controller.getInstanca().getAllBookGenres());
                            break;
                        case ADD_BOOK:
                            Book book = (Book) request.getArgument();
                            Controller.getInstanca().addBook(book);
                            response.setResult(book);
                            break;
                        case GET_ALL_BOOK:
                            response.setResult(Controller.getInstanca().getAllBooks());
                            break;
                        case DELETE_BOOK:
                            Book bookDelete = (Book) request.getArgument();
                            Controller.getInstanca().deleteBook(bookDelete);
                            break;
                        case EDIT_BOOK:
                            Book bookEdit = (Book) request.getArgument();
                            Controller.getInstanca().editBook(bookEdit);
                            break;
                        case ADD_RECEIPT:
                            Receipt receipt = (Receipt) request.getArgument();
                            Controller.getInstanca().addReceipt(receipt);
                            response.setResult(receipt);
                            break;
                        case DELETE_RECEIPT:
                            Receipt receiptDelete = (Receipt) request.getArgument();
                            Controller.getInstanca().deleteReceipt(receiptDelete);
                            break;
                        case GET_ALL_RECEIPTS:
                            response.setResult(Controller.getInstanca().getAllReceipts());
                            break;
                        case EDIT_RECEIPT:
                            Receipt receiptEdit = (Receipt) request.getArgument();
                            Controller.getInstanca().editReceipt(receiptEdit);
                            break;
                        case GET_ALL_STATUSES:
                            response.setResult(Controller.getInstanca().getAllStatuses());
                            break;
                        case GET_ALL_COPIES:
                            Book bookCopy = (Book) request.getArgument();
                            response.setResult(Controller.getInstanca().getAllCopies(bookCopy));
                            break;
                        case ADD_ORDER:
                            Order order = (Order) request.getArgument();
                            Controller.getInstanca().addOrder(order);
                            response.setResult(order);
                            break;
                        case DELETE_ORDER:
                            Order orderDelete = (Order) request.getArgument();
                            Controller.getInstanca().deleteOrder(orderDelete);
                            break;
                        case GET_ALL_ORDERS:
                            response.setResult(Controller.getInstanca().getAllOrders());
                            break;
                        case EDIT_ORDER:
                            Order orderEdit = (Order) request.getArgument();
                            Controller.getInstanca().editOrder(orderEdit);
                            break;
                        case EDIT_COPY:
                            Copy copyEdit = (Copy) request.getArgument();
                            Controller.getInstanca().editCopy(copyEdit);
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.setException(ex);
                }
                sender.send(response);
            } catch (Exception ex) {
                signal = false;
                server.stopClient(this);
            }
        }
    }

    public Date getTimeOfJoining() {
        return timeOfJoining;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
