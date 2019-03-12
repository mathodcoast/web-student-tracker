package com.mathodcoast.dao;

import com.mathodcoast.exception.*;
import com.mathodcoast.model.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentDaoImpl implements StudentDao {

    private final String SELECT_ALL_STUDENTS_SQL = "SELECT * FROM student ORDER BY last_name";
    private final String INSERT_STUDENT_SQL = "INSERT INTO student (first_name, last_name, email) VALUES (?,?,?)";
    private final String GET_STUDENT_BY_ID_SQL = "SELECT * FROM student WHERE id=?";
    private final String UPDATE_STUDENT_SQL = "UPDATE student SET first_name=?, last_name=?, email=? WHERE id=?";
    private final String SEARCH_STUDENT_BY_NAME_SQL = "SELECT * FROM student WHERE first_name=? OR last_name=?";
    private final String DELETE_STUDENT_SQL = "DELETE FROM student WHERE id=?";


    @Resource ( name = "jdbc/web_student_tracker" )
    private DataSource dataSource;

    public StudentDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Student> getStudentsList() {
        List<Student> students;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_STUDENTS_SQL);
            students = getStudentsListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException("Could not get the students list.",e);
        }
        return students;
    }

    @Override
    public void addStudent(Student student) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL);
            setStudentToStatement(student,preparedStatement);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DaoOperationException("Could not add the student",e);
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        try (Connection connection = dataSource.getConnection()) {
            Student student;
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STUDENT_BY_ID_SQL);
            preparedStatement.setInt(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = getStudentFromResultSetRow(resultSet);
            } else {
                throw new DaoOperationException("The student with id:" + studentId + "is not existing in DB.");
            }
            return student;
        } catch (SQLException e) {
            throw new DaoOperationException("Could not get student by Id: " + studentId,e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        Objects.requireNonNull(student);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL);
            setStudentToStatement(student,preparedStatement);
            preparedStatement.setInt(4,student.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoOperationException("Could not update student",e);
        }
    }

    @Override
    public void deleteStudentById(int studentId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_SQL);
            preparedStatement.setInt(1,studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoOperationException("Could not delete student by Id: " + studentId,e);
        }
    }

    @Override
    public List<Student> searchStudentsByValue(String searchValue) {
        List<Student> studentList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_STUDENT_BY_NAME_SQL);
            preparedStatement.setString(1, searchValue);
            preparedStatement.setString(2, searchValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            studentList = getStudentsListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoOperationException("Could not find student by value: " + searchValue);
        }
        return studentList;
    }

    private List<Student> getStudentsListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            Student student = getStudentFromResultSetRow(resultSet);
            students.add(student);
        }
        return students;
    }

    private Student getStudentFromResultSetRow(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getInt("id"),
                resultSet.getString("last_name"),
                resultSet.getString("first_name"),
                resultSet.getString("email")
        );
    }

    private void setStudentToStatement(Student student,PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,student.getFirstName());
        preparedStatement.setString(2,student.getLastName());
        preparedStatement.setString(3,student.getEmail());
    }
}
