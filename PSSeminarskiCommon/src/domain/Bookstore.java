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
public class Bookstore implements GenericEntity{
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String email;

    public Bookstore() {
    }

    public Bookstore(int id, String name, String phoneNumber, String address, String city, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return name;
    }
    

    @Override
    public String getTableName() {
        return "bookstore";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "name, phoneNumber, address, city, email";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(name).append("\",")
        .append("\"").append(phoneNumber).append("\",")
        .append("\"").append(address).append("\",")
        .append("\"").append(city).append("\",")
        .append("\"").append(email).append("\"");   
        return sb.toString();
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
        return "bookstoreID="+id;
    }

    @Override
    public String getColumnNamesValuesUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("name ='").append(name).append("', ").append("phoneNumber ='").append(phoneNumber).append("', ").append("address ='").append(address).append("', ").append("city ='").append(city).append("', ").append("email ='").append(email).append("'");
        return sb.toString();
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
        final Bookstore other = (Bookstore) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
