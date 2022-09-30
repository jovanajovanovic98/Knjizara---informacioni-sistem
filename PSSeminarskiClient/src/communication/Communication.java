/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domain.Book;
import domain.BookGenre;
import domain.Bookstore;
import domain.Copy;
import domain.Order;
import domain.PurchaseOrderItem;
import domain.Receipt;
import domain.StatusPurchaseOrder;
import domain.User;
import domain.Vat;
import form.PurchaseOrderForm;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class Communication {
    Socket socket;
    Sender sender;
    Receiver receiver;
    private static Communication instance;
    //ThreadClosedSocket thread;
    private Communication() throws Exception {
        socket = new Socket("localhost", 9000);
        //thread = new ThreadClosedSocket(socket);
        //startThread();
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public User login(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request request = new Request(Operation.LOGIN, user);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (User) response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public void addBookstore(Bookstore b) throws Exception {
        Request request = new Request(Operation.ADD_BOOKSTORE, b);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Bookstore res = (Bookstore) response.getResult();
            b.setId(res.getId());
        } else {
            throw response.getException();
        }
    }
    
    public List<Bookstore> getAllBookstores() throws Exception {
        Request request = new Request(Operation.GET_ALL_BOOKSTORE, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Bookstore>) response.getResult();
        } else {
            throw response.getException();
        }
    }
    public void deleteBookstore(Bookstore bookstore) throws Exception {
        Request request = new Request(Operation.DELETE_BOOKSTORE, bookstore);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }
    
    public void editBookstore(Bookstore bookstore) throws Exception {
        Request request = new Request(Operation.EDIT_BOOKSTORE, bookstore);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }
   public void addBook(Book book) throws Exception {
        Request request = new Request(Operation.ADD_BOOK, book);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Book res = (Book) response.getResult();
            book.setId(res.getBookID());
        } else {
            throw response.getException();
        }
    }
   
   public List<Book> getAllBooks() throws Exception {
        Request request = new Request(Operation.GET_ALL_BOOK, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Book>) response.getResult();
        } else {
            throw response.getException();
        }
    }
   
   public void deleteBook(Book book) throws Exception {
        Request request = new Request(Operation.DELETE_BOOK,book);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }
   
   public void editBook(Book book) throws Exception {
        Request request = new Request(Operation.EDIT_BOOK, book);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }
   
   public Vat getVAT() throws Exception {
        Request request = new Request(Operation.GET_VAT, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Vat) response.getResult();
        } else {
            throw response.getException();
        }
    }
   
   public List<BookGenre> getAllBookGenres() throws Exception {
        Request request = new Request(Operation.GET_ALL_GENRES_OF_BOOKS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<BookGenre>) response.getResult();
        } else {
            throw response.getException();
        }
    }
   public void addReceipt(Receipt receipt) throws Exception {
        Request request = new Request(Operation.ADD_RECEIPT, receipt);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Receipt res = (Receipt) response.getResult();
            receipt.setId(res.getReceiverID());
        } else {
            throw response.getException();
        }
    }

    public void deleteReceipt(Receipt receipt) throws Exception{
        Request request = new Request(Operation.DELETE_RECEIPT, receipt);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }

    public List<Receipt> getAllReceipts() throws Exception{
        Request request = new Request(Operation.GET_ALL_RECEIPTS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Receipt>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void editReceipt(Receipt receipt) throws Exception{
        Request request = new Request(Operation.EDIT_RECEIPT, receipt);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }

    public List<StatusPurchaseOrder> getAllStatuses() throws Exception {
        Request request = new Request(Operation.GET_ALL_STATUSES, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<StatusPurchaseOrder>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Copy> getAllCopies(Book book) throws Exception {
        Request request = new Request(Operation.GET_ALL_COPIES, book);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Copy>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void addOrder(Order order) throws Exception {
        Request request = new Request(Operation.ADD_ORDER, order);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Order res = (Order) response.getResult();
            order.setId(res.getOrderID());
        } else {
            throw response.getException();
        }
    }

    public void deleteOrder(Order order) throws Exception {
        Request request = new Request(Operation.DELETE_ORDER, order);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }

    public List<Order> getAllOrders() throws Exception {
        Request request = new Request(Operation.GET_ALL_ORDERS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Order>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void editOrder(Order order) throws Exception {
        Request request = new Request(Operation.EDIT_ORDER, order);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }

    public void editCopy(Copy copy) throws Exception {
        Request request = new Request(Operation.EDIT_COPY, copy);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            
        } else {
            throw response.getException();
        }
    }
   
}
