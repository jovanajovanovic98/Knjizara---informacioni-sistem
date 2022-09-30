/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cordinator;

import controller.BookController;
import controller.BookViewAllController;
import controller.BookstoreController;
import controller.BookstoreViewAllController;
import controller.LoginController;
import controller.MainController;
import controller.OrderController;
import controller.OrderViewController;
import controller.ReceiptController;
import controller.ReceiptViewAllController;
import domain.Bookstore;
import form.BookForm;
import form.BookstoreForm;
import form.LoginForm;
import form.MainForm;
import form.PurchaseOrderForm;
import form.ReceiptForm;
import form.ViewBookForm;
import form.ViewBookstoreForm;
import form.ViewOrderForm;
import form.ViewReceiptsForm;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import view.form.util.FormMode;

/**
 *
 * @author Korisnik
 */
public class MainCordinator {
    private static MainCordinator instance;
    private final MainController mainController;
    private final Map<String, Object> params;
    
    private MainCordinator() {
        mainController = new MainController(new MainForm());
        params = new HashMap<>();
    }
    
    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }
    
     public void openLoginForm() {
        LoginController loginController = new LoginController(new LoginForm());
        loginController.openForm();
    }
     
    public Object getParam(String name) {
        return params.get(name);
    }

    public void addParam(String name, Object key) {
        params.put(name, key);
    }
  
    public MainController getMainController() {
        return mainController;
    }
    
     public void openMainForm() {
        mainController.openForm();
    }
     
     public void openAddNewBookstoreForm() {
        BookstoreController bookstoreController = new BookstoreController(new BookstoreForm(mainController.getFrmMain(), true));
        bookstoreController.openForm(FormMode.FORM_ADD);
    }

    public void openViewBookstoresForm() {
        ViewBookstoreForm form = new ViewBookstoreForm(mainController.getFrmMain(), true);

        BookstoreViewAllController bookstoreViewAllController = new BookstoreViewAllController(form);
        bookstoreViewAllController.openForm();
    }

    public void openBookstoreDetailsBookForm() {
        BookstoreForm bookstoreDetails = new BookstoreForm(mainController.getFrmMain(), true);
        BookstoreController bookstoreController = new BookstoreController(bookstoreDetails);
        bookstoreController.openForm(FormMode.FORM_VIEW);
    }

    public void openAddNewBookForm() {
        BookController bookController = new BookController(new BookForm(mainController.getFrmMain(), true));
        bookController.openForm(FormMode.FORM_ADD);
    }
    
    public void openViewAllBooksForm() {
        BookViewAllController bookViewAllController = new BookViewAllController(new ViewBookForm(mainController.getFrmMain(), true));
        bookViewAllController.openForm();
    }

    public void openBookDetailsBookForm() {
        BookForm bookDetails = new BookForm(mainController.getFrmMain(), true);
        BookController bookController = new BookController(bookDetails);
        bookController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openAddNewReceiptForm() {
        ReceiptForm receiptForm = new ReceiptForm(mainController.getFrmMain(), true);
        ReceiptController receiptController = new ReceiptController(receiptForm);
        receiptController.openForm(FormMode.FORM_ADD);
    }
    
     public void openAddNewOrderForm() {
        OrderController orderController = new OrderController(new PurchaseOrderForm(mainController.getFrmMain(), true));
        orderController.openForm(FormMode.FORM_ADD);
    }

    public void openViewAllOrderForm() {
        OrderViewController orderViewAllController = new OrderViewController(new ViewOrderForm(mainController.getFrmMain(), true));
        orderViewAllController.openForm(FormMode.FORM_VIEW);
    }

    public void openOrderDetailsProductForm() {
        PurchaseOrderForm orderDetails = new PurchaseOrderForm(mainController.getFrmMain(), true);
        OrderController orderController = new OrderController(orderDetails);
        orderController.openForm(FormMode.FORM_VIEW);
    }

    public void closeAllForms() {
        JOptionPane.showMessageDialog(null, "Server nije dostupan!");
        getMainController().getFrmMain().dispose();
        
    }


    public void openShowAllReceiversForm() {
         ReceiptViewAllController receiptViewAllController = new ReceiptViewAllController(new ViewReceiptsForm(mainController.getFrmMain(), true));
        receiptViewAllController.openForm(FormMode.FORM_VIEW);
    }

    public void openReceiptDetailsForm() {
        ReceiptForm receiptDetails = new ReceiptForm(mainController.getFrmMain(), true);
        ReceiptController receiptController = new ReceiptController(receiptDetails);
        receiptController.openForm(FormMode.FORM_VIEW);
    }

}
