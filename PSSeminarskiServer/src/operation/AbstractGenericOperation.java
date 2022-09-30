/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation;

import db.DBRepository;
import db.RepositoryDBGeneric;
import repository.Repository;



/**
 *
 * @author Klara
 */
public abstract class AbstractGenericOperation {

    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }

    public final void execute(Object param) throws Exception {
        try {
            preconditions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
            
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            throw e;
        } finally {
            disconnect();
        }
    }

    protected abstract void preconditions(Object param) throws Exception;

    private void startTransaction() throws Exception{
        ((DBRepository)repository).connect();
    }

    protected abstract void executeOperation(Object param) throws Exception;

    private void commitTransaction() throws Exception{
        ((DBRepository)repository).commit();
    }

    private void rollbackTransaction() throws Exception{
        ((DBRepository)repository).rollback();
    }

    private void disconnect() throws Exception{
        ((DBRepository)repository).disconnect();
    }

}
