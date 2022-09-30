/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.genres;

import domain.BookGenre;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class GetAllBookGenres extends AbstractGenericOperation{

    ArrayList<BookGenre> list;
    
    @Override
    protected void preconditions(Object param) throws Exception {
     if(param == null || !(param instanceof BookGenre))
            throw new Exception("Invalid product type data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
       list = (ArrayList<BookGenre>) repository.getAll((BookGenre)param);
    }
    
    public ArrayList<BookGenre> getList() {
        return list;
    }
}
