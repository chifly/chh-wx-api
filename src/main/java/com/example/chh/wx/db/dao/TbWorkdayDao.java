package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbWorkday;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbWorkdayDao {
    /**
     * 查询工作日
     * @return
     */
    public Integer searchTodayIsWorkday();

    /**
     * 特殊工作日
     * @param param
     * @return
     */
    public ArrayList<String> searchWorkdayInRange(HashMap param);
}