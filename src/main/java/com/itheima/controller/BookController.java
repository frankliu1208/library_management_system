package com.itheima.controller;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.service.BookService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
Book information Controller
 */
@Controller
@RequestMapping("/book")
public class BookController {
    //inject bookService object
    @Autowired
    private BookService bookService;

    /**
     *  check the books that is newly on the shelf
     *  belong to Main page module
     */
    @RequestMapping("/selectNewbooks")
    public ModelAndView selectNewbooks() {
        // pageNum means current page
        int pageNum = 1;
        int pageSize = 5;
        PageResult pageResult = bookService.selectNewBooks(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books_new");
        // below codes go to books_new.jsp L41
        modelAndView.addObject("pageResult", pageResult);
        return modelAndView;
    }


    /**
     * search books according to id,  the request come from findBookById function L71 in my.js
     * belong to Main page module
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Result<Book> findById(String id) {
        try {
            Book book=bookService.findById(id);
            if(book==null){
                return new Result(false,"Search failed！");
            }
            return new Result(true,"Search succeeded",book);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Search failed！");
        }
    }




    /**
     * borrow the books
     * belong to Main page module
     */
    @ResponseBody
    @RequestMapping("/borrowBook")
    public Result borrowBook(Book book, HttpSession session) {
        // get the user name of current login
        String pname = ((User) session.getAttribute("USER_SESSION")).getName();
        book.setBorrower(pname);
        try {
            // conduct borrowing according to book id and user
            Integer count = bookService.borrowBook(book);
            if (count != 1) {
                return new Result(false, "Borrow failed!");
            }
            return new Result(true, "Borrow succeeded，please get the book at the entrance of the library !");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Borrow failed!");
        }
    }


    /**
     * search by pagination and condition, condition is put into book object
     *  belong to book management module:  2nd sub-module book lending
     */
    @RequestMapping("/search")
    public ModelAndView search(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = bookService.search(book, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books");
        modelAndView.addObject("pageResult", pageResult);  // put the data got from database into modelAndView object,  send to books.jsp, L68
        modelAndView.addObject("search", book); // bring the condition of searching to the frontend, fill the input tag of searching, books.jsp  L42
        modelAndView.addObject("pageNum", pageNum); // bring the current page to the frontend, for display of pagination
        modelAndView.addObject("gourl", request.getRequestURI());// bring URL of current searching request to the frontend, send this request again when page number changes
        return modelAndView;
    }



    /**
     * add books,  parameter book comes from the front end, bringing the newly-added book info
     *  put the result and info into the object of Result
     *  belong to book management module:  2nd sub-module book lending, only for administrator
     */
    @ResponseBody
    @RequestMapping("/addBook")
    public Result addBook(Book book) {
        try {
            Integer count=bookService.addBook(book);
            if(count!=1){
                return new Result(false, "Failed to add new book!");
            }
            return new Result(true, "Add new book succeeded!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "Failed to add new book!");
        }
    }


    /**
     * edit book information
     *
     * belong to book management module:  2nd sub-module book lending, only for administrator
     */
    @ResponseBody
    @RequestMapping("/editBook")
    public Result editBook(Book book) {
        try {
            Integer count= bookService.editBook(book);
            if(count!=1){
                return new Result(false, "Edit failed!");
            }
            return new Result(true, "Edit succeeded!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "Edit failed!");
        }
    }




    /**
     *   belongs to third sub-module: current borrowing
     *   search and displaying current borrowing information (both for admin and common user)
     * @param pageNum  current page
     * @param pageSize number per page
     */
    @RequestMapping("/searchBorrowed")
    public ModelAndView searchBorrowed(Book book,Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //get the current logged-in user
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        PageResult pageResult = bookService.searchBorrowed(book,user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_borrowed");

        modelAndView.addObject("pageResult", pageResult);  //  put the data from database into the object
        modelAndView.addObject("search", book); // put the condition into book, bring to frontend, fill the input tags of searching
        modelAndView.addObject("pageNum", pageNum); // bring current page to the frontend, used for pagination display
        modelAndView.addObject("gourl", request.getRequestURI()); // // bring URL of current searching request to the frontend, send this request again when page number changes
        return modelAndView;
    }


    /**
     * return the book
     * belongs to third sub-module: current borrowing
     */
    @ResponseBody
    @RequestMapping("/returnBook")
    public Result returnBook(String id, HttpSession session) {
        //get the current user info
        User user = (User) session.getAttribute("USER_SESSION");
        try {
            boolean flag = bookService.returnBook(id, user);
            if (!flag) {
                return new Result(false, "Failed to return!");
            }
            return new Result(true, " Returning in process，please go to the entrance of library to return the book!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "Failed to return!");
        }
    }




    /**
     * Confirm book returning
     *  belongs to third sub-module: current borrowing
     */
    @ResponseBody
    @RequestMapping("/returnConfirm")
    public Result returnConfirm(String id) {
        try {
            Integer count=bookService.returnConfirm(id);
            if(count!=1){
                return new Result(false, "confirm failed!");
            }
            return new Result(true, "confirm succeeded!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "confirm failed!");
        }
    }

    }

