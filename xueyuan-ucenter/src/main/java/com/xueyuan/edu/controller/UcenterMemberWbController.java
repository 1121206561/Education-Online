package com.xueyuan.edu.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edu.Utils.ConstantDefineUtil;
import com.xueyuan.edu.Utils.HttpUtil;
import com.xueyuan.edu.Utils.JwtUtils;
import com.xueyuan.edu.entity.UcenterMember;
import com.xueyuan.edu.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wb")
@CrossOrigin
public class UcenterMemberWbController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("login")
    public String login(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://api.weibo.com/oauth2/authorize" +
                "?client_id=%s" +
                "&redirect_uri=%s";
        // 回调地址
        String redirectUrl = ConstantDefineUtil.REDIRECT_URL2; //获取业务服务器重定向地址
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantDefineUtil.APP_ID2,
                redirectUrl);
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("callback")
    public String callback(String code, HttpSession session) {
        Map<String, String> params = new HashMap<>(5);
        //不知道url填什么可以看文档：https://open.weibo.com/wiki/Oauth2/access_token
        String url = "https://api.weibo.com/oauth2/access_token";
        //申请应用时分配的AppKey
        params.put("client_id", ConstantDefineUtil.APP_ID2);
        //申请应用时分配的AppSecret
        params.put("client_secret", ConstantDefineUtil.APP_SECRET2);
        //请求的类型，填写authorization_code
        params.put("grant_type", "authorization_code");
        //调用authorize获得的code值
        params.put("code", code);
        //回调地址，需需与注册应用里的回调地址一致。
        params.put("redirect_uri", ConstantDefineUtil.REDIRECT_URL2);
        try {
            String result = HttpUtil.post(url, params);
            //获取登录用户的uid和access_token
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            //现在数据库查询是否已经注册过了该用户,如果注册过则直接进行跳转
            QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", jsonObject.get("uid"));
            UcenterMember ucenterMember = ucenterMemberService.getOne(queryWrapper);
            if (ucenterMember == null) {
                //得到用户的基本信息
                url = "https://api.weibo.com/2/users/show.json";
                String getUserInfo = HttpUtil.get(url, jsonObject.get("access_token"), jsonObject.get("uid"));
                //将用户信息转换为json字符串
                JSONObject jsonObjectUser = (JSONObject) JSONObject.parse(getUserInfo);
                //将用户信息保存到数据库中
                ucenterMember.setAvatar(jsonObjectUser.get("profile_image_url").toString());
                ucenterMember.setOpenid(jsonObjectUser.get("id").toString());
                ucenterMember.setNickname(jsonObjectUser.get("name").toString());
                ucenterMemberService.save(ucenterMember);
            }
            String token = JwtUtils.genJsonWebToken(ucenterMember);
            return "redirect:http://localhost:3000?token=" + token;
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:http://localhost:3000";
        }
    }
}