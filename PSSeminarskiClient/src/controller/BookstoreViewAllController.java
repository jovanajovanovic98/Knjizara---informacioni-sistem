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
import form.ViewBookstoreForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.ModelBookstore;

/**
 *
 * @author Korisnik
 */
public class BookstoreViewAllController {
    private final ViewBookstoreForm frmViewBookstores;
    List<Bookstore> bookstores;

    public BookstoreViewAllController(ViewBookstoreForm form) {
        this.frmViewBookstores = form;
        addActionListener();
    }

    public void openForm() {
        frmViewBookstores.setLocationRelativeTo(MainCordinator.getInstance().getMainController().getFrmMain());
        prepareView();
        frmViewBookstores.setVisible(true);
    }

    public void closeForm() {
        frmViewBookstores.setVisible(false);
    }

    private void addActionListener() {
        frmViewBookstores.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewBookstores.getTblBookstores().getSelectedRow();
                if (row >= 0) {
                    try {
                        Bookstore bookstore = ((ModelBookstore) frmViewBookstores.getTblBookstores().getModel()).getBookstoreAt(row);
                        MainCordinator.getInstance().addParam(Constants.PARAM_BOOKSTORE, bookstore);
                        MainCordinator.getInstance().openBookstoreDetailsBookForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmViewBookstores, "Sistem ne moze da u??ita knji??aru", "KNJI??ARA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewBookstores, "Morate izabrati knji??aru iz tabele!", "KNJI??ARA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewBookstores.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblCustomers();
            }
        });

        frmViewBookstores.getBtnAddAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewBookstoreForm();
            }
        });

        frmViewBookstores.getBtnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewBookstores.getTblBookstores().getSelectedRow();
                if (row >= 0) {
                    Bookstore bookstore = ((ModelBookstore) frmViewBookstores.getTblBookstores().getModel()).getBookstoreAt(row);
                    try {
                        int answer = JOptionPane.showConfirmDialog(frmViewBookstores, "Da li ste sigurni da ??elite da obri??ete knji??aru?", "Potvrda o brisanju knji??are", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            Communication.getInstance().deleteBookstore(bookstore);
                            JOptionPane.showMessageDialog(frmViewBookstores, "Sistem je uspesno obrisao knji??aru!", "Brisanje knji??are", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frmViewBookstores, "Sistem ne moze da izbrise knjizaru", "KNJI??ARA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewBookstores, "Morate izabrati knji??aru iz tabele!", "KNJI??ARA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewBookstores.getTxtNameSearchAddKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
        
        frmViewBookstores.getTxtEmailSearchAddKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                search();

            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

        });
    }

    private void search() {
        String nameSearch = frmViewBookstores.getTxtNameSearch().getText().trim();
        String emailSearch = frmViewBookstores.getTxtEmailSearch().getText().trim();
        ModelBookstore model = (ModelBookstore) frmViewBookstores.getTblBookstores().getModel();
        ArrayList<Bookstore> searchedBookstores = new ArrayList<>();
        for (Bookstore bookstore : bookstores) {
            if (bookstore.getName().contains(nameSearch) && bookstore.getEmail().contains(emailSearch)) {
                searchedBookstores.add(bookstore);
            }
        }
        model.setBookstore(searchedBookstores);
        model.fireTableDataChanged();
        if (searchedBookstores.isEmpty()) {
            JOptionPane.showMessageDialog(frmViewBookstores, "Sistem ne mo??e da na??e knji??are po zadatim vrednostima", "KNJI??ARA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepareView() {
        fillTblCustomers();
    }

    private void fillTblCustomers() {

        try {
            bookstores = Communication.getInstance().getAllBookstores();
            ModelBookstore mb = new ModelBookstore(bookstores);
            frmViewBookstores.getTblBookstores().setModel(mb);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewBookstores, "Gre??ka: " + ex.getMessage(), "Gre??ka", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void refresh() {
        fillTblCustomers();
    }
}
