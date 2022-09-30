/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.receipt;

import domain.Receipt;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class DeleteReceipt extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof Receipt))
            throw new Exception("Invalid receipt data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.delete((Receipt)param);
    }
    
}
