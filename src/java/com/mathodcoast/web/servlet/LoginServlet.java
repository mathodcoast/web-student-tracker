package com.mathodcoast.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet ( name = "LoginServlet", urlPatterns = "/login_accept")
public class LoginServlet extends HttpServlet {
    private static final long SERIAL_VERSION_UID = 1L;
    private final String validUser = "mathodcoast";
    private final String validPassword = "myhome";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String user = request.getParameter("user");
        String password = request.getParameter("password");

        log("User=" + user + ":: Password=" + password);

        if (user.equals(validUser) && password.equals(validPassword)){
            HttpSession session = request.getSession();
            session.setAttribute("user", validUser);
            session.setMaxInactiveInterval(30*60);
            Cookie userCookie = new Cookie("user", validUser);
            userCookie.setMaxAge(30*60);
            response.addCookie(userCookie);
            response.sendRedirect("students");
//            response.sendRedirect(request.getContextPath()+ "/students");
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            requestDispatcher.include(request,response);
        }
    }
}
