/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class Request implements Serializable{
    Operation operation;
    Object object;

    public Request() {
    }

    public Request(Operation operation, Object object) {
        this.operation = operation;
        this.object = object;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getArgument() {
        return object;
    }

    public void setArgument(Object argument) {
        this.object = argument;
    }
    
}
