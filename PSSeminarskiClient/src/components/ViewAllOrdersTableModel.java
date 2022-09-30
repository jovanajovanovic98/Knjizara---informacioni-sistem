/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import domain.Order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Klara
 */
public class ViewAllOrdersTableModel extends AbstractTableModel{
    ArrayList<Order> orders;
    String[] columnNames = new String[]{"ID", "Knjizara", "Broj telefona knjizare", "Datum slanja", "Iznos sa PDV-om", "Kurir", "Status"};
    public ViewAllOrdersTableModel() {
        orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Order order = orders.get(rowIndex);
         SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        switch(columnIndex)
        {
            case 0: return order.getOrderID();
            case 1: return order.getBookstore();
            case 2: return order.getBookstore().getPhoneNumber();
            case 3: return sdf.format(order.getDeliveryDate());
            case 4: return order.getTotalPlusPDV();
            case 5: return order.getShippingCompany();
            case 6: return order.getStatus();
            default: return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
     public Order getOrderAt(int row) {
        return orders.get(row);
    }
    
    
}
