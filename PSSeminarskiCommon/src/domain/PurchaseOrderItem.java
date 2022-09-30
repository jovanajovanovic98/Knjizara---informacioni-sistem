/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Korisnik
 */
public class PurchaseOrderItem implements GenericEntity{
    
    private int itemID;
    private int orderID;
    private int bookstoreID;
    private int rb;
    private Copy copy;
    private double unitPricePlusPDV;
    private double unitPriceMinusPDV;

    public PurchaseOrderItem() {
    }

    public PurchaseOrderItem(int itemID, int orderID, int bookstoreID, int rb, Copy copy, double unitPricePlusPDV, double unitPriceMinusPDV) {
        this.itemID = itemID;
        this.orderID = orderID;
        this.bookstoreID = bookstoreID;
        this.rb = rb;
        this.copy = copy;
        this.unitPricePlusPDV = unitPricePlusPDV;
        this.unitPriceMinusPDV = unitPriceMinusPDV;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookstoreID() {
        return bookstoreID;
    }

    public void setBookstoreID(int bookstoreID) {
        this.bookstoreID = bookstoreID;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public double getUnitPricePlusPDV() {
        return unitPricePlusPDV;
    }

    public void setUnitPricePlusPDV(double unitPricePlusPDV) {
        this.unitPricePlusPDV = unitPricePlusPDV;
    }

    public double getUnitPriceMinusPDV() {
        return unitPriceMinusPDV;
    }

    public void setUnitPriceMinusPDV(double unitPriceMinusPDV) {
        this.unitPriceMinusPDV = unitPriceMinusPDV;
    }
    
    
    @Override
    public String getTableName() {
        return "orderitems";
    }

    @Override
    public String getColumnNamesForInsert() {
        return " orderID, bookstoreID, rb, copyID, unitPricePlusPDV, unitPriceMinusPDV ";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(orderID).append(", ").append(bookstoreID).append(", ").append(rb).append(", ").append(copy.getIdCopy()).append(", ").append(unitPricePlusPDV).append(", ").append(unitPriceMinusPDV).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " o.itemID, o.orderID, o.bookstoreID, o.rb, o.copyID, o.unitPricePlusPDV, o.unitPriceMinusPDV, c.copyID, c.itemIDByUser, c.available, bg.genreID, bg.nameOfGenre, v.vatID, v.vatPercentage, b.bookID,b.name,b.priceWithoutVAT,b.priceWithVAT,b.author, b.purchasePrice ";
    }

    @Override
    public String getJoinClause() {
        return " copies c on (o.copyID=c.copyID) JOIN book b on (c.bookID=b.bookID) JOIN  bookgenre bg on b.genre= bg.genreID join vat v on b.vat=v.vatID ";
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        StringBuilder sb = new StringBuilder();
        sb.append(" itemID=").append(itemID).append(" and orderID=").append(orderID).append(" and bookstoreID=").append(bookstoreID);
        return sb.toString();
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append(" rb=").append(rb).append(", copyID=").append(copy.getIdCopy()).append(", unitPricePlusPDV=").append(unitPricePlusPDV).append(", unitPriceMinusPDV=").append(unitPriceMinusPDV);
        return sb.toString();       
    }

    @Override
    public void setId(int id) {
        this.itemID = id;
    }

    @Override
    public String getWhereForGetAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(" o.orderID=").append(orderID).append(" and o.bookstoreID=").append(bookstoreID).append(" ");
        return sb.toString();
    }
    

   
}
