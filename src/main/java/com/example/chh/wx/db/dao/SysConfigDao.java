package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author apple
 */
@Mapper
public interface SysConfigDao {
    public List<SysConfig> selectAllParam();
}