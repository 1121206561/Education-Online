package com.xueyuan.edu.Utils;

import com.xueyuan.edu.entity.UcenterMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author helen
 * @since 2019/3/16
 */
public class JwtUtils {


    public static final String APPSECRET = "guli123456";

    /**
     * 生成jwt
     *
     * @param member
     * @return
     */
    public static String genJsonWebToken(UcenterMember member) {

        if (member == null
                || StringUtils.isEmpty(member.getOpenid())
                || StringUtils.isEmpty(member.getNickname())
                || StringUtils.isEmpty(member.getAvatar())) {
            return null;
        }

        String token = Jwts.builder().setSubject("guli")
                .claim("openid", member.getOpenid())
                .claim("nickname", member.getNickname())
                .claim("avatar", member.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

        return token;
    }

    /**
     * 校验jwt
     *
     * @param token
     * @return
     */
    public static Claims checkJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        return claims;
    }
}
