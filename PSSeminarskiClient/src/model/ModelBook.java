/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domain.Book;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Korisnik
 */
public class ModelBook extends AbstractTableModel{
    private List<Book> books;
    String[] colnames = new String[]{"ID", "Naziv", "Autor", "Žanr", "Cena bez PDV-a", "Cena sa PDV-om"};

    public ModelBook(List<Book> books) {
        this.books = books;
    }

    

    @Override
    public int getRowCount() {
        if (books == null) {
            return 0;
        }
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return colnames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book b = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return b.getBookID();
            case 1:
                return b.getName();
            case 2:
                return b.getAuthor();
            case 3:
                return b.getGenre();
            case 4:
                return b.getPriceWithoutVAT();
            case 5:
                return b.getPriceWithVAT();
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


    public void addBook(Book book) {
        books.add(book);
        fireTableRowsInserted(books.size()-1, books.size()-1);
    }

    public Book getBookAt(int row) {
        return books.get(row);
    }

    public boolean emptyList() {
        if(books == null || books.isEmpty())
            return true;
        return false;
    }

    public List<Book> getBook() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
