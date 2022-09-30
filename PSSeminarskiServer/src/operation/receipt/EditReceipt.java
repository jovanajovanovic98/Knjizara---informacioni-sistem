/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.receipt;

import domain.Receipt;
import domain.ReceiverItem;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class EditReceipt extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Receipt) || ((Receipt) param).getList() == null) {
            throw new Exception("Invalid receipt data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.edit((Receipt) param);
        ReceiverItem ri = new ReceiverItem();
        ri.setReceiverID(((Receipt) param).getReceiverID());
        
        ArrayList<ReceiverItem> listInDatabase = (ArrayList<ReceiverItem>) repository.getAll(ri);
        ArrayList<ReceiverItem> forUpdate = new ArrayList<>();
        ArrayList<ReceiverItem> forDelete = new ArrayList<>();
        ArrayList<ReceiverItem> forInsert = new ArrayList<>();

        for (ReceiverItem item : ((Receipt) param).getList()) {
            for (ReceiverItem itemDatabase : listInDatabase) {
                if (item.getIdItem()== itemDatabase.getIdItem() && (!item.equals(itemDatabase))) {
                    forUpdate.add(item);
                }
            }
            System.out.println("PAZI:" + item.getIdItem());
            if (item.getIdItem() == -1) {
                forInsert.add(item);
            }
        }

        for (ReceiverItem itemDatabase : listInDatabase) {
            boolean has = false;
            for (ReceiverItem item : ((Receipt)param).getList()) {
                if (itemDatabase.getIdItem() == item.getIdItem()) {
                    has = true;
                    break;
                }

            }
            if (has == false) {
                forDelete.add(itemDatabase);
            }
        }
        System.out.println("DELETE:" + forDelete.size());
        System.out.println("UPDATE:" + forUpdate.size());
        System.out.println("ADD: " + forInsert.size());
        if (forInsert.size() > 0) {
            for (ReceiverItem item : forInsert) {
                item.setReceiverID(((Receipt)param).getReceiverID());
                repository.add(item.getCopy());
                repository.add(item);
            }
        }
        if (forDelete.size() > 0) {
            for (ReceiverItem item : forDelete) {
                repository.delete(item);
            }
        }
        if (forUpdate.size() > 0) {
            for (ReceiverItem item : forUpdate) {
                repository.edit(item.getCopy());
                repository.edit(item);
            }
        }
    }
    
}
