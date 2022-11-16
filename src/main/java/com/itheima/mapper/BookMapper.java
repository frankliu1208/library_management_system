package com.itheima.mapper;
import com.github.pagehelper.Page;
import com.itheima.domain.Book;
import org.apache.ibatis.annotations.*;
/**
 * Interface in DAO layer
 */
public interface BookMapper {
    @Select("SELECT * FROM book where book_status !='3' order by book_uploadtime DESC")
    @Results(id = "bookMap",value = {
            //id is false by default, means it is not primary key
            //column means field in database
            @Result(id = true,column = "book_id",property = "id"),
            @Result(column = "book_name",property = "name"),
            @Result(column = "book_isbn",property = "isbn"),
            @Result(column = "book_press",property = "press"),
            @Result(column = "book_author",property = "author"),
            @Result(column = "book_pagination",property = "pagination"),
            @Result(column = "book_price",property = "price"),
            @Result(column = "book_uploadtime",property = "uploadTime"),
            @Result(column = "book_status",property = "status"),
            @Result(column = "book_borrower",property = "borrower"),
            @Result(column = "book_borrowtime",property = "borrowTime"),
            @Result(column = "book_returntime",property = "returnTime")
    })
    // object of Page includes total pages and rows, see the entity class PageResult, Page class is provided by PageHelper plugin
    // belong to Main page module
    Page<Book> selectNewBooks();


    // belong to Main page module
    @Select("SELECT * FROM book where book_id=#{id}")
    @ResultMap("bookMap")
    //search for book according to id
    Book findById(String id);



    // realizing dynamic sql using annotation ,  need to use <script></script> tags to wrap the sentence
    @Select({ "<script>" +
            "SELECT * FROM book " +
            "where book_status !='3'" +
            "<if test=\"name != null\"> AND  book_name  like  CONCAT('%',#{name},'%')</if>" +
            "<if test=\"press != null\"> AND book_press like  CONCAT('%', #{press},'%') </if>" +
            "<if test=\"author != null\"> AND book_author like  CONCAT('%', #{author},'%')</if>" +
            "order by book_borrowtime" +
            "</script>"
    })
    @ResultMap("bookMap")
     //search books by pagination,  conditional search
    // belong to book management module:  2nd sub-module book lending
    Page<Book> searchBooks(Book book);



    //add new books, only administrator has the right to conduct this function
    // belong to book management module:  2nd sub-module book lending
    Integer addBook(Book book);



    // belong to book management module,  Home page sub-module
    // also belong to book management module:  2nd sub-module book lending, only for administrator
    // also belong to 3rd sub-module current borrowing.  for Returning functions(common user and admin), and ReturnConfirm functions
    Integer editBook(Book book);



    @Select(
            {"<script>" +
                    "SELECT * FROM book " +
                    "where book_borrower=#{borrower}" +
                    "AND book_status ='1'"+
                    "<if test=\"name != null\"> AND  book_name  like  CONCAT('%',#{name},'%')</if>" +
                    "<if test=\"press != null\"> AND book_press like  CONCAT('%', #{press},'%') </if>" +
                    "<if test=\"author != null\"> AND book_author like  CONCAT('%', #{author},'%')</if>" +
                    "or book_status ='2'"+
                    "<if test=\"name != null\"> AND  book_name  like  CONCAT('%',#{name},'%')</if>" +
                    "<if test=\"press != null\"> AND book_press like  CONCAT('%', #{press},'%') </if>" +
                    "<if test=\"author != null\"> AND book_author like  CONCAT('%', #{author},'%')</if>" +
                    "order by book_borrowtime" +
                    "</script>"})
    @ResultMap("bookMap")
    //search books that is borrowed, used for Admiinstrator
    // belongs to third sub-module: current borrowing
    Page<Book> selectBorrowed(Book book);



    @Select({"<script>"  +
            "SELECT * FROM book " +
            "where book_borrower=#{borrower}" +
            "AND book_status in('1','2')"+
            "<if test=\"name != null\"> AND  book_name  like  CONCAT('%',#{name},'%')</if>" +
            "<if test=\"press != null\"> AND book_press like  CONCAT('%', #{press},'%') </if>" +
            "<if test=\"author != null\"> AND book_author like  CONCAT('%', #{author},'%')</if>" +
            "order by book_borrowtime" +
            "</script>"})
    @ResultMap("bookMap")
    //search for borrowed book,  used for normal user
        // belongs to third sub-module: current borrowing
    Page<Book> selectMyBorrowed(Book book);

}
