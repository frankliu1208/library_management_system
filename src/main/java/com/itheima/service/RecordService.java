package com.itheima.service;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import entity.PageResult;

/**
 * borrowing record interface
 */
public interface RecordService {
    //add new record of borrowing
    // belong to book management module:  4th sub-module lending record
    Integer addRecord(Record record);

    //search for borrowing record
    // belong to book management module:  4th sub-module lending record
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}
