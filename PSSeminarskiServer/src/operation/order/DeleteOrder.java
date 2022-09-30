/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.order;

import domain.Order;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class DeleteOrder extends AbstractGenericOperation{
    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof Order))
            throw new Exception("Invalid order data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.delete((Order)param);
    }
}
