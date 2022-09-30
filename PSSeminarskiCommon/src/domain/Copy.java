/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;

/**
 *
 * @author Klara
 */
public class Copy implements GenericEntity {
    
    private String itemIDByUser;
    private Book book;
    private int idCopy;
    private boolean available;

    public Copy() {
    }

    public Copy(String itemIDByUser, Book book, int idCopy, boolean available) {
        this.itemIDByUser = itemIDByUser;
        this.book = book;
        this.idCopy = idCopy;
        this.available = available;
    }

    public void setIdCopy(int idCopy) {
        this.idCopy = idCopy;
    }

    public int getIdCopy() {
        return idCopy;
    }


    public String getItemIDByUser() {
        return itemIDByUser;
    }

    public void setItemIDByUser(String itemIDByUser) {
        this.itemIDByUser = itemIDByUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String getTableName() {
        return "copies";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "itemIDByUser, bookID, available";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("\'").append(itemIDByUser).append("\', ").append(book.getBookID()).append(", ").append(available).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " c.copyID, c.itemIDByUser, c.available, bg.genreID, bg.nameOfGenre, v.vatID, v.vatPercentage, b.bookID,b.name,b.priceWithoutVAT,b.priceWithVAT,b.author, b.purchasePrice  ";
    }

    @Override
    public String getJoinClause() {
        return " book b on (c.bookID=b.bookID) JOIN bookgenre bg on b.genre= bg.genreID join vat v on b.vat=v.vatID ";
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        return " copyID=" + idCopy;
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append(" itemIDByUser=\'").append(itemIDByUser).append("\', bookID=").append(book.getBookID()).append(", available=").append(isAvailable());
        return sb.toString();
    }

    @Override
    public void setId(int id) {
        this.idCopy = id;
    }

    @Override
    public String getWhereForGetAll() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Copy other = (Copy) obj;
        if (this.idCopy != other.idCopy) {
            return false;
        }
        if (this.available != other.available) {
            return false;
        }
        if (!Objects.equals(this.itemIDByUser, other.itemIDByUser)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemIDByUser;
    }

    

}
