/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constant.Constants;
import cordinator.MainCordinator;
import domain.User;
import form.MainForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Korisnik
 */
public class MainController {
      private final MainForm frmMain;

    public MainController(MainForm frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }
    
    public void openForm() {
        User user = (User) MainCordinator.getInstance().getParam(Constants.PARAM_CURRENT_USER);
        frmMain.getLblCurrentUser().setText("Trenutno prijavljeni korisnik:" + user.getFirstname() + " " + user.getLastname());
        frmMain.setVisible(true);
    }
    
     public MainForm getFrmMain() {
        return frmMain;
    }
    
     private void addActionListener() {
        frmMain.jmiBookstoreNewAddActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCustomerNewActionPerformed(evt);
            }

            private void jmiCustomerNewActionPerformed(java.awt.event.ActionEvent evt) {
                MainCordinator.getInstance().openAddNewBookstoreForm();
            }
        });
        frmMain.jmiBookstoreShowAllActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCustomerShowAllActionPerformed(evt);
            }

            private void jmiCustomerShowAllActionPerformed(java.awt.event.ActionEvent evt) {
                MainCordinator.getInstance().openViewBookstoresForm();
            }
        });

        frmMain.jmiBookNewAddActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductNewActionPerformed(evt);
            }

            private void jmiProductNewActionPerformed(ActionEvent evt) {
                MainCordinator.getInstance().openAddNewBookForm();
            }
        });

        frmMain.jmiBookShowAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmiProductShowAllActionPerformed(e);
            }

            private void jmiProductShowAllActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllBooksForm();
            }
        });
        frmMain.jmiAddNewReceiverAddActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           MainCordinator.getInstance().openAddNewReceiptForm();
            }
        });
        frmMain.jmiReceiverShowAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            MainCordinator.getInstance().openShowAllReceiversForm();
            }
        });
        frmMain.jmiOrderNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmiProductNewActionPerformed(e);
            }

            private void jmiProductNewActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewOrderForm();
            }
        });
        
        frmMain.jmiOrderShowAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmiProductShowAllActionPerformed(e);
            }

            private void jmiProductShowAllActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllOrderForm();
            }
        });
     }

}
