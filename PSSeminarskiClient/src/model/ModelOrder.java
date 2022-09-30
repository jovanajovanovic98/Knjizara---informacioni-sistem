/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domain.Copy;
import domain.PurchaseOrderItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Klara
 */
public class ModelOrder extends AbstractTableModel{
    List<PurchaseOrderItem> list;

    public ModelOrder() {
        list = new ArrayList<>();
    }

    public List<PurchaseOrderItem> getList() {
        return list;
    }

    public void setList(List<PurchaseOrderItem> list) {
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PurchaseOrderItem poi = list.get(rowIndex);
        switch(columnIndex){
            case 0: return poi.getRb();
            case 1: return poi.getCopy().getBook();
            case 2: return poi.getCopy().getItemIDByUser();
            case 3: return poi.getUnitPriceMinusPDV();
            case 4: return poi.getUnitPricePlusPDV();
            default: return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "RB";
            case 1: return "Knjiga";
            case 2: return "Primerak";
            case 3: return "Cena bez PDV-a";
            case 4: return "Cena sa PDV-om";
            default: return "n/a";
        }
    }

    public PurchaseOrderItem addItem(Copy copy, double unitPriceMinusPDV, double unitPricePlusPDV) {
        PurchaseOrderItem item = new PurchaseOrderItem(-1, -1, -1, -1, copy, unitPricePlusPDV, unitPriceMinusPDV);
        item.getCopy().setAvailable(false);
        list.add(item);
        fixRB();
        fireTableDataChanged();
        return item;
    }

    private void fixRB() {
        int rb = 1;
        for (PurchaseOrderItem purchaseOrderItem : list) {
            purchaseOrderItem.setRb(rb++);
        }
    }

    public void editItem(PurchaseOrderItem itemEdit, Copy copy, double unitPriceMinusPDV, double unitPricePlusPDV) {
       itemEdit.setCopy(copy);
       itemEdit.setUnitPriceMinusPDV(unitPriceMinusPDV);
       itemEdit.setUnitPricePlusPDV(unitPricePlusPDV);
    }

    public PurchaseOrderItem deleteItem(int row) {
        PurchaseOrderItem itemDelete = list.get(row);
        list.remove(row);
        fixRB();
        fireTableDataChanged();
        itemDelete.getCopy().setAvailable(true);
        return itemDelete;
    }

    public PurchaseOrderItem getItemAtRow(int row) {
        return list.get(row);
    }

    public boolean checkCopyInTable(Copy c) {
        for (PurchaseOrderItem purchaseOrderItem : list) {
            if(purchaseOrderItem.getCopy().getIdCopy() == c.getIdCopy())
                return true;
        }
        return false;
    }
    
}
