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
public class User implements GenericEntity{
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return firstname+" " + lastname;
    }

    
    @Override
    public String getTableName() {
       return "user";
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
    public String getJoinClause(){
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
    public String getWhereForGetAll() {
        return null;
    }
    
    

}
