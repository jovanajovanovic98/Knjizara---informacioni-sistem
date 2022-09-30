/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domain.Bookstore;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Korisnik
 */
public class ModelBookstore extends AbstractTableModel{
    private List<Bookstore> bookstores;
    String[] colnames = new String[]{"ID",  "Naziv", "Broj telefona", "Adresa", "Mesto", "E-mail"};

    public ModelBookstore(List<Bookstore> bookstores) {
        this.bookstores = bookstores;
    }

    
    @Override
    public int getRowCount() {
        if (bookstores == null) {
            return 0;
        }
        return bookstores.size();
    }

    @Override
    public int getColumnCount() {
        return colnames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bookstore book = bookstores.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getId();
            case 1:
                return book.getName();
            case 2:
                return book.getPhoneNumber();
            case 3:
                return book.getAddress();
            case 4:
                return book.getCity();
            case 5: 
                return book.getEmail();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column > colnames.length) {
            return "n/a";
        }
        return colnames[column];
    }


    public void addBookstore(Bookstore bookstore) {
        bookstores.add(bookstore);
        fireTableRowsInserted(bookstores.size()-1,bookstores.size()-1);
    }

    public Bookstore getBookstoreAt(int row) {
        return bookstores.get(row);
    }

    public boolean emptyList() {
        if(bookstores == null || bookstores.isEmpty())
            return true;
        return false;
    }

    public List<Bookstore> getBookstores() {
        return bookstores;
    }

    public void setBookstore(List<Bookstore> bookstores) {
        this.bookstores = bookstores;
    }
}
