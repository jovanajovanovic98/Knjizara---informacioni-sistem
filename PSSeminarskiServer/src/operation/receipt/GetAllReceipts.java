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
public class GetAllReceipts extends AbstractGenericOperation{
    ArrayList<Receipt> list;
    @Override
    protected void preconditions(Object param) throws Exception {
         if(param == null || !(param instanceof Receipt))
            throw new Exception("Invalid receipt data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<Receipt>) repository.getAll((Receipt)param);
        for (Receipt r : list) {
            ReceiverItem ri = new ReceiverItem();
            ri.setReceiverID(r.getReceiverID());
            ArrayList<ReceiverItem> items = (ArrayList<ReceiverItem>) repository.getAll(ri);
            r.setList(items);
        }
    }

    public ArrayList<Receipt> getList() {
        return list;
    }
    
}
