/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copies;

import domain.Book;
import domain.Copy;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class GetAllCopies extends AbstractGenericOperation{
    ArrayList<Copy> list;
    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof Copy))
            throw new Exception("Invalid copy data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<Copy>) repository.getAll((Copy)param);
    }

    public ArrayList<Copy> getList() {
        return list;
    }
     public ArrayList<Copy> getList(Book book) {
         ArrayList<Copy> listBook = new ArrayList<>();
         for (Copy copy : list) {
             if(copy.getBook().equals(book))
                 listBook.add(copy);
         }
        return listBook;
    }
}
