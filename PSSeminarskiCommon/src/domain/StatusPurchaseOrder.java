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
public class StatusPurchaseOrder implements GenericEntity {
    private int statusID;
    private String StatusPurchaseOrder;

    public StatusPurchaseOrder() {
    }

    public StatusPurchaseOrder(int statusID, String StatusPurchaseOrder) {
        this.statusID = statusID;
        this.StatusPurchaseOrder = StatusPurchaseOrder;
    }

    public String getStatusPurchaseOrder() {
        return StatusPurchaseOrder;
    }

    public void setStatusPurchaseOrder(String StatusPurchaseOrder) {
        this.StatusPurchaseOrder = StatusPurchaseOrder;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    @Override
    public String toString() {
        return StatusPurchaseOrder;
    }

    @Override
    public String getTableName() {
        return "status";
    }

    @Override
    public String getColumnNamesForInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnNamesForGetAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(" statusID").append(", ").append("orderStatus ");
        return sb.toString();
    }

    @Override
    public String getJoinClause() {
        return null;
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        final StatusPurchaseOrder other = (StatusPurchaseOrder) obj;
        if (this.statusID != other.statusID) {
            return false;
        }
        return true;
    }
    
   
}
