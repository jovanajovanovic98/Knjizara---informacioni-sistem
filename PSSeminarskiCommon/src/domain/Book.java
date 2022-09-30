/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;

/**
 *
 * @author Korisnik
 */
public class Book implements GenericEntity{
    private int bookID;
    private String name;
    private double priceWithoutVAT;
    private double priceWithVAT;
    private double purchasePrice;
    private BookGenre genre;
    private String author;
    private Vat vat;

    public Book() {
    }

    public Book(int bookID, String name, double priceWithoutVAT, double priceWithVAT, double purchasePrice, BookGenre genre, String author, Vat vat) {
        this.bookID = bookID;
        this.name = name;
        this.priceWithoutVAT = priceWithoutVAT;
        this.priceWithVAT = priceWithVAT;
        this.purchasePrice = purchasePrice;
        this.genre = genre;
        this.author = author;
        this.vat = vat;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }


    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceWithoutVAT() {
        return priceWithoutVAT;
    }

    public void setPriceWithoutVAT(double priceWithoutVAT) {
        this.priceWithoutVAT = priceWithoutVAT;
    }

    public double getPriceWithVAT() {
        return priceWithVAT;
    }

    public void setPriceWithVAT(double priceWithVAT) {
        this.priceWithVAT = priceWithVAT;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getTableName() {
        return "book";
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public String getColumnNamesForInsert() {
        return "name, priceWithoutVAT, priceWithVAT, genre, author, vat, purchasePrice";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(name).append("\"").append(", ")
        .append(priceWithoutVAT).append(", ")
        .append(priceWithVAT).append(", ")
        .append(genre.genreID).append(", ")
        .append("\"").append(author).append("\"").append(", ")
        .append(vat.vatID).append(", ").append(purchasePrice).append(" ");
                
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " bg.genreID, bg.nameOfGenre, v.vatID, v.vatPercentage, b.bookID,b.name,b.priceWithoutVAT,b.priceWithVAT,b.author, b.purchasePrice ";
    }

    @Override
    public String getJoinClause() {
        return " bookgenre bg on b.genre= bg.genreID join vat v on b.vat=v.vatID ";
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        return " bookID=" + bookID;
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("name ='").append(name).append("', ").append("priceWithoutVAT=").append(priceWithoutVAT).append(", priceWithVAT=").append(priceWithVAT).append(", genre=").append(genre.genreID).append(", author='").append(author).append("', vat=").append(vat.vatID).append(", purchasePrice= ").append(purchasePrice).append(" ");
        return sb.toString();
    }

    @Override
    public void setId(int id) {
        this.bookID = id;
    }

    @Override
    public String getWhereForGetAll() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Book other = (Book) obj;
        if (this.bookID != other.bookID) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceWithoutVAT) != Double.doubleToLongBits(other.priceWithoutVAT)) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceWithVAT) != Double.doubleToLongBits(other.priceWithVAT)) {
            return false;
        }
        if (Double.doubleToLongBits(this.purchasePrice) != Double.doubleToLongBits(other.purchasePrice)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        return true;
    }

    

    

    

    
}
