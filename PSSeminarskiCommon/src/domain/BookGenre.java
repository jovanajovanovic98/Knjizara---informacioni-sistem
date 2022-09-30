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
public class BookGenre implements GenericEntity{
    int genreID;
    String nameOfGenre;

    public BookGenre() {
    }

    public BookGenre(int genreID, String nameOfGenre) {
        this.genreID = genreID;
        this.nameOfGenre = nameOfGenre;
    }

    public String getNameOfGenre() {
        return nameOfGenre;
    }

    public void setNameOfGenre(String nameOfGenre) {
        this.nameOfGenre = nameOfGenre;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    @Override
    public String toString() {
        return nameOfGenre;
    }

    @Override
    public String getTableName() {
        return "bookgenre";
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

    @Override
    public int hashCode() {
        int hash = 5;
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
        final BookGenre other = (BookGenre) obj;
        if (this.genreID != other.genreID) {
            return false;
        }
        if (!Objects.equals(this.nameOfGenre, other.nameOfGenre)) {
            return false;
        }
        return true;
    }

   

    
    
    
}
