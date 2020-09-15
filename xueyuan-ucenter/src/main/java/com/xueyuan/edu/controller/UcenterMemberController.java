package com.xueyuan.edu.controller;


import com.xueyuan.edu.R;
import com.xueyuan.edu.Utils.JwtUtils;
import com.xueyuan.edu.entity.UcenterMember;
import com.xueyuan.edu.service.UcenterMemberService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/edu/ucenter-member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/getNumberByDay/{day}")
    public R getNumberByDay(@PathVariable("day") String day) {
        Integer result = ucenterMemberService.getNumberByDay(day);
        return R.ok().data("item", result);
    }

    @GetMapping("/getInfo/{token}")
    public R getInfo(@PathVariable("token") String token) {
        Claims claims = JwtUtils.checkJwt(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String openid = (String) claims.get("openid");
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setAvatar(avatar);
        ucenterMember.setOpenid(openid);
        return R.ok().data("item", ucenterMember);
    }

}

