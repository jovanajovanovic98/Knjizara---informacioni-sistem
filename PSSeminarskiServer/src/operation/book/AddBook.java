/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.book;

import domain.Book;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class AddBook extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Book))
            throw new Exception("Invalid product data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Book)param);
    }
    
}
