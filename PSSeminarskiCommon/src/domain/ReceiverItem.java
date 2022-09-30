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
public class ReceiverItem implements GenericEntity {

    private int receiverID;
    private int idItem;
    private Copy copy;
    private double price;
    private int rb;

    public ReceiverItem() {
    }

    public ReceiverItem(int receiverID, int idItem, Copy copy, double price, int rb) {
        this.receiverID = receiverID;
        this.idItem = idItem;
        this.copy = copy;
        this.price = price;
        this.rb = rb;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    
    @Override
    public String getTableName() {
        return "receiveritem";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "receiptID, copyID, price, rb";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(receiverID).append(", ").append(copy.getIdCopy()).append(", ").append(price).append(", ").append(rb).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " r.receiptID, r.idItem, r.copyID, r.price, r.rb, c.itemIDByUser, c.bookID, c.available, bg.genreID, bg.nameOfGenre, v.vatID, v.vatPercentage, b.bookID,b.name,b.priceWithoutVAT,b.priceWithVAT,b.author, b.purchasePrice ";
    }

    @Override
    public String getJoinClause() {
        return " copies c on (r.copyID=c.copyID) JOIN book b on (c.bookID=b.bookID) JOIN bookgenre bg on b.genre= bg.genreID join vat v on b.vat=v.vatID ";
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        return " receiptID=" + receiverID + " and idItem=" + idItem;
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append(" copyID=").append(copy.getIdCopy()).append(", price=").append(price).append(", rb=").append(rb).append(" ");
        return sb.toString();
    }

    @Override
    public void setId(int id) {
        this.idItem = id;
    }

    @Override
    public String getWhereForGetAll() {
        return " receiptID=" + receiverID;
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
        final ReceiverItem other = (ReceiverItem) obj;
        if (this.receiverID != other.receiverID) {
            return false;
        }
        if (this.idItem != other.idItem) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.rb != other.rb) {
            return false;
        }
        if (!Objects.equals(this.copy, other.copy)) {
            return false;
        }
        return true;
    }

    

    

}
