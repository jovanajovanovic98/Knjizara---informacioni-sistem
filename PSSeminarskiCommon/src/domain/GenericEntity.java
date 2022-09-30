/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public interface GenericEntity extends Serializable{
     String getTableName();

    String getColumnNamesForInsert();

    String getInsertValues();

    String getColumnNamesForGetAll();

    String getJoinClause();

    String getWhereClauseDeleteEdit();

    String getColumnNamesValuesUpdate();

    void setId(int id);

    String getWhereForGetAll();
}
