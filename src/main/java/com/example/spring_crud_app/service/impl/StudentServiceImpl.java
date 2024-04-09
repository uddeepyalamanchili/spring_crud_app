package com.example.spring_crud_app.service.impl;

import com.example.spring_crud_app.model.Student;
import com.example.spring_crud_app.repository.StudentRepository;
import com.example.spring_crud_app.service.StudentService;
import com.example.spring_crud_app.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

	@Override
	public Student getStudentById(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new ResourceNotFoundException("Student", "Id", studentId);
        }
	}

	@Override
	public Student createStudent(Student student) {
        return studentRepository.save(student);
	}

	@Override
	public Student updateStudent(Long studentId, Student studentDetails) {

        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
        
        existingStudent.setFirstName(studentDetails.getFirstName());
        existingStudent.setLastName(studentDetails.getLastName());
        existingStudent.setEmail(studentDetails.getEmail());
        
        studentRepository.save(existingStudent);
        return existingStudent;
	}

	@Override
	public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
        
        studentRepository.delete(student);		
	}
	
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
