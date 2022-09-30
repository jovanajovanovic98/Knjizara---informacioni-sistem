/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.vat;

import domain.Vat;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class GetAllVat extends AbstractGenericOperation{

    ArrayList<Vat> list;
    
    @Override
    protected void preconditions(Object param) throws Exception {
      if(param == null || !(param instanceof Vat))
            throw new Exception("Invalid vat data");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
     list = (ArrayList<Vat>) repository.getAll((Vat)param);
    }
    
    public Vat getLastInsertedVat() throws Exception {
        if (list.isEmpty() == false) {
            Vat vat = list.get(list.size() - 1);
            System.out.println(vat);
            return vat;
        } else throw new Exception("Tabela VAT je prazna!");
    }
}
