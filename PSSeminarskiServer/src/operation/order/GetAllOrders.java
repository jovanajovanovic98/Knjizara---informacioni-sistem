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
public class GetAllOrders extends AbstractGenericOperation{
    ArrayList<Order> list;
    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof Order))
            throw new Exception("Invalid order data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<Order>) repository.getAll((Order)param);
        for (Order o : list) {
            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setOrderID(o.getOrderID());
            item.setBookstoreID(o.getBookstore().getId());
            ArrayList<PurchaseOrderItem> items = (ArrayList<PurchaseOrderItem>) repository.getAll(item);
            o.setList(items);
        }
    }

    public ArrayList<Order> getList() {
        return list;
    }
}
