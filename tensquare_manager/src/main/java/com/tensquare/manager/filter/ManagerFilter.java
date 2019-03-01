package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    private static final String BEARER = "Bearer ";
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 过滤器类型:1.pre请求被路由之前调用 2.route:路由请求时候被调用
     * 3.post:在route和error过滤器之后被调用 4.error:处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序,数字越小,表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启,true:开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作,return任何Object的值都表示继续执行
     * setsendzullResponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // request域
        HttpServletRequest request = currentContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        // 若果是登录请求就放行
        if(request.getRequestURI().indexOf("login")>0){
            return null;
        }
        // 得到头信息
        String header = request.getHeader("Authorization");
        if(StringUtils.isNotEmpty(header)){
            if(header.startsWith(BEARER)){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles.equals("admin")){
                        // 把头信息转发下去,并且放行
                        currentContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    // 终止运行
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        // 终止运行
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
