package com.mathodcoast.dao;

import com.mathodcoast.model.*;

import java.util.List;

public interface StudentDao {
    List<Student> getStudentsList();
    void addStudent(Student student);
    Student getStudentById(int studentId);
    void updateStudent(Student student);
    void deleteStudentById(int studentId);
    List<Student> searchStudentsByValue(String searchValue);
}
