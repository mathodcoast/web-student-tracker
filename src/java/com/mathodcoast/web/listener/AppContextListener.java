package com.mathodcoast.web.listener;

import com.mathodcoast.dao.StudentDao;
import com.mathodcoast.dao.StudentDaoImpl;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.sql.DataSource;

@WebListener ()
public class AppContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    @Resource ( name = "jdbc/web_student_tracker" )
    private DataSource dataSource;
    StudentDao studentDao;

    // Public constructor is required by servlet spec
    public AppContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("datasource", dataSource);
        servletContext.log("Database connection initialized.");
    }
}
