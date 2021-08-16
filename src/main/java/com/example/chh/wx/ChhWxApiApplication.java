package com.example.chh.wx;

import cn.hutool.core.util.StrUtil;
import com.example.chh.wx.config.SystemConstants;
import com.example.chh.wx.db.dao.SysConfigDao;
import com.example.chh.wx.db.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;


@SpringBootApplication
@ServletComponentScan
@Slf4j
@EnableAsync
public class ChhWxApiApplication {

    @Autowired
    private SysConfigDao sysConfigDao;
    @Autowired
    private SystemConstants constants;

    @Value("${emos.image-folder}")
    private String imageFolder;

    public static void main(String[] args) {
        SpringApplication.run(ChhWxApiApplication.class, args);
    }
    @PostConstruct
    public void init(){
        List<SysConfig> list = sysConfigDao.selectAllParam();
        for (SysConfig sysConfig : list) {
            String key = sysConfig.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value = sysConfig.getParamValue();
            try{
                Field field = constants.getClass().getDeclaredField(key);
                field.set(constants, value);
            } catch (Exception e) {
                log.error("常量注入执行异常" , e);
            }
        }

        new File(imageFolder).mkdirs();
    }
}
