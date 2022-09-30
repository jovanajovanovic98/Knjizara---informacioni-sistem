/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderstatus;

import domain.StatusPurchaseOrder;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class GetAllOrderStatuses extends AbstractGenericOperation{
    ArrayList<StatusPurchaseOrder> list;
    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof StatusPurchaseOrder))
            throw new Exception("Invalid order status data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<StatusPurchaseOrder>) repository.getAll((StatusPurchaseOrder)param);
    }

    public ArrayList<StatusPurchaseOrder> getList() {
        return list;
    }
    
}
