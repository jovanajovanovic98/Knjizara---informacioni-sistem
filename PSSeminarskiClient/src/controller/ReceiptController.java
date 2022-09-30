/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import components.ReceiptTableModel;
import constant.Constants;
import cordinator.MainCordinator;
import domain.Book;
import domain.Receipt;
import domain.ReceiverItem;
import form.BookForm;
import form.ReceiptForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import view.form.util.FormMode;

/**
 *
 * @author Klara
 */
public class ReceiptController {

    private final ReceiptForm frmReceipt;

    public ReceiptController(ReceiptForm frmReceipt) {
        this.frmReceipt = frmReceipt;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmReceipt.setLocationRelativeTo(frmReceipt.getParent());
        prepareView(formMode);
        frmReceipt.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCmbBooks();
        fillDefaultValues();
        setModel();
        setupComponents(formMode);
    }

    private void fillCmbBooks() {
        try {

            frmReceipt.getCmbBook().removeAllItems();
            List<Book> books = Communication.getInstance().getAllBooks();

            for (Book b : books) {
                frmReceipt.getCmbBook().addItem(b);
            }
            frmReceipt.getCmbBook().setSelectedIndex(-1);
            frmReceipt.getCmbBook().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Book book = (Book) e.getItem();
                        frmReceipt.getTxtPurchasePrice().setText(book.getPurchasePrice() + "");
                        frmReceipt.getTxtPurchasePrice().grabFocus();
                        frmReceipt.getTxtPurchasePrice().setSelectionStart(0);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmReceipt, "Greska prilikom punjenja combo box-a knjiga!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillDefaultValues() {
        String currentDate = new SimpleDateFormat("dd.MM.yyyy.").format(new Date());
        frmReceipt.getDateOfReceipt().setText(currentDate);
        frmReceipt.getTxtTotalAmount().setText("0.0");
    }

    private void setModel() {
        ReceiptTableModel rtm = new ReceiptTableModel();
        frmReceipt.getTblReceviverItems().setModel(rtm);
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmReceipt.setTitle("Unos prijemnice");
                frmReceipt.getTxtIDReceive().setEnabled(false);
                frmReceipt.getTxtTotalAmount().setEnabled(false);
                frmReceipt.getDateOfReceipt().setEnabled(true);
                frmReceipt.getCmbBook().setEnabled(true);
                frmReceipt.getTxtIDBook().setEnabled(true);
                frmReceipt.getTxtPurchasePrice().setEnabled(true);

                frmReceipt.getBtnDeleteItem().setEnabled(true);
                frmReceipt.getBtnEditItem().setEnabled(true);
                frmReceipt.getBtnSaveItem().setEnabled(true);
                frmReceipt.getBtnSave().setEnabled(true);
                frmReceipt.getBtnCancel().setEnabled(false);
                frmReceipt.getBtnEnableChanges().setEnabled(false);
                frmReceipt.getBtnEdit().setEnabled(false);
                frmReceipt.getBtnDelete().setEnabled(false);
                break;
            case FORM_VIEW:
                Receipt receipt = (Receipt) MainCordinator.getInstance().getParam(Constants.PARAM_RECEIPT);
                frmReceipt.setTitle("Detaljnije o prijemnici");

                frmReceipt.getTxtIDReceive().setText(receipt.getReceiverID() + "");
                frmReceipt.getTxtTotalAmount().setText(receipt.getTotal() + "");

                String dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").format(receipt.getDateOfReceipt());
                frmReceipt.getDateOfReceipt().setText(dateOfReceipt);

                ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
                rtm.setList((ArrayList<ReceiverItem>) receipt.getList());
                rtm.fireTableDataChanged();

                frmReceipt.getTxtIDReceive().setEnabled(false);
                frmReceipt.getTxtTotalAmount().setEnabled(false);
                frmReceipt.getDateOfReceipt().setEnabled(false);
                frmReceipt.getCmbBook().setEnabled(false);
                frmReceipt.getTxtIDBook().setEnabled(false);
                frmReceipt.getTxtPurchasePrice().setEnabled(false);

                frmReceipt.getBtnDeleteItem().setEnabled(false);
                frmReceipt.getBtnEditItem().setEnabled(false);
                frmReceipt.getBtnSaveItem().setEnabled(false);
                frmReceipt.getBtnSave().setEnabled(false);
                frmReceipt.getBtnCancel().setEnabled(false);
                frmReceipt.getBtnEnableChanges().setEnabled(true);
                frmReceipt.getBtnEdit().setEnabled(false);
                frmReceipt.getBtnDelete().setEnabled(true);

                break;
            case FORM_EDIT:
                frmReceipt.setTitle("Izmena prijemnice");
                frmReceipt.getTxtIDReceive().setEnabled(false);
                frmReceipt.getTxtTotalAmount().setEnabled(false);
                frmReceipt.getDateOfReceipt().setEnabled(true);
                frmReceipt.getCmbBook().setEnabled(true);
                frmReceipt.getTxtIDBook().setEnabled(true);
                frmReceipt.getTxtPurchasePrice().setEnabled(true);

                frmReceipt.getBtnDeleteItem().setEnabled(true);
                frmReceipt.getBtnEditItem().setEnabled(true);
                frmReceipt.getBtnSaveItem().setEnabled(true);
                frmReceipt.getBtnSave().setEnabled(false);
                frmReceipt.getBtnCancel().setEnabled(true);
                frmReceipt.getBtnEnableChanges().setEnabled(false);
                frmReceipt.getBtnEdit().setEnabled(true);
                frmReceipt.getBtnDelete().setEnabled(true);
                break;
        }
    }

    private void addActionListeners() {
        frmReceipt.btnAddItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookCopy();
            }
        });
        frmReceipt.btnEditItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBookCopy();
            }
        });
        frmReceipt.btnDeleteItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBookCopy();
            }
        });
        frmReceipt.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReceipt();
            }
        });
        frmReceipt.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReceipt();
            }
        });
        frmReceipt.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });
        frmReceipt.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmReceipt.dispose();
            }
        });
        frmReceipt.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReceipt();
            }
        });
    }

    private void addBookCopy() {
        Book book = (Book) frmReceipt.getCmbBook().getSelectedItem();
        if (book != null) {
            try {
                String price = frmReceipt.getTxtPurchasePrice().getText();
                if (price.isEmpty()) {
                    JOptionPane.showMessageDialog(frmReceipt, "Morate uneti nabavnu cenu!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double purchasePrice = Double.parseDouble(price);
                if (purchasePrice < 0) {
                    JOptionPane.showMessageDialog(frmReceipt, "Nabavna cena ne sme biti manja od 0!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String idBookCopy = frmReceipt.getTxtIDBook().getText().trim();
                if (idBookCopy.isEmpty()) {
                    JOptionPane.showMessageDialog(frmReceipt, "Morate uneti sifru primerka!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
                boolean checkBookCopyInTable = rtm.checkBookCopyInTable(book, idBookCopy);
                ReceiverItem itemEdit = (ReceiverItem) MainCordinator.getInstance().getParam(Constants.PARAM_ITEM_FOR_EDIT);
                if ((checkBookCopyInTable == true && itemEdit == null) || (itemEdit != null && !itemEdit.getCopy().getBook().equals(book) && checkBookCopyInTable == true)) {
                    JOptionPane.showMessageDialog(frmReceipt, "Primerak već postoji u tabeli!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (itemEdit == null) {
                    itemEdit = rtm.addItem(book, idBookCopy, purchasePrice);
                } else {
                    double total = Double.parseDouble(frmReceipt.getTxtTotalAmount().getText());
                    total = total - itemEdit.getPrice();
                    frmReceipt.getTxtTotalAmount().setText(total + "");
                    rtm.editItem(itemEdit, book, idBookCopy, purchasePrice);

                };
                resetItemForm();
                double total = Double.parseDouble(frmReceipt.getTxtTotalAmount().getText());
                total = total + itemEdit.getPrice();
                frmReceipt.getTxtTotalAmount().setText(total + "");
                MainCordinator.getInstance().addParam(Constants.PARAM_ITEM_FOR_EDIT, null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmReceipt, "Nevalidan unos podataka!" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frmReceipt, "Morate izabrati knjigu!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBookCopy() {
        int row = frmReceipt.getTblReceviverItems().getSelectedRow();
        if (row != -1) {
            ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
            ReceiverItem item = rtm.getItemAtRow(row);
            if (item.getCopy().isAvailable() == false) {
                JOptionPane.showMessageDialog(frmReceipt, "Ne mozete da menjate primerak koji je vec prodat!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frmReceipt.getCmbBook().setSelectedItem(item.getCopy().getBook());
            frmReceipt.getTxtIDBook().setText(item.getCopy().getItemIDByUser());
            frmReceipt.getTxtPurchasePrice().setText(item.getPrice() + "");
            MainCordinator.getInstance().addParam(Constants.PARAM_ITEM_FOR_EDIT, item);
        } else {
            JOptionPane.showMessageDialog(frmReceipt, "Morate izabrati stavku prijemnice za izmenu!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;

        }

    }

    private void deleteBookCopy() {
        int row = frmReceipt.getTblReceviverItems().getSelectedRow();
        if (row != -1) {
            ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
            int answer = JOptionPane.showConfirmDialog(frmReceipt, "Da li ste sigurni da želite da obrišete stavku prijemnice?", "Potvrda o brisanju stavke", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                ReceiverItem forDelete = rtm.getItemAtRow(row);
                if (forDelete.getCopy().isAvailable() == false) {
                    JOptionPane.showMessageDialog(frmReceipt, "Ne mozete da obrisete primerak koji je vec prodat!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ReceiverItem deletedItem = rtm.deleteItem(row);
                double total = Double.parseDouble(frmReceipt.getTxtTotalAmount().getText());
                total = total - deletedItem.getPrice();
                frmReceipt.getTxtTotalAmount().setText(total + "");
            }
        } else {
            JOptionPane.showMessageDialog(frmReceipt, "Morate izabrati stavku prijemnice za brisanje!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void saveReceipt() {
        try {
            String date = frmReceipt.getDateOfReceipt().getText().trim();
            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(frmReceipt, "Morate popuniti datum prijema knjiga!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
            double total = Double.parseDouble(frmReceipt.getTxtTotalAmount().getText().trim());

            ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
            ArrayList<ReceiverItem> items = (ArrayList<ReceiverItem>) rtm.getList();
            if (items.size() <= 0) {
                JOptionPane.showMessageDialog(frmReceipt, "Mora postojati makar jedna stavka!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Receipt receipt = new Receipt(-1, dateOfReceipt, total, items);
            try {
                Communication.getInstance().addReceipt(receipt);
                frmReceipt.getTxtIDReceive().setText(receipt.getReceiverID() + "");
                rtm.fireTableDataChanged();
                JOptionPane.showMessageDialog(frmReceipt, "Sistem je zapamtio prijemnicu", "Uspesno sačuvano", JOptionPane.INFORMATION_MESSAGE);
                frmReceipt.dispose();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frmReceipt, "Sistem ne može da zapamti prijemnicu: " + e.getMessage(), "Neuspešno sačuvano", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmReceipt, "Nevalidan unos podataka:" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private void deleteReceipt() {
        Receipt receipt = (Receipt) MainCordinator.getInstance().getParam(Constants.PARAM_RECEIPT);
        try {
            for (ReceiverItem receiverItem : receipt.getList()) {
                if (receiverItem.getCopy().isAvailable() == false) {
                    JOptionPane.showMessageDialog(frmReceipt, "Sistem ne moze da obrise prijemnicu jer je neki primerak vec prodat!\n", "Brisanje prijemnice", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            int answer = JOptionPane.showConfirmDialog(frmReceipt, "Da li ste sigurni da želite da obrišete prijemnicu?", "Potvrda o brisanju prijemnice", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                Communication.getInstance().deleteReceipt(receipt);
                JOptionPane.showMessageDialog(frmReceipt, "Sistem je uspesno obrisao prijemnicu!", "Brisanje prijemnice", JOptionPane.INFORMATION_MESSAGE);
                frmReceipt.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmReceipt, "Sistem ne moze da obrise prijemnicu!\n" + ex.getMessage(), "Brisanje prijemnice", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void editReceipt() {
        try {
            String date = frmReceipt.getDateOfReceipt().getText().trim();
            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(frmReceipt, "Morate popuniti datum prijema knjiga!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
            double total = Double.parseDouble(frmReceipt.getTxtTotalAmount().getText().trim());

            ReceiptTableModel rtm = (ReceiptTableModel) frmReceipt.getTblReceviverItems().getModel();
            ArrayList<ReceiverItem> items = (ArrayList<ReceiverItem>) rtm.getList();
            if (items.size() <= 0) {
                JOptionPane.showMessageDialog(frmReceipt, "Mora postojati makar jedna stavka!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = Integer.parseInt(frmReceipt.getTxtIDReceive().getText().trim());
            Receipt receipt = new Receipt(id, dateOfReceipt, total, items);
            try {
                int answer = JOptionPane.showConfirmDialog(frmReceipt, "Da li ste sigurni da želite da izmenite prijemnicu?", "Potvrda o izmeni prijemnice", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    Communication.getInstance().editReceipt(receipt);
                    JOptionPane.showMessageDialog(frmReceipt, "Sistem je zapamtio prijemnicu", "Izmena prijemnice", JOptionPane.INFORMATION_MESSAGE);
                    frmReceipt.dispose();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frmReceipt, "Sistem ne može da zapamti prijemnicu: " + e.getMessage(), "Neuspešno sačuvano", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmReceipt, "Nevalidan unos podataka:" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void resetItemForm() {
        frmReceipt.getCmbBook().setSelectedIndex(-1);
        frmReceipt.getTxtIDBook().setText("");
        frmReceipt.getTxtPurchasePrice().setText("");
    }

}
