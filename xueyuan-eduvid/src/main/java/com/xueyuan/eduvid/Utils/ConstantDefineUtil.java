package com.xueyuan.eduvid.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//定义该项目中的所有常量 , 不用写在每个代码中 方便维护
@Component
public class ConstantDefineUtil implements InitializingBean {
    //读取配置文件中的数据
    @Value("${TengXun.Cos_secretid}")
    private String cos_secretid;

    @Value("${TengXun.Cos_secretkey}")
    private String cos_secretkey;

    //定义常量
    public static String COS_SECRETID;
    public static String COS_SECRETKEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        //给常量赋值
        COS_SECRETID = cos_secretid;
        COS_SECRETKEY = cos_secretkey;
    }
}
