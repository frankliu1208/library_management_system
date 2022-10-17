package com.itheima.service;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import entity.PageResult;
/**
 * Book service interface
 */
public interface BookService {

    // search the books that is newly on the shelf, relates to selectNewBooks method in BookMapper.java
    // belong to book management module,  Home page sub-module
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);


    //search book according to id
    // belong to book management module,  Home page sub-module
    Book findById(String id);

    // belong to book management module,  Home page sub-module
    Integer borrowBook(Book book);



    // search book applying pagination, conditional search
    // belong to book management module:  2nd sub-module book lending
    PageResult search(Book book, Integer pageNum, Integer pageSize);



    //add new book
    // belong to book management module:  2nd sub-module book lending, only for administrator
    Integer addBook(Book book);


    //edit book information, common user does not have this function
    // belong to book management module:  2nd sub-module book lending, only for administrator
    Integer editBook(Book book);


    //search borrowed book
    // belongs to third sub-module: current borrowing
    PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize);


    //return the book
    // belongs to third sub-module: current borrowing
    boolean returnBook(String  id,User user);

    //confirm returning
    // belong to 3rd sub-module current borrowing.
    Integer returnConfirm(String id);

}
