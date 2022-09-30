/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import copies.EditCopy;
import copies.GetAllCopies;
import db.RepositoryDBGeneric;
import domain.Book;
import domain.BookGenre;
import domain.Bookstore;
import domain.Copy;
import domain.Order;
import domain.Receipt;
import domain.StatusPurchaseOrder;
import domain.User;
import domain.Vat;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;
import operation.book.AddBook;
import operation.book.DeleteBook;
import operation.book.EditBook;
import operation.book.GetAllBooks;
import operation.bookstore.AddBookstore;
import operation.bookstore.DeleteBookstore;
import operation.bookstore.EditBookstore;
import operation.bookstore.GetAllBookstores;
import operation.genres.GetAllBookGenres;
import operation.receipt.AddReceipt;
import operation.receipt.DeleteReceipt;
import operation.receipt.EditReceipt;
import operation.receipt.GetAllReceipts;
import operation.user.GetAllUsers;
import operation.vat.GetAllVat;
import operation.order.AddOrder;
import operation.order.DeleteOrder;
import operation.order.EditOrder;
import operation.order.GetAllOrders;
import repository.Repository;
import orderstatus.GetAllOrderStatuses;

/**
 *
 * @author Korisnik
 */
public class Controller {
    private static Controller instanca;
    private final Repository repositoryGeneric;

    private Controller() {
        this.repositoryGeneric = new RepositoryDBGeneric();
    }

    public static Controller getInstanca() {
        if (instanca == null) {
            instanca = new Controller();
        }
        return instanca;
    }

    public User login(User user) throws Exception {
        AbstractGenericOperation operation = new GetAllUsers();
        operation.execute(new User());
        User userChecked = ((GetAllUsers) operation).login(user);
        return userChecked;
    }
     public void addBookstore(Bookstore bs) throws Exception {
        AbstractGenericOperation operation = new AddBookstore();
        operation.execute(bs);
    }

    public List<Bookstore> getAllBookstores() throws Exception {
        AbstractGenericOperation operation = new GetAllBookstores();
        operation.execute(new Bookstore());
        ArrayList<Bookstore> list = ((GetAllBookstores) operation).getList();
        return list;
    }

    public void deleteBookstore(Bookstore bookstore) throws Exception {
        AbstractGenericOperation operation = new DeleteBookstore();
        operation.execute(bookstore);
    }

    public void editBookstore(Bookstore bookstore) throws Exception {
        AbstractGenericOperation operation = new EditBookstore();
        operation.execute(bookstore);

    }

    public Vat getVAT() throws Exception {
        AbstractGenericOperation operation = new GetAllVat();
        operation.execute(new Vat());
        Vat vat = ((GetAllVat) operation).getLastInsertedVat();
        return vat;
    }

    public List<BookGenre> getAllBookGenres() throws Exception {
        AbstractGenericOperation operation = new GetAllBookGenres();
        operation.execute(new BookGenre());
        ArrayList<BookGenre> list = ((GetAllBookGenres) operation).getList();
        return list;
    }
   
    public void addBook(Book book) throws Exception {
        AbstractGenericOperation operation = new AddBook();
        operation.execute(book);
    }

    public List<Book> getAllBooks() throws Exception {
        AbstractGenericOperation operation = new GetAllBooks();
        operation.execute(new Book());
        ArrayList<Book> list = ((GetAllBooks) operation).getList();
        return list;
    }

    public void deleteBook(Book book) throws Exception {
        AbstractGenericOperation operation = new DeleteBook();
        operation.execute(book);
    }

    public void editBook(Book book) throws Exception {
        AbstractGenericOperation operation = new EditBook();
        operation.execute(book);
    }

    public List<StatusPurchaseOrder> getAllStatuses() throws Exception {
        AbstractGenericOperation operation = new GetAllOrderStatuses();
        operation.execute(new StatusPurchaseOrder());
        ArrayList<StatusPurchaseOrder> list = ((GetAllOrderStatuses) operation).getList();
        return list;
    }

    public void addReceipt(Receipt receipt) throws Exception{
        AbstractGenericOperation operation = new AddReceipt();
        operation.execute(receipt);
    }

    public void deleteReceipt(Receipt receiptDelete) throws Exception {
        AbstractGenericOperation operation = new DeleteReceipt();
        operation.execute(receiptDelete);
    }

    public Object getAllReceipts() throws Exception {
        AbstractGenericOperation operation = new GetAllReceipts();
        operation.execute(new Receipt());
        ArrayList<Receipt> list = ((GetAllReceipts) operation).getList();
        return list;
    }

    public void editReceipt(Receipt receiptEdit) throws Exception{
        AbstractGenericOperation operation = new EditReceipt();
        operation.execute(receiptEdit);
    }

    public Object getAllCopies(Book bookCopy) throws Exception {
        AbstractGenericOperation operation = new GetAllCopies();
        operation.execute(new Copy());
        ArrayList<Copy> list = ((GetAllCopies) operation).getList(bookCopy);
        return list;
    }

    public void addOrder(Order order) throws Exception {
        AbstractGenericOperation operation = new AddOrder();
        operation.execute(order);
    }

    public void deleteOrder(Order orderDelete) throws Exception {
        AbstractGenericOperation operation = new DeleteOrder();
        operation.execute(orderDelete);
    }

    public Object getAllOrders() throws Exception {
        AbstractGenericOperation operation = new GetAllOrders();
        operation.execute(new Order());
        ArrayList<Order> list = ((GetAllOrders) operation).getList();
        return list;
    }

    public void editOrder(Order orderEdit) throws Exception {
        AbstractGenericOperation operation = new EditOrder();
        operation.execute(orderEdit);
    }

    public void editCopy(Copy copyEdit) throws Exception {
        AbstractGenericOperation operation = new EditCopy();
        operation.execute(copyEdit);
    }

}
