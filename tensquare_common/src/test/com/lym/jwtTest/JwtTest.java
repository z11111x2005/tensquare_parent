package com.lym.jwtTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JwtTest {

    /**
     * 创建jwt
     */
    @Test
    public void CreateJwt() {
        JwtBuilder builder = Jwts.builder()
                .setId("666")
                .setSubject("用户名")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .setExpiration(new Date(new Date().getTime() + 60000))
                .claim("role", "admin");
        // eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLnlKjmiLflkI0iLCJpYXQiOjE1NTA4MTUzMDksImV4cCI6MTU1MDgxNTM2OSwicm9sZSI6ImFkbWluIn0.-rGR9ddDz6z4rfUpk3pirogaFCeUoLdOff_Kh0pMFWg
        System.out.println(builder.compact());
    }

    /**
     * 解析jwt
     */
    @Test
    public void parseJwt() {
        Claims body = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLnlKjmiLflkI0iLCJpYXQiOjE1NTA4MTUzMDksImV4cCI6MTU1MDgxNTM2OSwicm9sZSI6ImFkbWluIn0.-rGR9ddDz6z4rfUpk3pirogaFCeUoLdOff_Kh0pMFWg")
                .getBody();
        // 用户id:666
        // 用户名:用户名
        // 登录时间:2019-02-22 14:01:49
        // 过期时间:2019-02-22 14:02:49
        // 用户角色:admin
        System.out.println("用户id:" + body.getId());
        System.out.println("用户名:" + body.getSubject());
        System.out.println("登录时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(body.getIssuedAt()));
        System.out.println("过期时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(body.getExpiration()));
        System.out.println("用户角色:" + body.get("role"));
    }
}
