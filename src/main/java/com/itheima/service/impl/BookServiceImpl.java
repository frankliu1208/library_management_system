package com.itheima.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.mapper.BookMapper;
import com.itheima.service.BookService;
import com.itheima.service.RecordService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * @param pageNum current page
     * @param pageSize number per page
     */
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        // set parameters for pagination
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page=bookMapper.selectNewBooks();
        // page is the object of Page class, Page class can be used as the collection to store the objects returned from BookMapper, selectNewBooks()
        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * search books according to id
     */
    public Book findById(String id) {
        return bookMapper.findById(id);
    }


    /**
     * borrow books,  need to update the current info.
     */
    @Override
    public Integer borrowBook(Book book) {
        // get the complete book info of to-be-borrowed book according to id
        Book b = this.findById(book.getId()+"");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //set today as borrow time
        book.setBorrowTime(dateFormat.format(new Date()));
        //set the status to be has been borrowed
        book.setStatus("1");
        // set price
        book.setPrice(b.getPrice());

        book.setUploadTime(b.getUploadTime());
        return bookMapper.editBook(book);
    }



    /**
     * @param book search conditions inside
     * @param pageNum current page
     * @param pageSize number per page
     */
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page=bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(),page.getResult());
    }




    /**
     * add books
     */
    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //set the time of put on the shelf
        book.setUploadTime(dateFormat.format(new Date()));
        return  bookMapper.addBook(book);
    }



    /**
     * edit book info
     */
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }




    /**
     * used for Admin
     * @param book object with search condition
     * @param user current logged-in user
     * @param pageNum current page
     * @param pageSize number per page
     */
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page;
        // put the current user to the search condition
        book.setBorrower(user.getName());
        // if it is Admin, use selectBorrowed
        if("ADMIN".equals(user.getRole())){
            page= bookMapper.selectBorrowed(book);
        }else {
            //  if it is common user, use selectMyBorrowed
            page= bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(),page.getResult());
    }




    /**
     * Return the book
     * @param id   book id which is going to return
     * @param user   the user who borrowed this book
     */
    @Override
    public boolean returnBook(String id,User user) {
        //  get the complete book info by book id
        Book book = this.findById(id);
        //  check again that the current user is the person who borrows this book
        boolean rb=book.getBorrower().equals(user.getName());
        //  if this is the same person, allow returning
        if(rb){
            //  change the status of book to in returning
            book.setStatus("2");
            bookMapper.editBook(book);
        }
        return rb;
    }


    @Autowired
    private RecordService recordService;


    /**
     * confirm the return
     * @param id  returned book id
     */
    @Override
    public Integer returnConfirm(String id) {

        Book book = this.findById(id);
        // set borrowing record
        Record record = this.setRecord(book);
        // change the status of borrowing to 0, can be borrowed
        book.setStatus("0");
        //  clear the borrowing person
        book.setBorrower("");
        // clear the borrowing time
        book.setBorrowTime("");
        // clear the expected returning time
        book.setReturnTime("");
        Integer count= bookMapper.editBook(book);
        //  if returning confirmation is implemented, then add the borrowing record
        if(count==1){
            return  recordService.addRecord(record);
        }
        return 0;
    }


    /**
     *   set borrowing record according to book info
     * @param book borrowed book info
     */
    private Record setRecord(Book book){
        Record record=new Record();
        //set the name of borrowed book
        record.setBookname(book.getName());

        record.setBookisbn(book.getIsbn());

        record.setBorrower(book.getBorrower());

        record.setBorrowTime(book.getBorrowTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        record.setRemandTime(dateFormat.format(new Date()));
        return record;
    }
}
