/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import components.ViewAllOrdersTableModel;
import constant.Constants;
import cordinator.MainCordinator;
import domain.Bookstore;
import domain.Order;
import domain.StatusPurchaseOrder;
import form.ViewOrderForm;
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
import view.form.util.FormMode;

/**
 *
 * @author Klara
 */
public class OrderViewController {

    private final ViewOrderForm frmViewOrders;
    List<Order> orders;

    public OrderViewController(ViewOrderForm frmViewOrders) {
        this.frmViewOrders = frmViewOrders;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmViewOrders.setLocationRelativeTo(frmViewOrders.getParent());
        prepareView();
        frmViewOrders.setVisible(true);
    }

    private void prepareView() {
        frmViewOrders.setTitle("Pretraživanje narudžbenica");
        ViewAllOrdersTableModel ostm = new ViewAllOrdersTableModel();
        frmViewOrders.getTblOrders().setModel(ostm);
        fillTblOrders();
        fillCmbStatuses();
        fillCmbBookstores();
    }

    private void fillTblOrders() {
        try {
            orders = Communication.getInstance().getAllOrders();
            ViewAllOrdersTableModel ostm = (ViewAllOrdersTableModel) frmViewOrders.getTblOrders().getModel();
            ostm.setOrders((ArrayList<Order>) orders);
            ostm.fireTableDataChanged();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewOrders, "Greška: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void fillCmbStatuses() {
        try {

            frmViewOrders.getCmbStatusSearch().removeAllItems();
            List<StatusPurchaseOrder> list = Communication.getInstance().getAllStatuses();
            frmViewOrders.getCmbStatusSearch().addItem("");
            for (StatusPurchaseOrder os : list) {
                frmViewOrders.getCmbStatusSearch().addItem(os.getStatusPurchaseOrder());
            }

            frmViewOrders.getCmbStatusSearch().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        search();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewOrders, "Greska prilikom punjenja combo box-a statusa narudžbenice!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillCmbBookstores() {
        try {

            frmViewOrders.getCmbBookstoreSearch().removeAllItems();
            List<Bookstore> list = Communication.getInstance().getAllBookstores();
            frmViewOrders.getCmbBookstoreSearch().addItem("");
            for (Bookstore b : list) {
                frmViewOrders.getCmbBookstoreSearch().addItem(b.getName());
            }

            frmViewOrders.getCmbBookstoreSearch().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        search();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewOrders, "Greska prilikom punjenja combo box-a knjizara narudžbenice!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        frmViewOrders.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewOrders.getTblOrders().getSelectedRow();
                if (row >= 0) {
                    try {
                        Order order = ((ViewAllOrdersTableModel) frmViewOrders.getTblOrders().getModel()).getOrderAt(row);
                        MainCordinator.getInstance().addParam(Constants.PARAM_ORDER, order);
                        MainCordinator.getInstance().openOrderDetailsProductForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmViewOrders, "Sistem ne moze da ucita narudžbenicu", "NARUDŽBENICA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewOrders, "Morate izabrati narudžbenicu iz tabele!", "NARUDŽBENICA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewOrders.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblOrders();
            }
        });

        frmViewOrders.btnAddOrderAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewOrderForm();
            }
        });

        frmViewOrders.btnDeleteOrderAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewOrders.getTblOrders().getSelectedRow();
                if (row >= 0) {
                    Order order = ((ViewAllOrdersTableModel) frmViewOrders.getTblOrders().getModel()).getOrderAt(row);
                    try {
                        int answer = JOptionPane.showConfirmDialog(frmViewOrders, "Da li ste sigurni da želite da obrišete narudžbenicu?", "Potvrda o brisanju narudžbenice", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            Communication.getInstance().deleteOrder(order);
                            JOptionPane.showMessageDialog(frmViewOrders, "Sistem je uspesno obrisao narudžbenicu!", "Brisanje narudžbenice", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frmViewOrders, "Sistem ne moze da izbriše narudžbenicu", "NARUDŽBENICA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewOrders, "Morate izabrati narudžbenicu iz tabele!", "NARUDŽBENICA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewOrders.getTxtPhoneNumberSearchAddKeyListener(new KeyListener() {
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
        String phonenumber = frmViewOrders.getTxtPhoneNumberSearch().getText().trim();
        String bookstore = (String) frmViewOrders.getCmbBookstoreSearch().getSelectedItem();
        String status = (String) frmViewOrders.getCmbStatusSearch().getSelectedItem();
        ArrayList<Order> listSearch = new ArrayList<>();
        for (Order order : orders) {
            if (order.getBookstore().getPhoneNumber().contains(phonenumber) && (order.getBookstore().getName().contains(bookstore)) && (order.getStatus().getStatusPurchaseOrder().contains(status))) {
                listSearch.add(order);
                System.out.println("OVDE");
            }
        }
        ViewAllOrdersTableModel ostm = (ViewAllOrdersTableModel) frmViewOrders.getTblOrders().getModel();
        ostm.setOrders(listSearch);
        ostm.fireTableDataChanged();
        if (listSearch.isEmpty()) {
            JOptionPane.showMessageDialog(frmViewOrders, "Sistem ne može da nađe narudžbenice po zadatim vrednostima", "Pretraga narudžbenica", JOptionPane.ERROR_MESSAGE);
            clearCriteriums();
        }

    }

    private void clearCriteriums() {
        frmViewOrders.getCmbBookstoreSearch().setSelectedItem("");
        frmViewOrders.getCmbStatusSearch().setSelectedItem("");
        frmViewOrders.getTxtPhoneNumberSearch().setText("");
    }
}
