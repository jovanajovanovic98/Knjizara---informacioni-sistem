/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.order;

import domain.Order;
import domain.PurchaseOrderItem;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class AddOrder extends AbstractGenericOperation{
     @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Order) || ((Order) param).getList() == null) {
            throw new Exception("Invalid order data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Order) param);

        for (PurchaseOrderItem item : ((Order) param).getList()) {
            item.setOrderID(((Order) param).getOrderID());
            item.setBookstoreID(((Order)param).getBookstore().getId());
            repository.edit(item.getCopy());
            repository.add(item);
        }

    }
}
