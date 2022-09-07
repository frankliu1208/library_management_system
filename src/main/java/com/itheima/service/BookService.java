package com.itheima.service;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import entity.PageResult;
/**
 * 图书接口
 */
public interface BookService {

// search the books that is newly on the shelf
PageResult selectNewBooks(Integer pageNum, Integer pageSize);


//search book according to id
Book findById(String id);

//borrow books
Integer borrowBook(Book book);



//search book applying pagination, conditional search
PageResult search(Book book, Integer pageNum, Integer pageSize);



//add new book
Integer addBook(Book book);


//edit book info
Integer editBook(Book book);


//search borrowed book
PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize);


//return teh book
boolean returnBook(String  id,User user);

//confirm returning
Integer returnConfirm(String id);

}
