package com.mathodcoast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter ( filterName = "RequestLoggingFilter" )
public class RequestLoggingFilter implements Filter {

    private  ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("RequestLoggingFilter initialized");
    }

    public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()){
            String name = params.nextElement();
            String value = request.getParameter(name);
            this.context.log(request.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie: cookies){
                this.context.log(request.getRemoteAddr() + "::Cookie::{" + cookie.getName() + " , " + cookie.getValue() + "}");
            }
        }
        chain.doFilter(req,resp);
    }

    public void destroy() {
    }

}
