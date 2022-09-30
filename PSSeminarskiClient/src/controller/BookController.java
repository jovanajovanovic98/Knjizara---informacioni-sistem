/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import constant.Constants;
import cordinator.MainCordinator;
import domain.Book;
import domain.BookGenre;
import domain.Vat;
import form.BookForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import view.form.util.FormMode;

/**
 *
 * @author Korisnik
 */
public class BookController {
    private final BookForm frmBook;

    public BookController(BookForm frmBook) {
        this.frmBook = frmBook;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        try {
            frmBook.setLocationRelativeTo(MainCordinator.getInstance().getMainController().getFrmMain());
            prepareView(formMode);
            frmBook.setVisible(true);
        } catch (Exception ex) {
            frmBook.dispose();
        }

    }

    public void closeForm() {
        frmBook.dispose();
    }

    private void prepareView(FormMode formMode){
        setupComponents(formMode);
        fillCmbType();

    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmBook.setTitle("Unos knjige:");
                frmBook.getBtnCancel().setEnabled(false);
                frmBook.getBtnEdit().setEnabled(false);
                frmBook.getBtnDelete().setEnabled(false);
                frmBook.getBtnEnableEdit().setEnabled(false);
                frmBook.getBtnSave().setEnabled(true);
                frmBook.getTxtIDBook().setEnabled(false);
                frmBook.getTxtName().setEnabled(true);
                frmBook.getTxtAuthor().setEnabled(true);
                frmBook.getTxtPriceWithoutVAT().setEnabled(true);
                frmBook.getCmbGenre().setEnabled(true);
                frmBook.getTxtPurchasePrice().setEnabled(true);
                break;
            case FORM_VIEW:
                frmBook.setTitle("Detaljnije o knjizi:");
                frmBook.getBtnCancel().setEnabled(false);
                frmBook.getBtnEdit().setEnabled(false);
                frmBook.getBtnDelete().setEnabled(true);
                frmBook.getBtnEnableEdit().setEnabled(true);
                frmBook.getBtnSave().setEnabled(false);
                frmBook.getTxtIDBook().setEnabled(false);
                frmBook.getTxtName().setEnabled(false);
                frmBook.getTxtAuthor().setEnabled(false);
                frmBook.getTxtPriceWithoutVAT().setEnabled(false);
                frmBook.getCmbGenre().setEnabled(false);
                frmBook.getTxtPurchasePrice().setEnabled(false);

                Book book = (Book) MainCordinator.getInstance().getParam(Constants.PARAM_BOOK);
                frmBook.getTxtIDBook().setText(book.getBookID()+ "");
                frmBook.getTxtName().setText(book.getName());
                frmBook.getTxtAuthor().setText(book.getAuthor());
                frmBook.getCmbGenre().setSelectedItem(book.getGenre());
                frmBook.getTxtPriceWithoutVAT().setText(String.valueOf(book.getPriceWithoutVAT()));
                frmBook.getTxtPriceWithVAT().setText(String.valueOf(book.getPriceWithVAT()));
                frmBook.getTxtPurchasePrice().setText(String.valueOf(book.getPurchasePrice()));
                break;
            case FORM_EDIT:
                frmBook.setTitle("Izmena knjige:");
                frmBook.getBtnCancel().setEnabled(true);
                frmBook.getBtnEdit().setEnabled(true);
                frmBook.getBtnDelete().setEnabled(false);
                frmBook.getBtnEnableEdit().setEnabled(false);
                frmBook.getBtnSave().setEnabled(false);
                frmBook.getTxtIDBook().setEnabled(false);
                frmBook.getTxtName().setEnabled(true);
                frmBook.getTxtAuthor().setEnabled(true);
                frmBook.getTxtPriceWithoutVAT().setEnabled(true);
                frmBook.getCmbGenre().setEnabled(true);
                frmBook.getTxtPurchasePrice().setEnabled(true);
                break;
        }
    }

    private Vat getVAT() throws Exception {
        Vat vat = new Vat();
        try {
            vat = Communication.getInstance().getVAT();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        return vat;
    }

    private void fillCmbType(){
        try {
            frmBook.getCmbGenre().removeAllItems();
            List<BookGenre> genres = Communication.getInstance().getAllBookGenres();
            for (BookGenre genre : genres) {
                frmBook.getCmbGenre().addItem(genre);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmBook, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            

        }
    }

    private void addActionListeners() {
        frmBook.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                resetForm();
                try {

                    String name = frmBook.getTxtName().getText().trim();
                    String author = frmBook.getTxtAuthor().getText().trim();
                    BookGenre genre = (BookGenre) frmBook.getCmbGenre().getSelectedItem();
                    String priceMinusVAT = frmBook.getTxtPriceWithoutVAT().getText().trim();
                    validateForm(name, priceMinusVAT);
                    double priceWithoutVAT = Double.parseDouble(priceMinusVAT);
                    double purchasePrice = Double.parseDouble(frmBook.getTxtPurchasePrice().getText().trim());
                    if (priceWithoutVAT <= 0 || purchasePrice <= 0) {
                        throw new Exception("Cena mora da bude veća od nule!\n");
                    }
                    Book book = new Book();
                    book.setBookID(-1);
                    book.setName(name);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setPriceWithoutVAT(priceWithoutVAT);
                    book.setVat(getVAT());
                    book.setPriceWithVAT(book.getPriceWithoutVAT() * (1 + book.getVat().getVatPercentage()));
                    book.setPurchasePrice(purchasePrice);
                    frmBook.getTxtPriceWithVAT().setText(book.getPriceWithVAT() + "");
                    int answer = JOptionPane.showConfirmDialog(frmBook, "Da li ste sigurni da želite da izmenite knjigu?", "Potvrda o čuvanju proizvoda", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        Communication.getInstance().addBook(book);
                        frmBook.getTxtIDBook().setText(book.getBookID()+ "");
                        JOptionPane.showMessageDialog(frmBook, "Sistem je zapamtio knjigu", "Uspešno čuvanje knjige", JOptionPane.INFORMATION_MESSAGE);
                        frmBook.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmBook, "Sistem ne može da zapamti knjigu: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        frmBook.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Book book = (Book) MainCordinator.getInstance().getParam(Constants.PARAM_BOOK);
                    int answer = JOptionPane.showConfirmDialog(frmBook, "Da li ste sigurni da želite da obrišete knjigu?", "Potvrda o brisanju knjige", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        Communication.getInstance().deleteBook(book);
                        JOptionPane.showMessageDialog(frmBook, "Sistem je obrisao knjigu!", "Brisanje knjige", JOptionPane.INFORMATION_MESSAGE);
                        frmBook.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmBook, "Sistem ne moze da obrise knjigu", "Brisanje knjige", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        frmBook.addEnableEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);

            }
        });

        frmBook.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }

            private void edit() {
                resetForm();
                try {
                    String name = frmBook.getTxtName().getText().trim();
                    String author = frmBook.getTxtAuthor().getText().trim();
                    BookGenre genre = (BookGenre) frmBook.getCmbGenre().getSelectedItem();
                    String priceMinusVAT = frmBook.getTxtPriceWithoutVAT().getText().trim();
                    validateForm(name, priceMinusVAT);
                    double priceWithoutVAT = Double.parseDouble(priceMinusVAT);
                    double purchasePrice = Double.parseDouble(frmBook.getTxtPurchasePrice().getText().trim());
                    if (priceWithoutVAT <= 0 || purchasePrice <= 0) {
                        throw new Exception("Cena mora da bude veca od nule!\n");
                    }
                    Book book = new Book();
                    book.setBookID(Integer.valueOf(frmBook.getTxtIDBook().getText()));
                    book.setName(name);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setPriceWithoutVAT(priceWithoutVAT);
                    book.setVat(getVAT());
                    book.setPriceWithVAT(book.getPriceWithoutVAT() * (1 + book.getVat().getVatPercentage()));
                    book.setPurchasePrice(purchasePrice);
                    frmBook.getTxtPriceWithVAT().setText(book.getPriceWithVAT() + "");;
                    int answer = JOptionPane.showConfirmDialog(frmBook, "Da li ste sigurni da želite da izmenite knjigu?", "Potvrda o izmeni knjige", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        Communication.getInstance().editBook(book);
                        JOptionPane.showMessageDialog(frmBook, "Sistem je zapamtio knjigu", "Izmena knjige", JOptionPane.INFORMATION_MESSAGE);
                        frmBook.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmBook, "Sistem ne moze da zapamti knjigu:" + ex.getMessage(), "Izmena knjige", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        frmBook.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmBook.dispose();
            }
        });
    }

    private void resetForm() {
        frmBook.getLblNameError().setText("");
        frmBook.getLblPriceWithoutVATError().setText("");
    }

    private void validateForm(String name, String priceWithoutVAT) throws Exception {
        String errorMessage = "";
        if (name.isEmpty()) {
            frmBook.getLblNameError().setText("Naziv ne sme biti prazan!");
            errorMessage += "Naziv ne sme biti prazan!\n";

        }
        if (priceWithoutVAT.trim().isEmpty()) {
            frmBook.getLblPriceWithoutVATError().setText("Cena ne sme biti prazna!");
            errorMessage += "Cena ne sme biti prazna!\n";
        }

        if (!errorMessage.isEmpty()) {
            throw new Exception(errorMessage);
        }
    }
}
