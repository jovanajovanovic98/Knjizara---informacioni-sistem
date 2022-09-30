/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.book;

import domain.Book;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class GetAllBooks extends AbstractGenericOperation{

    ArrayList<Book> list;
    
    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Book))
            throw new Exception("Invalid product data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<Book>) repository.getAll((Book)param);
    }
    
    public ArrayList<Book> getList() {
        return list;
    }
    
}
