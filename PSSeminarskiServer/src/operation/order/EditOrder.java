/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.order;

import domain.Order;
import domain.PurchaseOrderItem;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class EditOrder extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Order) || ((Order) param).getList() == null) {
            throw new Exception("Invalid order data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.edit((Order) param);
        PurchaseOrderItem i = new PurchaseOrderItem();
        i.setOrderID(((Order) param).getOrderID());
        i.setBookstoreID(((Order)param).getBookstore().getId());
        
        ArrayList<PurchaseOrderItem> listInDatabase = (ArrayList<PurchaseOrderItem>) repository.getAll(i);
        ArrayList<PurchaseOrderItem> forUpdate = new ArrayList<>();
        ArrayList<PurchaseOrderItem> forDelete = new ArrayList<>();
        ArrayList<PurchaseOrderItem> forInsert = new ArrayList<>();

        for (PurchaseOrderItem item : ((Order) param).getList()) {
            for (PurchaseOrderItem itemDatabase : listInDatabase) {
                if (item.getItemID()== itemDatabase.getItemID() && (!item.equals(itemDatabase))) {
                    forUpdate.add(item);
                }
            }
            if (item.getItemID() == -1) {
                forInsert.add(item);
            }
        }

        for (PurchaseOrderItem itemDatabase : listInDatabase) {
            boolean has = false;
            for (PurchaseOrderItem item : ((Order)param).getList()) {
                if (itemDatabase.getItemID() == item.getItemID()) {
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
            for (PurchaseOrderItem item : forInsert) {
                item.setOrderID(((Order)param).getOrderID());
                item.setBookstoreID(((Order)param).getBookstore().getId());
                repository.add(item);
            }
        }
        if (forDelete.size() > 0) {
            for (PurchaseOrderItem item : forDelete) {
                repository.delete(item);
            }
        }
        if (forUpdate.size() > 0) {
            for (PurchaseOrderItem item : forUpdate) {
                repository.edit(item);
            }
        }
    }
    
}
