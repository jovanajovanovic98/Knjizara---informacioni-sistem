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
public class Vat implements GenericEntity{
    int vatID;
    double vatPercentage;

    public Vat() {
    }

    public Vat(int vatID, double vatPercentage) {
        this.vatID = vatID;
        this.vatPercentage = vatPercentage;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public int getVatID() {
        return vatID;
    }

    public void setVatID(int vatID) {
        this.vatID = vatID;
    }

    @Override
    public String getTableName() {
        return "vat";
    }

    @Override
    public String getColumnNamesForInsert() {
        return null;
    }

    @Override
    public String getInsertValues() {
        return null;
    }

    @Override
    public String getColumnNamesForGetAll() {
        return "*";
    }

    @Override
    public String getJoinClause() {
        return null;
    }

    @Override
    public String getWhereClauseDeleteEdit() {
        return null;
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        return null;
    }

    @Override
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getWhereForGetAll() {
        return null;
    }

    
    
}
