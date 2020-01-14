package com.example.springcrud.controller;

import com.example.springcrud.exception.ResourceNotFoundException;
import com.example.springcrud.model.Student;
import com.example.springcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    // Get All Student
    @GetMapping("/students")
    public List<Student> getAllNotes() {
        return studentRepository.findAll();
    }

    // Create a new Student
    @PostMapping("/students")
    public Student createNote(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Get a Single Student
    @GetMapping("/students/{id}")
    public Student getNoteById(@PathVariable(value = "id") Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
    }

    // Update a Student
    @PutMapping("/students/{id}")
    public Student updateNote(@PathVariable(value = "id") Long studentId,
                           @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        student.setFirstname(studentDetails.getFirstname());
        student.setLastname(studentDetails.getLastname());
        student.setReg(studentDetails.getReg());
        student.setFee(studentDetails.getFee());

        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }


    // Delete a Student

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }
}
