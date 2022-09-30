/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class Order implements GenericEntity {

    private int orderID;
    private Bookstore bookstore;
    private String shippingCompany;
    private Date deliveryDate;
    private Date dateOfReceipt;
    private StatusPurchaseOrder status;
    private double totalPlusPDV;
    private double totalMinusPDV;
    private List<PurchaseOrderItem> list;

    public Order() {
    }

    public Order(int orderID, Bookstore bookstore, String shippingCompany, Date deliveryDate, Date dateOfReceipt, StatusPurchaseOrder status, double totalPlusPDV, double totalMinusPDV, List<PurchaseOrderItem> list) {
        this.orderID = orderID;
        this.bookstore = bookstore;
        this.shippingCompany = shippingCompany;
        this.deliveryDate = deliveryDate;
        this.dateOfReceipt = dateOfReceipt;
        this.status = status;
        this.totalPlusPDV = totalPlusPDV;
        this.totalMinusPDV = totalMinusPDV;
        this.list = list;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Bookstore getBookstore() {
        return bookstore;
    }

    public void setBookstore(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public StatusPurchaseOrder getStatus() {
        return status;
    }

    public void setStatus(StatusPurchaseOrder status) {
        this.status = status;
    }

    public double getTotalPlusPDV() {
        return totalPlusPDV;
    }

    public void setTotalPlusPDV(double totalPlusPDV) {
        this.totalPlusPDV = totalPlusPDV;
    }

    public double getTotalMinusPDV() {
        return totalMinusPDV;
    }

    public void setTotalMinusPDV(double totalMinusPDV) {
        this.totalMinusPDV = totalMinusPDV;
    }

    public List<PurchaseOrderItem> getList() {
        return list;
    }

    public void setList(List<PurchaseOrderItem> list) {
        this.list = list;
    }

    @Override
    public String getTableName() {
        return "orderno";
    }

    @Override
    public String getColumnNamesForInsert() {
        return " bookstoreID, shipping, deliveryDate, dateOfReceipt, statusID, totalPlusPDV, totalMinusPDV ";
 }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        sb.append(bookstore.getId()).append(", \'")
                .append(shippingCompany).append("\', ")
                .append((deliveryDate == null ? NULL : "\'" + sdf.format(dateOfReceipt) + "\'")).append(",")
                .append((dateOfReceipt == null ? NULL: "\'" + sdf.format(dateOfReceipt) + "\'")).append(",")
                .append(status.getStatusID()).append(", ")
                .append(totalPlusPDV).append(", ")
                .append(totalMinusPDV).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " o.orderID, o.bookstoreID, o.shipping, o.deliveryDate, o.dateOfReceipt, s.statusID, s.orderStatus, o.totalPlusPDV, o.totalMinusPDV, b.bookstoreID, b.name, b.phoneNumber, b.address, b.city, b.email ";
    }

    @Override
    public String getJoinClause() {
        return " status s on (o.statusID=s.statusID) JOIN bookstore b on (o.bookstoreID=b.bookstoreID) ";
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        StringBuilder sb = new StringBuilder();
        sb.append(" orderID=").append(orderID).append(" and bookstoreID=").append(bookstore.getId());
        return sb.toString();
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(" shipping=\'").append(shippingCompany).append("\',").append(" deliveryDate=\'").append(sdf.format(deliveryDate)).append("\',").append(" dateOfReceipt=\'").append(sdf.format(dateOfReceipt)).append("\', ").append(" statusID=").append(status.getStatusID()).append(", totalPlusPDV=").append(totalPlusPDV).append(", totalMinusPDV=").append(totalMinusPDV).append(" ");
        return sb.toString();
    }

    @Override
    public void setId(int id) {
        this.orderID = id;
    }

    @Override
    public String getWhereForGetAll() {
        return null;
    }

}
