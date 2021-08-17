package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbCity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbCityDao {
    public String searchCode(String city);
}