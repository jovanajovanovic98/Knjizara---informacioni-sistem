/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Klara
 */
public class Receipt implements GenericEntity{
    
    private int receiverID;
    Date dateOfReceipt;
    double total;
    List<ReceiverItem> list;

    public Receipt() {
    }

    public Receipt(int receiverID, Date dateOfReceipt, double total, List<ReceiverItem> list) {
        this.receiverID = receiverID;
        this.dateOfReceipt = dateOfReceipt;
        this.total = total;
        this.list = list;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ReceiverItem> getList() {
        return list;
    }

    public void setList(List<ReceiverItem> list) {
        this.list = list;
    }
    
    
    @Override
    public String getTableName() {
        return "receipt";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "dateOfReceipt, total";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append((dateOfReceipt == null ? null : "\'" + sdf.format(dateOfReceipt) + "\'")).append(",").append(total).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesForGetAll() {
        return " receiptID, dateOfReceipt, total";
    }

    @Override
    public String getJoinClause() {
        return null;
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        StringBuilder sb = new  StringBuilder();
        sb.append(" receiptID=").append(receiverID).append(" ");
        return sb.toString();
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("dateOfReceipt=").append((dateOfReceipt == null ? null : "\'" + sdf.format(dateOfReceipt) + "\'")).append(",")
          .append(" total=").append(total);
        return sb.toString();
    }

    @Override
    public void setId(int id) {
        this.receiverID = id;
    }

    @Override
    public String getWhereForGetAll() {
        return null;
    }
    
}
