package com.xueyuan.edu.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//定义该项目中的所有常量 , 不用写在每个代码中 方便维护
@Component
public class ConstantDefineUtil implements InitializingBean {
    //读取配置文件中的数据
    @Value("${wx.open.app_id}")
    private String app_id;

    @Value("${wx.open.app_secret}")
    private String app_secret;

    @Value("${wx.open.redirect_url}")
    private String redirect_url;

    //读取配置文件中的数据
    @Value("${wb.open.app_id}")
    private String app_id2;

    @Value("${wb.open.app_secret}")
    private String app_secret2;

    @Value("${wb.open.redirect_url}")
    private String redirect_url2;


    //定义常量
    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;
    public static String APP_ID2;
    public static String APP_SECRET2;
    public static String REDIRECT_URL2;

    @Override
    public void afterPropertiesSet() throws Exception {
        //给常量赋值
        APP_ID = app_id;
        APP_SECRET = app_secret;
        REDIRECT_URL = redirect_url;
        APP_ID2= app_id2;
        APP_SECRET2 = app_secret2;
        REDIRECT_URL2 = redirect_url2;
    }
}
