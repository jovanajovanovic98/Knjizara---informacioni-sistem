/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import domain.Receipt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Klara
 */
public class ViewAllReceiptsTableModel extends AbstractTableModel {

    ArrayList<Receipt> list;

    public ViewAllReceiptsTableModel() {
        list = new ArrayList<>();
    }

    public ArrayList<Receipt> getList() {
        return list;
    }

    public void setList(ArrayList<Receipt> list) {
        this.list = list;
    }
   
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Receipt r = list.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        switch (columnIndex) {
            case 0:
                return r.getReceiverID();
            case 1:
                return sdf.format(r.getDateOfReceipt());
            case 2:
                return r.getTotal();
            default: return "n/a";
          
        }
    }

    @Override
    public String getColumnName(int column) {
        
        switch (column) {
            case 0:
                return "ID prijemnice";
            case 1:
                return "Datum prijema";
            case 2:
                return "Total";
            default: return "n/a";
          
        }
    }

    public Receipt getReceiptAt(int row) {
        return list.get(row);
    }

}
