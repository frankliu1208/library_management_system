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
     *  search for the lending record
     * @param record    searching conditions of lending record
     * @param pageNum   current page
     * @param pageSize   displaying number per page
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
        PageResult pageResult=recordService.searchRecords(record,user,pageNum,pageSize);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("record");
        //  put the data which has been found from database, into the object of ModelAndView
        modelAndView.addObject("pageResult",pageResult);
        // return the searching conditions to the front-end page, fill the input tag in record.jsp
        modelAndView.addObject("search",record);
        // return the current page to the front-end page , i.e. record.jsp, used for pagination displaying
        modelAndView.addObject("pageNum",pageNum);
        //  bring URL of current searching request to the frontend, send this request again when page number changes
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }
    }

