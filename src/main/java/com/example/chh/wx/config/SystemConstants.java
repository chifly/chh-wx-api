package com.example.chh.wx.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 系统的常量，时间
 * @author apple
 */
@Data
@Component
public class SystemConstants {
    public String attendanceStartTime;
    public String attendanceTime;
    public String attendanceEndTime;
    public String closingStartTime;
    public String closingTime;
    public String closingEndTime;
}
