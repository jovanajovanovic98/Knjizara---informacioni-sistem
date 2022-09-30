/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import constant.Constants;
import cordinator.MainCordinator;
import domain.Bookstore;
import form.BookstoreForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.form.util.FormMode;

/**
 *
 * @author Korisnik
 */
public class BookstoreController {
    private final BookstoreForm frmBookstore;

    public BookstoreController(BookstoreForm frmBookstore) {
        this.frmBookstore = frmBookstore;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmBookstore.setLocationRelativeTo(MainCordinator.getInstance().getMainController().getFrmMain());
        prepareView(formMode);
        frmBookstore.setVisible(true);
    }

    public void closeForm() {
        frmBookstore.setVisible(false);
    }

    private void prepareView(FormMode formMode) {
        setupComponents(formMode);

    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmBookstore.setTitle("Unos knjižare:");
                frmBookstore.getBtnCancel().setEnabled(false);
                frmBookstore.getBtnEdit().setEnabled(false);
                frmBookstore.getBtnRemove().setEnabled(false);
                frmBookstore.getBtnEnableEdit().setEnabled(false);
                frmBookstore.getBtnSave().setEnabled(true);
                frmBookstore.getTxtIDBookstore().setEnabled(false);
                frmBookstore.getTxtName().setEnabled(true);
                frmBookstore.getTxtPhoneNumber().setEnabled(true);
                frmBookstore.getTxtAdress().setEnabled(true);
                frmBookstore.getTxtCity().setEnabled(true);
                frmBookstore.getTxtEmail().setEnabled(true);
                break;
            case FORM_VIEW:
                frmBookstore.setTitle("Detaljnije o knjižari:");
                frmBookstore.getBtnCancel().setEnabled(false);
                frmBookstore.getBtnEdit().setEnabled(false);
                frmBookstore.getBtnRemove().setEnabled(true);
                frmBookstore.getBtnEnableEdit().setEnabled(true);
                frmBookstore.getBtnSave().setEnabled(false);
                frmBookstore.getTxtIDBookstore().setEnabled(false);
                frmBookstore.getTxtName().setEnabled(false);
                frmBookstore.getTxtPhoneNumber().setEnabled(false);
                frmBookstore.getTxtAdress().setEnabled(false);
                frmBookstore.getTxtCity().setEnabled(false);
                frmBookstore.getTxtEmail().setEnabled(false);
                Bookstore bookstore = (Bookstore) MainCordinator.getInstance().getParam(Constants.PARAM_BOOKSTORE);
                frmBookstore.getTxtIDBookstore().setText(bookstore.getId() + "");
                frmBookstore.getTxtName().setText(bookstore.getName());
                frmBookstore.getTxtPhoneNumber().setText(bookstore.getPhoneNumber());
                frmBookstore.getTxtAdress().setText(bookstore.getAddress());
                frmBookstore.getTxtCity().setText(bookstore.getCity());
                frmBookstore.getTxtEmail().setText(bookstore.getEmail());

                break;
            case FORM_EDIT:
                frmBookstore.setTitle("Izmena knjižare:");
                frmBookstore.getBtnCancel().setEnabled(true);
                frmBookstore.getBtnEdit().setEnabled(true);
                frmBookstore.getBtnRemove().setEnabled(false);
                frmBookstore.getBtnEnableEdit().setEnabled(false);
                frmBookstore.getBtnSave().setEnabled(false);
                frmBookstore.getTxtIDBookstore().setEnabled(false);
                frmBookstore.getTxtName().setEnabled(true);
                frmBookstore.getTxtPhoneNumber().setEnabled(true);
                frmBookstore.getTxtAdress().setEnabled(true);
                frmBookstore.getTxtCity().setEnabled(true);
                frmBookstore.getTxtEmail().setEnabled(true);
                break;
        }
    }

    private Bookstore makeCustomerFromForm() {
        Bookstore bookstore = new Bookstore();
        bookstore.setId(Integer.parseInt(frmBookstore.getTxtIDBookstore().getText().trim()));
        bookstore.setName(frmBookstore.getTxtName().getText().trim());
        bookstore.setPhoneNumber(frmBookstore.getTxtPhoneNumber().getText().trim());
        bookstore.setAddress(frmBookstore.getTxtAdress().getText().trim());
        bookstore.setCity(frmBookstore.getTxtCity().getText().trim());
        bookstore.setEmail(frmBookstore.getTxtEmail().getText().trim());
        return bookstore;
    }

    private void addActionListeners() {
        frmBookstore.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                resetForm();
                try {

                    String name = frmBookstore.getTxtName().getText().trim();
                    String phoneNumber = frmBookstore.getTxtPhoneNumber().getText().trim();
                    String address = frmBookstore.getTxtAdress().getText().trim();
                    String city = frmBookstore.getTxtCity().getText().trim();
                    String email = frmBookstore.getTxtEmail().getText().trim();

                    validateForm(name, phoneNumber, address, city);
                    Bookstore bs = new Bookstore(-1, name, phoneNumber, address, city, email);
                    int answer = JOptionPane.showConfirmDialog(frmBookstore, "Da li ste sigurni da želite da sačuvate knjižaru?", "Potvrda o čuvanju knjižare", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        Communication.getInstance().addBookstore(bs);
                        frmBookstore.getTxtIDBookstore().setText(bs.getId() + "");
                        JOptionPane.showMessageDialog(frmBookstore, "Sistem je zapamtio knjižaru", "Uspešno čuvanje knjižare", JOptionPane.INFORMATION_MESSAGE);
                        frmBookstore.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmBookstore, "Sistem ne moze da zapamti knjižaru: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        );

        frmBookstore.addEnableEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }

        }
        );

        frmBookstore.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmBookstore.dispose();
            }

        }
        );

        frmBookstore.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                Bookstore bookstore = makeCustomerFromForm();
                try {
                    int answer = JOptionPane.showConfirmDialog(frmBookstore, "Da li ste sigurni da želite da obrišete knjižaru?", "Potvrda o brisanju kupca", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        Communication.getInstance().deleteBookstore(bookstore);
                        JOptionPane.showMessageDialog(frmBookstore, "Sistem je uspesno obrisao knjižaru!", "Brisanje knjižare", JOptionPane.INFORMATION_MESSAGE);
                        frmBookstore.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmBookstore, "Sistem ne moze da obrise knjižaru!\n" + ex.getMessage(), "Brisanje knjižare", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(BookstoreController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        frmBookstore.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }

            private void edit() {
                resetForm();
                Bookstore bookstore = makeCustomerFromForm();
                try {
                    int answer = JOptionPane.showConfirmDialog(frmBookstore, "Da li ste sigurni da želite da izmenite knjižaru?", "Potvrda o izmeni knjižare", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        validateForm(bookstore.getName(), bookstore.getPhoneNumber(), bookstore.getAddress(), bookstore.getCity());
                        Communication.getInstance().editBookstore(bookstore);
                        JOptionPane.showMessageDialog(frmBookstore, "Sistem je zapamtio knjižaru", "Izmena knjižare", JOptionPane.INFORMATION_MESSAGE);
                        frmBookstore.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmBookstore, "Sistem ne moze da zapamti kjnižaru:" + ex.getMessage(), "Izmena knjižare", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(BookstoreController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

    }

    private void validateForm(String name, String phoneNumber, String address, String city) throws Exception {
        String errorMessage = "";
        if (name.isEmpty()) {
            frmBookstore.getLblNameError().setText("Ime ne sme biti prazno!");
            errorMessage += "Ime ne sme biti prazno!\n";

        }
       
        if (phoneNumber.isEmpty()) {
            frmBookstore.getLblPhoneError().setText("Broj telefona ne sme biti prazan!");
            errorMessage += "Broj telefona ne sme biti prazan!\n";

        }
        if (address.trim().isEmpty()) {
            frmBookstore.getLblAddressError().setText("Adresa ne sme biti prazna!");
            errorMessage += "Adresa ne sme biti prazna!\n";
        }
        if (city.trim().isEmpty()) {
            frmBookstore.getLblCityError().setText("Mesto ne sme biti prazno!");
            errorMessage += "Mesto ne sme biti prazno!\n";
        }
        if (!errorMessage.isEmpty()) {
            throw new Exception(errorMessage);
        }
    }

    private void resetForm() {
        frmBookstore.getLblNameError().setText("");
        frmBookstore.getLblAddressError().setText("");
        frmBookstore.getLblCityError().setText("");
        frmBookstore.getLblPhoneError().setText("");
    }
}
