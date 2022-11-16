package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.mapper.RecordMapper;
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
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     *  add new lending record
     *  belong to book management module:  4th sub-module lending record
     */
    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    /**
     * search lending records
     * belong to book management module:  3th sub-module borrowing record
     * @param record record conditions
     * @param user   current login user
     * @param pageNum current page
     * @param pageSize number per page
     */
    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        //  if it is not admin, set the borrower to be current login user
        if(!"ADMIN".equals(user.getRole())){
            record.setBorrower(user.getName());
        }
        Page<Record> page= recordMapper.searchRecords(record);
        return new PageResult(page.getTotal(),page.getResult());
    }

}
