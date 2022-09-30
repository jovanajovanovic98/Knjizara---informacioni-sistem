/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import server.Server;
import server.component.ServerTableModel;
import server.view.ServerView;
import thread.ProcessClientsRequests;

/**
 *
 * @author Korisnik
 */
public class ServerViewController {
    Server server;
    ServerView form;
    List<ProcessClientsRequests> handlerList;

    public ServerViewController(Server server) {
        this.server = server;
        form = new ServerView();
        handlerList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("hh:mm dd.MM.yyyy.");
        form.getLblTimeOfStart().setText(df.format(System.currentTimeMillis()));
        df = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy.");
        form.getLblLastTimeOfUpdate().setText(df.format(System.currentTimeMillis()));
        form.getTblUsers().setModel(new ServerTableModel());
        form.getLblCurrentState().setText("Server nije pokrenut.");
        form.getLblCurrentState().setForeground(Color.red);
        addActionListeners();
        form.getBtnStartServer().setEnabled(true);
        form.getBtnStopServer().setEnabled(false);
        form.getPanelUsers().setVisible(false);
        form.setVisible(true);

    }

    public void addClient(ProcessClientsRequests clientHandler) {
        handlerList.add(clientHandler);
    }

    public void updateView() {
        ((ServerTableModel) form.getTblUsers().getModel()).update(handlerList);
        DateFormat df = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy.");
        form.getLblLastTimeOfUpdate().setText(df.format(System.currentTimeMillis()));
    }

    public void removeClient(ProcessClientsRequests clientHandler) {
        handlerList.remove(clientHandler);
        ((ServerTableModel) form.getTblUsers().getModel()).remove(clientHandler);
    }

    public void editClient(ProcessClientsRequests clientHandler) {
        for (int i = 0; i < handlerList.size(); i++) {
            if (handlerList.get(i).getTimeOfJoining() == clientHandler.getTimeOfJoining()) {
                handlerList.remove(i);
                handlerList.add(i, clientHandler);
            }
        }
    }

    private void addActionListeners() {
        form.getBtnStartServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startServer();
                form.getLblCurrentState().setText("Server je pokrenut.");
                form.getLblCurrentState().setForeground(Color.green);
                form.getPanelUsers().setVisible(true);
                handlerList.addAll(server.getHandlerList());
                ((ServerTableModel) form.getTblUsers().getModel()).update(handlerList);
                form.getBtnStartServer().setEnabled(false);
                form.getBtnStopServer().setEnabled(true);
            }
        });
        form.getBtnStopServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                server.stopSocket();
                form.getLblCurrentState().setText("Server nije pokrenut.");
                form.getLblCurrentState().setForeground(Color.red);
                handlerList.removeAll(handlerList);
                ((ServerTableModel) form.getTblUsers().getModel()).update(handlerList);
                form.getPanelUsers().setVisible(false);
                form.getBtnStartServer().setEnabled(true);
                form.getBtnStopServer().setEnabled(false);
            }

        });
    }
}
