package com.mathodcoast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter ( filterName = "AuthenticationFilter" ,urlPatterns = "/authentication_filter")
public class AuthenticationFilter implements javax.servlet.Filter {

    private ServletContext context;
    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        HttpSession session = request.getSession(false);

        if (session == null && !(uri.endsWith("html") || uri.endsWith("/login_accept"))){
            this.context.log("Unauthorized access request");
            response.sendRedirect("login.html");
        } else {
            chain.doFilter(req,resp);
        }
    }

    public void destroy() {
    }

}
