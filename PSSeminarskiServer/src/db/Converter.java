/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domain.Book;
import domain.BookGenre;
import domain.Bookstore;
import domain.Copy;
import domain.GenericEntity;
import domain.Order;
import domain.PurchaseOrderItem;
import domain.Receipt;
import domain.ReceiverItem;
import domain.StatusPurchaseOrder;
import domain.User;
import domain.Vat;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Korisnik
 */
public class Converter {

    public static GenericEntity convert(GenericEntity entity, ResultSet rs) throws SQLException {

        if (entity instanceof Book) {
            Book b = new Book();
            BookGenre bg = new BookGenre(rs.getInt("bg.genreID"), rs.getString("bg.nameOfGenre"));
            b.setGenre(bg);
            Vat vat = new Vat(rs.getInt("v.vatID"), rs.getDouble("v.vatPercentage"));
            b.setVat(vat);
            b.setBookID(rs.getInt("b.bookID"));
            b.setName(rs.getString("b.name"));
            b.setPriceWithoutVAT(rs.getDouble("b.priceWithoutVAT"));
            b.setPriceWithVAT(rs.getDouble("b.priceWithVAT"));
            b.setAuthor(rs.getString("b.author"));
            b.setPurchasePrice(rs.getDouble("b.purchasePrice"));
            return b;
        } else if (entity instanceof BookGenre) {
            BookGenre bg = new BookGenre(rs.getInt("genreID"), rs.getString("nameOfGenre"));
            return bg;
        } else if (entity instanceof User) {
            User user = new User();
            user.setId(rs.getInt("userID"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        } else if (entity instanceof Bookstore) {
            Bookstore bookstore = new Bookstore();
            bookstore.setId(rs.getInt("bookstoreID"));
            bookstore.setName(rs.getString("name"));
            bookstore.setPhoneNumber(rs.getString("phoneNumber"));
            bookstore.setAddress(rs.getString("address"));
            bookstore.setCity(rs.getString("city"));
            bookstore.setEmail(rs.getString("email"));
            return bookstore;
        } else if (entity instanceof Vat) {
            Vat vat = new Vat(rs.getInt("vatID"), rs.getDouble("vatPercentage"));
            return vat;
        } else if (entity instanceof ReceiverItem) {
            Book b = new Book();
            BookGenre bg = new BookGenre(rs.getInt("bg.genreID"), rs.getString("bg.nameOfGenre"));
            b.setGenre(bg);
            Vat vat = new Vat(rs.getInt("v.vatID"), rs.getDouble("v.vatPercentage"));
            b.setVat(vat);
            b.setBookID(rs.getInt("b.bookID"));
            b.setName(rs.getString("b.name"));
            b.setPriceWithoutVAT(rs.getDouble("b.priceWithoutVAT"));
            b.setPriceWithVAT(rs.getDouble("b.priceWithVAT"));
            b.setAuthor(rs.getString("b.author"));
            b.setPurchasePrice(rs.getDouble("b.purchasePrice"));
            Copy copy = new Copy(rs.getString("c.itemIDByUser"), b, rs.getInt("r.copyID"), rs.getBoolean("c.available"));
            ReceiverItem ri = new ReceiverItem(rs.getInt("r.receiptID"), rs.getInt("r.idItem"), copy, rs.getDouble("r.price"), rs.getInt("r.rb"));
            return ri;
        } else if (entity instanceof Receipt) {
            Receipt r = new Receipt();
            r.setReceiverID(rs.getInt(1));
            r.setDateOfReceipt(rs.getDate(2));
            r.setTotal(rs.getDouble(3));
            return r;
        } else if (entity instanceof Copy) {
            Book b = new Book();
            BookGenre bg = new BookGenre(rs.getInt("bg.genreID"), rs.getString("bg.nameOfGenre"));
            b.setGenre(bg);
            Vat vat = new Vat(rs.getInt("v.vatID"), rs.getDouble("v.vatPercentage"));
            b.setVat(vat);
            b.setBookID(rs.getInt("b.bookID"));
            b.setName(rs.getString("b.name"));
            b.setPriceWithoutVAT(rs.getDouble("b.priceWithoutVAT"));
            b.setPriceWithVAT(rs.getDouble("b.priceWithVAT"));
            b.setAuthor(rs.getString("b.author"));
            b.setPurchasePrice(rs.getDouble("b.purchasePrice"));
            Copy copy = new Copy(rs.getString("c.itemIDByUser"), b, rs.getInt("c.copyID"), rs.getBoolean("c.available"));
            return copy;
        } else if (entity instanceof StatusPurchaseOrder) {
            StatusPurchaseOrder status = new StatusPurchaseOrder(rs.getInt("statusID"), rs.getString("orderStatus"));
            return status;
        } else if (entity instanceof Order) {
            Bookstore b = new Bookstore(rs.getInt("b.bookstoreID"), rs.getString("b.name"), rs.getString("b.phoneNumber"), rs.getString("b.address"), rs.getString("b.city"), rs.getString("b.email"));
            StatusPurchaseOrder s = new StatusPurchaseOrder(rs.getInt("s.statusID"), rs.getString("s.orderStatus"));
            Order o = new Order(rs.getInt("o.orderID"), b, rs.getString("o.shipping"), rs.getDate("o.deliveryDate"), rs.getDate("o.dateOfReceipt"), s, rs.getDouble("o.totalPlusPDV"), rs.getDouble("o.totalMinusPDV"), null);
            return o;
        } else if (entity instanceof PurchaseOrderItem) {
            Book b = new Book();
            BookGenre bg = new BookGenre(rs.getInt("bg.genreID"), rs.getString("bg.nameOfGenre"));
            b.setGenre(bg);
            Vat vat = new Vat(rs.getInt("v.vatID"), rs.getDouble("v.vatPercentage"));
            b.setVat(vat);
            b.setBookID(rs.getInt("b.bookID"));
            b.setName(rs.getString("b.name"));
            b.setPriceWithoutVAT(rs.getDouble("b.priceWithoutVAT"));
            b.setPriceWithVAT(rs.getDouble("b.priceWithVAT"));
            b.setAuthor(rs.getString("b.author"));
            b.setPurchasePrice(rs.getDouble("b.purchasePrice"));
            Copy copy = new Copy(rs.getString("c.itemIDByUser"), b, rs.getInt("c.copyID"), rs.getBoolean("c.available"));
            PurchaseOrderItem item = new PurchaseOrderItem(rs.getInt("o.itemID"), rs.getInt("o.orderID"), rs.getInt("o.bookstoreID"), rs.getInt("o.rb"), copy, rs.getDouble("o.unitPricePlusPDV"), rs.getDouble("o.unitPriceMinusPDV"));
            return item;
        }
        return null;
    }
}
