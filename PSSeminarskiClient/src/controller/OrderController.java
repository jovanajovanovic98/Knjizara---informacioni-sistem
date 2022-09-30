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
import domain.Bookstore;
import domain.Copy;
import domain.Order;
import domain.PurchaseOrderItem;
import domain.StatusPurchaseOrder;
import form.PurchaseOrderForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ModelOrder;
import view.form.util.FormMode;
import view.form.util.ShippingCompanies;

/**
 *
 * @author Klara
 */
public class OrderController {

    private final PurchaseOrderForm frmOrder;

    public OrderController(PurchaseOrderForm frmOrder) {
        this.frmOrder = frmOrder;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmOrder.setLocationRelativeTo(frmOrder.getParent());
        prepareView(formMode);
        frmOrder.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        setModel();
        fillCmbBookstore();
        fillCmbShipping();
        fillCmbBook();
        fillCmbOrderStatus();
        fillDefaultValues();
        setupComponents(formMode);
    }

    private void fillCmbBookstore() {
        try {
            frmOrder.getCmbBookstore().removeAllItems();
            List<Bookstore> list = Communication.getInstance().getAllBookstores();

            for (Bookstore b : list) {
                frmOrder.getCmbBookstore().addItem(b);
            }
            frmOrder.getCmbBookstore().setSelectedIndex(-1);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmOrder, "Greska prilikom punjenja combo box-a knjizara!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void fillCmbShipping() {
        frmOrder.getCmbShipping().removeAllItems();
        for (ShippingCompanies sc : ShippingCompanies.values()) {
            frmOrder.getCmbShipping().addItem(sc.toString());
        }
    }

    private void fillCmbBook() {
        try {
            frmOrder.getCmbBook().removeAllItems();
            List<Book> list = Communication.getInstance().getAllBooks();

            for (Book b : list) {
                frmOrder.getCmbBook().addItem(b);
            }
            frmOrder.getCmbBook().setSelectedIndex(-1);
            frmOrder.getCmbBook().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Book book = (Book) e.getItem();
                        try {
                            fillCmbCopies(book);
                        } catch (Exception ex) {
                            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                private void fillCmbCopies(Book book) throws Exception {
                    frmOrder.getCmbCopy().removeAllItems();
                    List<Copy> list = Communication.getInstance().getAllCopies(book);
                    ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();

                    for (Copy c : list) {
                        if (c.isAvailable() == true && (otm.checkCopyInTable(c) == false)) {
                            frmOrder.getCmbCopy().addItem(c);
                        }
                    }
                    if (MainCordinator.getInstance().getParam(Constants.PARAM_ITEM_FOR_EDIT_ORDER) == null) {
                        frmOrder.getCmbCopy().setSelectedIndex(-1);
                    }
                    frmOrder.getTxtUnitPriceMinusPDV().setText(book.getPriceWithoutVAT() + "");
                    frmOrder.getTxtUnitPriceMinusPDV().grabFocus();
                    frmOrder.getTxtUnitPriceMinusPDV().setSelectionStart(0);
                }

            });

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmOrder, "Greska prilikom punjenja combo box-a knjiga!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void fillCmbOrderStatus() {
        try {
            frmOrder.getCmbStatusOrder().removeAllItems();
            List<StatusPurchaseOrder> statuses = Communication.getInstance().getAllStatuses();

            for (StatusPurchaseOrder os : statuses) {
                frmOrder.getCmbStatusOrder().addItem(os);
            }
            frmOrder.getCmbStatusOrder().setSelectedIndex(-1);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmOrder, "Greska prilikom punjenja combo box-a statusa!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillDefaultValues() {
        String currentDate = new SimpleDateFormat("dd.MM.yyyy.").format(new Date());
        frmOrder.getTxtDeliveryDate().setText(currentDate);
        frmOrder.getTxtTotalPlusPDV().setText("0.0");
        frmOrder.getTxtTotalMinusPDV().setText("0.0");
        frmOrder.getCmbCopy().setSelectedIndex(-1);
        frmOrder.getCmbCopy().removeAllItems();
    }

    private void setModel() {
        ModelOrder model = new ModelOrder();
        frmOrder.getTblOrderItems().setModel(model);
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmOrder.setTitle("Unos narudžbenice");
                frmOrder.getTxtOrderID().setEnabled(false);
                frmOrder.getCmbBookstore().setEnabled(true);
                frmOrder.getCmbShipping().setEnabled(true);
                frmOrder.getTxtTotalMinusPDV().setEnabled(false);
                frmOrder.getTxtDeliveryDate().setEnabled(true);
                frmOrder.getTxtDateOfReceipt().setEnabled(true);
                frmOrder.getCmbStatusOrder().setEnabled(true);
                frmOrder.getTxtTotalPlusPDV().setEnabled(false);
                frmOrder.getCmbBook().setEnabled(true);
                frmOrder.getCmbCopy().setEnabled(true);
                frmOrder.getTxtUnitPriceMinusPDV().setEnabled(true);
                frmOrder.getBtnDeleteItem().setEnabled(true);
                frmOrder.getBtnEditItem().setEnabled(true);
                frmOrder.getBtnSaveItem().setEnabled(true);
                frmOrder.getBtnSave().setEnabled(true);
                frmOrder.getBtnCancel().setEnabled(false);
                frmOrder.getBtnEnableChanges().setEnabled(false);
                frmOrder.getBtnEdit().setEnabled(false);
                frmOrder.getBtnDelete().setEnabled(false);
                break;
            case FORM_VIEW:
                Order order = (Order) MainCordinator.getInstance().getParam(Constants.PARAM_ORDER);
                frmOrder.setTitle("Detaljnije o narudžbenici");

                frmOrder.getTxtOrderID().setText(order.getOrderID() + "");
                frmOrder.getCmbBookstore().setSelectedItem(order.getBookstore());
                frmOrder.getCmbShipping().setSelectedItem(order.getShippingCompany());
                frmOrder.getTxtTotalMinusPDV().setText(order.getTotalMinusPDV() + "");

                String deliveryDate = new SimpleDateFormat("dd.MM.yyyy.").format(order.getDeliveryDate());
                frmOrder.getTxtDeliveryDate().setText(deliveryDate);

                if (order.getDateOfReceipt() == null) {
                    frmOrder.getTxtDateOfReceipt().setText("");
                } else {
                    String dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").format(order.getDateOfReceipt());
                    frmOrder.getTxtDateOfReceipt().setText(dateOfReceipt);

                }
                frmOrder.getCmbStatusOrder().setSelectedItem(order.getStatus());
                frmOrder.getTxtTotalPlusPDV().setText(order.getTotalPlusPDV() + "");
                frmOrder.getCmbBook().setSelectedItem(-1);
                frmOrder.getTxtUnitPriceMinusPDV().setText("");
                ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
                otm.setList((ArrayList<PurchaseOrderItem>) order.getList());
                otm.fireTableDataChanged();

                frmOrder.getTxtOrderID().setEnabled(false);
                frmOrder.getCmbBookstore().setEnabled(false);
                frmOrder.getCmbShipping().setEnabled(false);
                frmOrder.getTxtTotalMinusPDV().setEnabled(false);
                frmOrder.getTxtDeliveryDate().setEnabled(false);
                frmOrder.getTxtDateOfReceipt().setEnabled(false);
                frmOrder.getCmbStatusOrder().setEnabled(false);
                frmOrder.getTxtTotalPlusPDV().setEnabled(false);
                frmOrder.getCmbBook().setEnabled(false);
                frmOrder.getCmbCopy().setEnabled(false);
                frmOrder.getTxtUnitPriceMinusPDV().setEnabled(false);
                frmOrder.getBtnDeleteItem().setEnabled(false);
                frmOrder.getBtnEditItem().setEnabled(false);
                frmOrder.getBtnSaveItem().setEnabled(false);
                frmOrder.getBtnSave().setEnabled(false);
                frmOrder.getBtnCancel().setEnabled(false);
                frmOrder.getBtnEnableChanges().setEnabled(true);
                frmOrder.getBtnEdit().setEnabled(false);
                frmOrder.getBtnDelete().setEnabled(true);
                break;
            case FORM_EDIT:
                frmOrder.setTitle("Izmena narudžbenice");
                frmOrder.getTxtOrderID().setEnabled(false);
                frmOrder.getCmbBookstore().setEnabled(false);
                frmOrder.getCmbShipping().setEnabled(true);
                frmOrder.getTxtTotalMinusPDV().setEnabled(false);
                frmOrder.getTxtDeliveryDate().setEnabled(true);
                frmOrder.getTxtDateOfReceipt().setEnabled(true);
                frmOrder.getCmbStatusOrder().setEnabled(true);
                frmOrder.getTxtTotalPlusPDV().setEnabled(false);
                frmOrder.getCmbBook().setEnabled(true);
                frmOrder.getCmbCopy().setEnabled(true);
                frmOrder.getTxtUnitPriceMinusPDV().setEnabled(true);
                frmOrder.getBtnDeleteItem().setEnabled(true);
                frmOrder.getBtnEditItem().setEnabled(true);
                frmOrder.getBtnSaveItem().setEnabled(true);
                frmOrder.getBtnSave().setEnabled(false);
                frmOrder.getBtnCancel().setEnabled(true);
                frmOrder.getBtnEnableChanges().setEnabled(false);
                frmOrder.getBtnEdit().setEnabled(true);
                frmOrder.getBtnDelete().setEnabled(false);
                break;
        }
    }

    private void addActionListeners() {
        frmOrder.btnAddItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        frmOrder.btnDeleteItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        frmOrder.btnEditItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editItem();
            }
        });
        frmOrder.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOrder();
            }
        });
        frmOrder.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        frmOrder.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }

        }
        );
        frmOrder.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmOrder.dispose();
            }
        });
        frmOrder.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            editOrder();
            }
        });
    }

    private void addItem() {
        Copy copy = (Copy) frmOrder.getCmbCopy().getSelectedItem();
        if (frmOrder.getCmbCopy().getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(frmOrder, "Nemamo nijedan primerak te knjige!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (copy != null) {
            try {
                double unitPriceMinusPDV = Double.parseDouble(frmOrder.getTxtUnitPriceMinusPDV().getText().trim());

                if (unitPriceMinusPDV <= 0) {
                    JOptionPane.showMessageDialog(frmOrder, "Cena ne sme biti negativan broj!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double unitPricePlusPDV = unitPriceMinusPDV * (1 + copy.getBook().getVat().getVatPercentage());

                ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
                PurchaseOrderItem itemEdit = (PurchaseOrderItem) MainCordinator.getInstance().getParam(Constants.PARAM_ITEM_FOR_EDIT_ORDER);

                if (itemEdit == null) {
                    itemEdit = otm.addItem(copy, unitPriceMinusPDV, unitPricePlusPDV);
                } else {
                    if (!itemEdit.getCopy().equals(copy)) {
                        itemEdit.getCopy().setAvailable(true);
                        Communication.getInstance().editCopy(itemEdit.getCopy());
                        fillCmbBook();
                    }
                    double totalWithoutVAT = Double.parseDouble(frmOrder.getTxtTotalMinusPDV().getText());
                    totalWithoutVAT = totalWithoutVAT - itemEdit.getUnitPriceMinusPDV();
                    frmOrder.getTxtTotalMinusPDV().setText(totalWithoutVAT + "");
                    double totalWithVAT = Double.parseDouble(frmOrder.getTxtTotalPlusPDV().getText());
                    totalWithVAT = totalWithVAT - itemEdit.getUnitPricePlusPDV();
                    frmOrder.getTxtTotalPlusPDV().setText(totalWithVAT + "");
                    otm.editItem(itemEdit, copy, unitPriceMinusPDV, unitPricePlusPDV);
                    otm.fireTableDataChanged();
                };
                resetItemForm();
                double totalWithoutVAT = Double.parseDouble(frmOrder.getTxtTotalMinusPDV().getText());
                totalWithoutVAT = totalWithoutVAT + itemEdit.getUnitPriceMinusPDV();
                frmOrder.getTxtTotalMinusPDV().setText(totalWithoutVAT + "");

                double totalWithVAT = Double.parseDouble(frmOrder.getTxtTotalPlusPDV().getText());
                totalWithVAT = totalWithVAT + itemEdit.getUnitPricePlusPDV();
                frmOrder.getTxtTotalPlusPDV().setText(totalWithVAT + "");
                MainCordinator.getInstance().addParam(Constants.PARAM_ITEM_FOR_EDIT_ORDER, null);
                fillCmbBook();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmOrder, "Nevalidan unos podataka!" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frmOrder, "Morate izabrati primerak!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetItemForm() {
        frmOrder.getCmbBook().setSelectedIndex(-1);
        frmOrder.getCmbCopy().setSelectedIndex(-1);
        frmOrder.getTxtUnitPriceMinusPDV().setText("");
    }

    private void deleteItem() {
        int row = frmOrder.getTblOrderItems().getSelectedRow();
        if (row != -1) {
            ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
            int answer = JOptionPane.showConfirmDialog(frmOrder, "Da li ste sigurni da želite da obrišete stavku?", "Potvrda o brisanju stavke", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                PurchaseOrderItem deletedItem = otm.deleteItem(row);
                deletedItem.getCopy().setAvailable(true);
                try {
                    Communication.getInstance().editCopy(deletedItem.getCopy());
                } catch (Exception ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                fillCmbBook();
                double totalAmountWithoutVAT = Double.parseDouble(frmOrder.getTxtTotalMinusPDV().getText());
                totalAmountWithoutVAT = totalAmountWithoutVAT - deletedItem.getUnitPriceMinusPDV();

                double totalAmountWithVAT = Double.parseDouble(frmOrder.getTxtTotalPlusPDV().getText());
                totalAmountWithVAT = totalAmountWithVAT - deletedItem.getUnitPricePlusPDV();

                frmOrder.getTxtTotalMinusPDV().setText(totalAmountWithoutVAT + "");
                frmOrder.getTxtTotalPlusPDV().setText(totalAmountWithVAT + "");
                fillCmbBook();
            }
        } else {
            JOptionPane.showMessageDialog(frmOrder, "Morate izabrati stavku za brisanje!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void editItem() {
        int row = frmOrder.getTblOrderItems().getSelectedRow();
        if (row != -1) {

            ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
            PurchaseOrderItem item = otm.getItemAtRow(row);
            MainCordinator.getInstance().addParam(Constants.PARAM_ITEM_FOR_EDIT_ORDER, item);
            frmOrder.getCmbBook().setSelectedItem(item.getCopy().getBook());
            frmOrder.getCmbCopy().addItem(item.getCopy());
            frmOrder.getCmbCopy().setSelectedItem(item.getCopy());
            frmOrder.getTxtUnitPriceMinusPDV().setText(item.getUnitPriceMinusPDV() + "");
            frmOrder.getTxtUnitPriceMinusPDV().setSelectionStart(0);
            frmOrder.getTxtUnitPriceMinusPDV().grabFocus();

        } else {
            JOptionPane.showMessageDialog(frmOrder, "Morate izabrati stavku za izmenu!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;

        }
    }

    private void saveOrder() {
        try {
            Bookstore bookstore = (Bookstore) frmOrder.getCmbBookstore().getSelectedItem();
            if (bookstore == null) {
                JOptionPane.showMessageDialog(frmOrder, "Morate izabrati knjizaru!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String shippingCompany = (String) frmOrder.getCmbShipping().getSelectedItem();
            if (shippingCompany == null) {
                JOptionPane.showMessageDialog(frmOrder, "Morate izabrati kompaniju za isporuku!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (frmOrder.getTxtDeliveryDate().getText().isEmpty()) {
                JOptionPane.showMessageDialog(frmOrder, "Datum slanja ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date deliveryDate = new SimpleDateFormat("dd.MM.yyyy.").parse(frmOrder.getTxtDeliveryDate().getText().trim());
            Date dateOfReceipt;
            if (!frmOrder.getTxtDateOfReceipt().getText().isEmpty()) {
                dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").parse(frmOrder.getTxtDateOfReceipt().getText().trim());
            } else {
                JOptionPane.showMessageDialog(frmOrder, "Datum prijema ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StatusPurchaseOrder status = (StatusPurchaseOrder) frmOrder.getCmbStatusOrder().getSelectedItem();
            if (status == null) {
                JOptionPane.showMessageDialog(frmOrder, "Status slanja ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double totalAmountWithoutVAT = Double.parseDouble(frmOrder.getTxtTotalMinusPDV().getText());
            double totalAmountWithVAT = Double.parseDouble(frmOrder.getTxtTotalPlusPDV().getText());
            ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
            ArrayList<PurchaseOrderItem> items = (ArrayList<PurchaseOrderItem>) otm.getList();
            if (items.size() <= 0) {
                JOptionPane.showMessageDialog(frmOrder, "Mora postojati makar jedna stavka!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Order order = new Order(-1, bookstore, shippingCompany, deliveryDate, dateOfReceipt, status, totalAmountWithVAT, totalAmountWithoutVAT, items);
            try {
                Communication.getInstance().addOrder(order);
                frmOrder.getTxtOrderID().setText(order.getOrderID() + "");
                JOptionPane.showMessageDialog(frmOrder, "Sistem je zapamtio narudžbenicu", "Uspesno sačuvano", JOptionPane.INFORMATION_MESSAGE);
                frmOrder.dispose();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frmOrder, "Sistem ne može da zapamti narudžbenicu: " + e.getMessage(), "Neuspešno sačuvano", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmOrder, "Nevalidan unos podataka:" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private void deleteOrder() {
        Order order = (Order) MainCordinator.getInstance().getParam(Constants.PARAM_ORDER);
        try {
            int answer = JOptionPane.showConfirmDialog(frmOrder, "Da li ste sigurni da želite da obrišete narudžbenicu?", "Potvrda o brisanju narudžbenice", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                for (PurchaseOrderItem purchaseOrderItem : order.getList()) {
                    purchaseOrderItem.getCopy().setAvailable(true);
                    Communication.getInstance().editCopy(purchaseOrderItem.getCopy());
                }
                Communication.getInstance().deleteOrder(order);
                JOptionPane.showMessageDialog(frmOrder, "Sistem je uspesno obrisao narudžbenicu!", "Brisanje narudžbenice", JOptionPane.INFORMATION_MESSAGE);
                frmOrder.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmOrder, "Sistem ne moze da obrise narudzbenicu!\n" + ex.getMessage(), "Brisanje narudzbenice", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void editOrder() {
        try {
            System.out.println("STIGAOOOO");
            int orderID = Integer.parseInt(frmOrder.getTxtOrderID().getText());
            Bookstore bookstore = (Bookstore) frmOrder.getCmbBookstore().getSelectedItem();
            if (bookstore == null) {
                JOptionPane.showMessageDialog(frmOrder, "Morate izabrati knjizaru!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String shippingCompany = (String) frmOrder.getCmbShipping().getSelectedItem();
            if (shippingCompany == null) {
                JOptionPane.showMessageDialog(frmOrder, "Morate izabrati kompaniju za isporuku!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (frmOrder.getTxtDeliveryDate().getText().isEmpty()) {
                JOptionPane.showMessageDialog(frmOrder, "Datum slanja ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date deliveryDate = new SimpleDateFormat("dd.MM.yyyy.").parse(frmOrder.getTxtDeliveryDate().getText().trim());
            Date dateOfReceipt;
            if (!frmOrder.getTxtDateOfReceipt().getText().isEmpty()) {
                dateOfReceipt = new SimpleDateFormat("dd.MM.yyyy.").parse(frmOrder.getTxtDateOfReceipt().getText().trim());
            } else {
                JOptionPane.showMessageDialog(frmOrder, "Datum prijema ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StatusPurchaseOrder status = (StatusPurchaseOrder) frmOrder.getCmbStatusOrder().getSelectedItem();
            if (status == null) {
                JOptionPane.showMessageDialog(frmOrder, "Status slanja ne sme biti prazan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double totalAmountWithoutVAT = Double.parseDouble(frmOrder.getTxtTotalMinusPDV().getText());
            double totalAmountWithVAT = Double.parseDouble(frmOrder.getTxtTotalPlusPDV().getText());
            ModelOrder otm = (ModelOrder) frmOrder.getTblOrderItems().getModel();
            ArrayList<PurchaseOrderItem> items = (ArrayList<PurchaseOrderItem>) otm.getList();
            if (items.size() <= 0) {
                JOptionPane.showMessageDialog(frmOrder, "Mora postojati makar jedna stavka!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Order order = new Order(orderID, bookstore, shippingCompany, deliveryDate, dateOfReceipt, status, totalAmountWithVAT, totalAmountWithoutVAT, items);
            try {

                int answer = JOptionPane.showConfirmDialog(frmOrder, "Da li ste sigurni da želite da izmenite narudžbenicu?", "Potvrda o izmeni narudžbenice", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    Communication.getInstance().editOrder(order);
                    JOptionPane.showMessageDialog(frmOrder, "Sistem je zapamtio narudžbenicu", "Izmena narudžbenice", JOptionPane.INFORMATION_MESSAGE);
                    frmOrder.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmOrder, "Sistem ne moze da zapamti narudžbenicu!", "Izmena narudžbenice", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmOrder, "Nevalidan unos podataka:" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
