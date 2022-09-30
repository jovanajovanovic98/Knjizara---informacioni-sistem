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
import form.ViewBookForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.ModelBook;

/**
 *
 * @author Korisnik
 */
public class BookViewAllController {
    private final ViewBookForm frmViewBooks;
    List<Book> books;

    public BookViewAllController(ViewBookForm frmViewBooks) {
        this.frmViewBooks = frmViewBooks;
        addActionListener();
    }

    public void openForm() {
        frmViewBooks.setLocationRelativeTo(MainCordinator.getInstance().getMainController().getFrmMain());
        prepareView();
        frmViewBooks.setVisible(true);
    }
    public void closeForm() {
        frmViewBooks.setVisible(false);
    }
    private void fillCmbProductTypes() {
        try {

            frmViewBooks.getCmbBookGenresSearch().removeAllItems();
            List<BookGenre> list = Communication.getInstance().getAllBookGenres();
            frmViewBooks.getCmbBookGenresSearch().addItem("");
            for (BookGenre bg : list) {
                frmViewBooks.getCmbBookGenresSearch().addItem(bg.getNameOfGenre());
            }

            frmViewBooks.getCmbBookGenresSearch().setSelectedItem("");
            frmViewBooks.getCmbBookGenresSearch().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String type = (String) e.getItem();
                        search(type);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewBooks, "Greska prilikom punjenja combo box-a žanrova knjiga!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search(String genre) {
        String nameOfBook = frmViewBooks.getTxtNameSearch().getText().trim();
        ArrayList<Book> list = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().getNameOfGenre().contains(genre) && book.getName().contains(nameOfBook)) {
                list.add(book);
            }
        }
        ModelBook mb = (ModelBook) frmViewBooks.getTblBook().getModel();
        mb.setBooks(list);
        mb.fireTableDataChanged();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(frmViewBooks, "Sistem ne moze da nađe knjige po zadatoj vrednosti", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void addActionListener() {
        frmViewBooks.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewBooks.getTblBook().getSelectedRow();
                if (row >= 0) {
                    try {
                        Book product = ((ModelBook) frmViewBooks.getTblBook().getModel()).getBookAt(row);
                        MainCordinator.getInstance().addParam(Constants.PARAM_BOOK, product);
                        MainCordinator.getInstance().openBookDetailsBookForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmViewBooks, "Sistem ne moze da ucita knjigu", "KNJIGA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(frmViewBooks, "Morate izabrati knjigu iz tabele!", "KNJIGA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewBooks.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblProducts();
            }
        });

        frmViewBooks.getBtnAddAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewBookForm();
            }
        });

        frmViewBooks.getBtnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewBooks.getTblBook().getSelectedRow();
                if (row >= 0) {
                    Book book = ((ModelBook) frmViewBooks.getTblBook().getModel()).getBookAt(row);
                    int answer = JOptionPane.showConfirmDialog(frmViewBooks, "Da li ste sigurni da želite da obrišete knjigu?", "Potvrda o brisanju proizvoda", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        try {
                            Communication.getInstance().deleteBook(book);
                            JOptionPane.showMessageDialog(frmViewBooks, "Sistem je uspesno obrisao knjigu!", "Brisanje knjige", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frmViewBooks, "Sistem ne moze da obrise knjigu!\n" + ex.getMessage(), "Brisanje proizvoda", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewBooks, "Morate izabrati knjigu iz tabele!", "KNJIGA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewBooks.getTxtNameOfBookSearchAddKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                String genre = (String) frmViewBooks.getCmbBookGenresSearch().getSelectedItem();
                search(genre);
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

        });
    }

    private void prepareView() {
        fillTblProducts();
        fillCmbProductTypes();
    }

    private void fillTblProducts() {

        try {
            books = Communication.getInstance().getAllBooks();
            ModelBook ctm = new ModelBook(books);
            frmViewBooks.getTblBook().setModel(ctm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewBooks, "Greška: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void refresh() {
        fillTblProducts();
    }
}
