package com.qbms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤中文乱码
@WebFilter(filterName = "CharacterEncodingFilter",value = "/*")
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String requestURI = req.getRequestURI();
        System.out.println("请求路径："+requestURI);
        //对css和js文件进行判断，true则直接放行
        if (requestURI.contains(".css") || requestURI.contains(".js")){
            chain.doFilter(request,response);
            return;
        }

        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        //resp.setContentType("text/html;charset=utf8");
        chain.doFilter(request, response);

    }
}
