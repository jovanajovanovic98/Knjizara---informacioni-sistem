/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.receipt;

import domain.Receipt;
import domain.ReceiverItem;
import operation.AbstractGenericOperation;

/**
 *
 * @author Klara
 */
public class AddReceipt extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Receipt) || ((Receipt) param).getList() == null) {
            throw new Exception("Invalid receipt data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Receipt) param);

        for (ReceiverItem item : ((Receipt) param).getList()) {
            item.setReceiverID(((Receipt) param).getReceiverID());
            repository.add(item.getCopy());
            repository.add(item);
        }

    }
}
