package com.xueyuan.edu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends ZuulFilter {
    //制定过滤的类型  pre error post routing
    @Override
    public String filterType() {
        return "pre";
    }

    //多个过滤器的执行顺序
    @Override
    public int filterOrder() {
        return 1;
    }

    //是否放行 如果是false 则不执行run方法
    @Override
    public boolean shouldFilter() {
        //如果使用网关访问路径中带有MyUri则跳转到run方法中去
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        String MyUri = "/api/ucenter/wb";
        if (!StringUtils.isEmpty(requestURI) && requestURI.contains(MyUri)) {
            return true;
        } else {
            return false;
        }
    }

    //过滤器的具体执行代码
    @Override
    public Object run() throws ZuulException {
        //如果访问参数中带有token值则拒绝访问  否则放行
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getParameter("token");
        if (!StringUtils.isEmpty(token)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.URI_TOO_LONG.value());
        }
        return null;
    }
}
