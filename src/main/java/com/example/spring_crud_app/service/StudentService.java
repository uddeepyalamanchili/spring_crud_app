package com.example.spring_crud_app.service;

import java.util.List;
import com.example.spring_crud_app.model.Student;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Long studentId);
    Student createStudent(Student student);
    Student updateStudent(Long studentId, Student studentDetails);
    void deleteStudent(Long studentId);
}
