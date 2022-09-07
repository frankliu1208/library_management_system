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
     * check the books that is newly on the shelf
     */
    @RequestMapping("/selectNewbooks")
    public ModelAndView selectNewbooks() {
        // check first page
        int pageNum = 1;
        int pageSize = 5;
        PageResult pageResult = bookService.selectNewBooks(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books_new");
        modelAndView.addObject("pageResult", pageResult);
        return modelAndView;
    }



    /**
     * search books according to id
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
// controller为什么返回Result对象，谁接收了这个返回值??



    /**
     * borrow the books
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
     * search by pagination and condition
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
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", book);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }



    /**
     * add books,  parameter book comes from the front end, bringing the newly-added book info
     *  put the result and info into the object of Result
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
     * edit book info
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
    //  put the data from database into the object
    modelAndView.addObject("pageResult", pageResult);
    //将查询的参数返回到页面，用于回显到查询的输入框中
    modelAndView.addObject("search", book);
    //将当前页码返回到页面，用于分页插件的分页显示
    modelAndView.addObject("pageNum", pageNum);
    //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
    modelAndView.addObject("gourl", request.getRequestURI());
    return modelAndView;
}




/**
 * return the book
 * @param id   book id
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
 * @param id book id that is about to return
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

