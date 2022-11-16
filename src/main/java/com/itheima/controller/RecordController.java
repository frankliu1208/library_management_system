package com.itheima.controller;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.service.RecordService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     *  search for the borrowing record.  belong to 3th sub-module "borrowing record"
     * @param record : searching conditions of lending record  @param pageNum : current page  @param pageSize: displaying number per page
     */
    @RequestMapping("/searchRecords")
    public ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        // get the user info of current log-in user
        User user = ((User) request.getSession().getAttribute("USER_SESSION"));
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("record");
        // send pageResult object to record.jsp L51, L72
        modelAndView.addObject("pageResult",pageResult);
        // below codes return the searching conditions to the front-end page, fill the input tag in record.jsp L29 -31
        modelAndView.addObject("search",record);
        // below codes return the number of current page to the front-end page ,  record.jsp, L74 used for pagination displaying
        modelAndView.addObject("pageNum",pageNum);
        //  bring URL of current searching request to record.jsp  L76
        modelAndView.addObject("gourl", request.getRequestURI());

        return modelAndView;
    }
    }

