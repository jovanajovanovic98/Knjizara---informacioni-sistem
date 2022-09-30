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
public enum Operation implements Serializable{
    LOGIN,
    ADD_BOOKSTORE,
    GET_ALL_BOOKSTORE,
    DELETE_BOOKSTORE,
    EDIT_BOOKSTORE,
    ADD_BOOK,
    GET_ALL_BOOK,
    DELETE_BOOK,
    EDIT_BOOK,
    GET_VAT,
    GET_ALL_GENRES_OF_BOOKS,
    ADD_RECEIPT,
    DELETE_RECEIPT,
    GET_ALL_RECEIPTS,
    EDIT_RECEIPT,
    GET_ALL_STATUSES,
    GET_ALL_COPIES,
    ADD_ORDER,
    DELETE_ORDER,
    GET_ALL_ORDERS,
    EDIT_ORDER,
    EDIT_COPY
    
}
