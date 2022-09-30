/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import components.ViewAllReceiptsTableModel;
import constant.Constants;
import cordinator.MainCordinator;
import domain.Receipt;
import domain.ReceiverItem;
import form.ViewReceiptsForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.JOptionPane;
import view.form.util.FormMode;

/**
 *
 * @author Klara
 */
public class ReceiptViewAllController {

    private final ViewReceiptsForm frmViewReceipts;
    List<Receipt> list;

    public ReceiptViewAllController(ViewReceiptsForm frmViewReceipts) {
        this.frmViewReceipts = frmViewReceipts;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        frmViewReceipts.setLocationRelativeTo(frmViewReceipts.getParent());
        prepareView();
        frmViewReceipts.setVisible(true);
    }

    private void addActionListeners() {
        frmViewReceipts.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewReceipts.getTblViewAllReceipts().getSelectedRow();
                if (row >= 0) {
                    try {
                        Receipt receipt = ((ViewAllReceiptsTableModel) frmViewReceipts.getTblViewAllReceipts().getModel()).getReceiptAt(row);
                        MainCordinator.getInstance().addParam(Constants.PARAM_RECEIPT, receipt);
                        MainCordinator.getInstance().openReceiptDetailsForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmViewReceipts, "Sistem ne moze da ucita prijemnicu", "PRIJEMNICA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewReceipts, "Morate izabrati prijemnicu iz tabele!", "PRIJEMNICA - DETALJNIJE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewReceipts.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblReceipts();
            }

        });
        frmViewReceipts.btnAddReceiptAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewReceiptForm();
            }
        });
        frmViewReceipts.btnDeleteReceiptAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewReceipts.getTblViewAllReceipts().getSelectedRow();
                if (row >= 0) {
                    ViewAllReceiptsTableModel model = (ViewAllReceiptsTableModel) frmViewReceipts.getTblViewAllReceipts().getModel();
                    Receipt receipt = model.getReceiptAt(row);
                    try {
                        for (ReceiverItem receiverItem : receipt.getList()) {
                            if (receiverItem.getCopy().isAvailable() == false) {
                                JOptionPane.showMessageDialog(frmViewReceipts, "Sistem ne moze da izbriše prijemnicu jer je bar jedan primerak vec prodat", "PRIJEMNICA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        int answer = JOptionPane.showConfirmDialog(frmViewReceipts, "Da li ste sigurni da želite da obrišete prijemnicu?", "Potvrda o brisanju prijemnice", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            Communication.getInstance().deleteReceipt(receipt);
                            JOptionPane.showMessageDialog(frmViewReceipts, "Sistem je uspesno obrisao prijemnicu!", "Brisanje prijemnice", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frmViewReceipts, "Sistem ne moze da izbriše prijemnicu", "PRIJEMNICA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewReceipts, "Morate izabrati prijemnicu iz tabele!", "PRIJEMNICA - BRISANJE", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        frmViewReceipts.getTxtIDSearchAddKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
              
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });
        frmViewReceipts.getTxtDateSearchAddKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
               
            }

            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });
    }

    private void prepareView() {
        frmViewReceipts.setTitle("Pretraživanje prijemnica");
        ViewAllReceiptsTableModel vrtm = new ViewAllReceiptsTableModel();
        frmViewReceipts.getTblViewAllReceipts().setModel(vrtm);
        fillTblReceipts();
    }

    private void fillTblReceipts() {
        try {
            list = Communication.getInstance().getAllReceipts();
            ViewAllReceiptsTableModel vrtm = (ViewAllReceiptsTableModel) frmViewReceipts.getTblViewAllReceipts().getModel();
            vrtm.setList((ArrayList<Receipt>) list);
            vrtm.fireTableDataChanged();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewReceipts, "Greška: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void search() {
        String id = (String) frmViewReceipts.getTxtIDSearch().getText();
        String date = frmViewReceipts.getTxtDateSearch().getText().trim();
        ArrayList<Receipt> listSearch = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        for (Receipt receipt : list) {
            if (String.valueOf(receipt.getReceiverID()).contains(id)) {
                String dateReceipt = sdf.format(receipt.getDateOfReceipt());
                if (dateReceipt.contains(date)) {
                    listSearch.add(receipt);
                }
            }
        }
        ViewAllReceiptsTableModel ostm = (ViewAllReceiptsTableModel) frmViewReceipts.getTblViewAllReceipts().getModel();
        ostm.setList(listSearch);
        ostm.fireTableDataChanged();
        if (listSearch.isEmpty()) {
            JOptionPane.showMessageDialog(frmViewReceipts, "Sistem ne može da nađe prijemnice po zadatim vrednostima", "Pretraga prijemnica", JOptionPane.ERROR_MESSAGE);
        }

    }
}
