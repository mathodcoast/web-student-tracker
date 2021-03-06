package com.mathodcoast.web.servlet;

import com.mathodcoast.dao.*;
import com.mathodcoast.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet ( name = "StudentControllerServlet", urlPatterns = "/students" )
public class StudentControllerServlet extends HttpServlet {

    private StudentDao studentDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ServletContext servletContext = config.getServletContext();
            studentDao = new StudentDaoImpl((DataSource) servletContext.getAttribute("datasource"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        try {
            String theCommand = request.getParameter("command");

            if (theCommand == null) {
                theCommand = "LIST";
            }
            switch (theCommand) {
                case "LIST":
                    listStudents(request,response);
                    break;
                case "LOAD":
                    loadStudent(request,response);
                    break;
                case "SEARCH":
                    searchStudent(request,response);
                    break;
                case "DELETE":
                    deleteStudent(request,response);
                    break;
                default:
                    listStudents(request,response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) {
        try {
            String command = request.getParameter("command");
            switch (command) {
                case ("ADD"):
                    addStudent(request,response);
                    break;
                case "UPDATE":
                    updateStudent(request,response);
                    break;
                default:
                    listStudents(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listStudents(HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<Student> studentList = studentDao.getStudentsList();

        request.setAttribute("STUDENT_LIST",studentList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request,response);
    }

    private void addStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Student student = getStudentFromRequestWithoutId(request);

        studentDao.addStudent(student);

        response.sendRedirect(request.getContextPath() + "/students?command=LIST");
    }

    private void loadStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        Student student = studentDao.getStudentById(studentId);

        request.setAttribute("THE_STUDENT",student);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request,response);
    }

    private void updateStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Student student = getStudentFromRequestWithoutId(request);
        String studentId = request.getParameter("studentId");

        student.setId(Integer.parseInt(studentId));

        studentDao.updateStudent(student);

        response.sendRedirect(request.getContextPath() + "/students?command=LIST");
    }

    private void searchStudent(HttpServletRequest request,HttpServletResponse response) {
        String searchValue = request.getParameter("searchValue");

        if (searchValue != null && searchValue.trim().length() > 0) {
            List<Student> searchedStudentList = studentDao.searchStudentsByValue(searchValue);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");

            request.setAttribute("STUDENT_LIST",searchedStudentList);

            try {
                dispatcher.forward(request,response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/students?command=LIST");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        studentDao.deleteStudentById(studentId);

        response.sendRedirect(request.getContextPath() + "/students?command=LIST");
    }

    private Student getStudentFromRequestWithoutId(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("emailName");

        return new Student(firstName,lastName,email);
    }
}
