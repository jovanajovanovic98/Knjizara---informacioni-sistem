/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import domain.Book;
import domain.Copy;
import domain.ReceiverItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Klara
 */
public class ReceiptTableModel extends AbstractTableModel {

    List<ReceiverItem> list;

    public ReceiptTableModel() {
        list = new ArrayList<>();
    }

    public void setList(List<ReceiverItem> list) {
        this.list = list;
    }

    public List<ReceiverItem> getList() {
        return list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReceiverItem item = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getRb();
            case 1:
                return item.getCopy().getItemIDByUser();
            case 2:
                return item.getCopy().getBook();
            case 3:
                return item.getPrice();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Redni broj";
            case 1:
                return "Sifra primerka";
            case 2:
                return "Naziv knjige";
            case 3:
                return "Nabavna cena";
            default:
                return "n/a";
        }
    }

    public boolean checkBookCopyInTable(Book book, String idBookCopy) {
        for (ReceiverItem receiverItem : list) {
            if (receiverItem.getCopy().getBook().equals(book) && receiverItem.getCopy().getItemIDByUser().equals(idBookCopy)) {
                return true;
            }
        }

        return false;
    }

    public ReceiverItem addItem(Book book, String idBookCopy, Double price) {
        Copy copy = new Copy(idBookCopy, book, -1, true);
        ReceiverItem item = new ReceiverItem(-1, -1, copy, price, -1);
        list.add(item);
        fixRB();
        fireTableDataChanged();
        return item;
    }

    private void fixRB() {
        int rb = 1;
        for (ReceiverItem receiverItem : list) {
            receiverItem.setRb(rb++);
        }
    }

    public void editItem(ReceiverItem itemEdit, Book book, String idBookCopy, double purchasePrice) {
        itemEdit.getCopy().setBook(book);
        itemEdit.getCopy().setItemIDByUser(idBookCopy);
        itemEdit.setPrice(purchasePrice);
        fireTableDataChanged();
    }

    public ReceiverItem getItemAtRow(int row) {
        if (row >= 0) {
            return list.get(row);
        }
        return null;
    }

    public ReceiverItem deleteItem(int row) {
        ReceiverItem item = list.get(row);
        list.remove(row);
        fixRB();
        fireTableDataChanged();
        return item;
    }

}
