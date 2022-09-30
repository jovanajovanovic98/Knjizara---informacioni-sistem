/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.user;

import domain.User;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class GetAllUsers extends AbstractGenericOperation{

    ArrayList<User> list;
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof User)) {
            throw new Exception("Invalid user data");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = (ArrayList<User>) repository.getAll((User) param);
    }
    
     public User login(User userCheck) throws Exception {
        for (User user : list) {
            if (user.getUsername().equals(userCheck.getUsername())) {
                if (user.getPassword().equals(userCheck.getPassword())) {
                    return user;
                }
            }
        }
        throw new Exception("Korisnik ne postoji!");
    }
    
}
