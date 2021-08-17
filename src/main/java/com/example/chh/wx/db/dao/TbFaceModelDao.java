package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFaceModelDao {
    public String searchFaceModel(int userId);
    public void insert(TbFaceModel tbFaceModel);
    public int deleteFaceModel(int userId);
}