package com.example.chh.wx.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 检查是否可以考勤
 * @author apple
 */
public interface CheckinService {
    public String validCanCheckIn(int userId, String date);
    public void checkin(HashMap param);

    /**
     * 创建人脸模型
     * @param userId
     * @param path
     */
    public void createFaceModel(int userId, String path);

    public HashMap searchTodayCheckin(int userId);
    public long searchCheckinDays(int userId);

    /**
     * 查询周数据
     * @param param
     * @return
     */
    public ArrayList<HashMap> searchWeekCheckin(HashMap param);

    /**
     * 查询月数据
     * @param param
     * @return
     */
    public ArrayList<HashMap> searchMonthCheckin(HashMap param);
}
