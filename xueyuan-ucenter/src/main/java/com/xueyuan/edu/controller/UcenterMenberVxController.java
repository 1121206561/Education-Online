package com.xueyuan.edu.controller;

import com.xueyuan.edu.Utils.ConstantDefineUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class UcenterMenberVxController {

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantDefineUtil.REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "yhj";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名;
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantDefineUtil.APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("callback")
    public void callback(String code, String state, HttpSession session) {
        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);
    }

}
