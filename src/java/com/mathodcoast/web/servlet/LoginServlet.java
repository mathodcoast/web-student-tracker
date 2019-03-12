package com.mathodcoast.web.servlet;

import com.mathodcoast.dao.AdminDao;
import com.mathodcoast.dao.AdminDaoImpl;
import com.mathodcoast.model.Admin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet ( name = "LoginServlet", urlPatterns = "/login_accept")
public class LoginServlet extends HttpServlet {
    AdminDao adminDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        DataSource datasource = (DataSource) servletContext.getAttribute("datasource");
        adminDao = new AdminDaoImpl(datasource);
        servletContext.log("AdminDao initialized");
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String user = request.getParameter("user");
        String password = request.getParameter("password");
        List<Admin> admins = adminDao.getAllAdmins();
        log("User=" + user + ":: Password=" + password);
        for(Admin admin: admins){
            if (admin.getUsername().equals(user) && admin.getPassword().equals(password)){
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30*60);
                Cookie userCookie = new Cookie("user", user);
                userCookie.setMaxAge(30*60);
                response.addCookie(userCookie);
                response.sendRedirect("students");

            }else {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>Either username or password is wrong.</font>");
                requestDispatcher.include(request,response);
            }
        }
    }
}
