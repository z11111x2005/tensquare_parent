package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String BEARER = "Bearer ";
    private static final String ADMIN = "admin";
    private static final String USER = "user";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 无论如何都放行,具体能不能操作还是在具体的操作中判断
        // 拦截器只是负责把有请求头中包含token的令牌进行解析
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header)) {
            if (!header.startsWith(BEARER)) {
                throw new RuntimeException("权限不足！");
            }
            // 得到token
            String token = header.substring(7);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                // 如果是admin
                if (StringUtils.isNotEmpty(roles) && roles.equals(ADMIN)) {
                    request.setAttribute("claims_admin", token);
                }
                // 如果是user
                if (StringUtils.isNotEmpty(roles) && roles.equals(USER)) {
                    request.setAttribute("claims_user", token);
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌不正确！");
            }
        }
        return true;
    }
}
