/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import thread.ProcessClientsRequests;


/**
 *
 * @author Klara
 */
public class ServerTableModel extends AbstractTableModel {

    List<ProcessClientsRequests> users;
    List<ProcessClientsRequests> toShow;
    String[] columns = new String[]{"Redni broj", "Korisničko ime", "Vreme pridruživanja"};

    public ServerTableModel() {
        users = new ArrayList<>();
        toShow = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return toShow.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessClientsRequests clientHandler = toShow.get(rowIndex);
        DateFormat df = new SimpleDateFormat("hh:mm dd.MM.yyyy.");
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return clientHandler.getUser() == null ? null : clientHandler.getUser().getUsername();
            case 2:
                return df.format(clientHandler.getTimeOfJoining());
            default:
                return "n/a";
        }
    }

    public void update(List<ProcessClientsRequests> handlerList) {
        this.users = handlerList;
        for (ProcessClientsRequests processClientsRequests : users) {
            System.out.println("ID:" + processClientsRequests.getUser().getId());
            if (processClientsRequests.getUser().getId() != 0 && notExist(processClientsRequests)) {
                toShow.add(processClientsRequests);
                System.out.println(toShow.size());
            }
        }
        fireTableDataChanged();
    }

    private boolean notExist(ProcessClientsRequests processClientsRequests) {
        boolean notExist = true;
        for (ProcessClientsRequests processClientsRequests1 : toShow) {
            if (processClientsRequests1.equals(processClientsRequests)) {
                notExist = false;
                break;
            }
        }
        return notExist;
    }

    public void remove(ProcessClientsRequests handlerList) {
        toShow.remove(handlerList);
    }

}
