package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbHolidays;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbHolidaysDao {
    public Integer searchTodayIsHolidays();

    /**
     * 查询特殊节假日
     * @param param
     * @return
     */
    public ArrayList<String> searchHolidaysInRange(HashMap param);
}