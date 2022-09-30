/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.bookstore;

import domain.Bookstore;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class GetAllBookstores extends AbstractGenericOperation{

    ArrayList<Bookstore> list;
    
    @Override
    protected void preconditions(Object param) throws Exception {
         if (param == null || !(param instanceof Bookstore)) {
            throw new Exception("Invalid customer data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<Bookstore>) repository.getAll((Bookstore)param);
    }

    public ArrayList<Bookstore> getList() {
        return list;
    }
    
    
}
