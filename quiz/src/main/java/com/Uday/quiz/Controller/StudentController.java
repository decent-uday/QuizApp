package com.Uday.quiz.Controller;

import com.Uday.quiz.Exception.DuplicateEmailException;
import com.Uday.quiz.Exception.StudentAuthenticationException;
import com.Uday.quiz.Model.Student;
import com.Uday.quiz.Repository.StudentRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Validated
public class StudentController {

    @Autowired
    private StudentRepo StdRepo;
    @Autowired
    private QuizController qc;

    @GetMapping("allStuds")
    public List allStuds(){
        return StdRepo.findAll();
    }

    @PostMapping("enroll")
    public ResponseEntity<List<Student>> enroll(@RequestBody @Valid Student student) {
        try {
            for (Student stud : StdRepo.findAll()) {
                if (stud.getEmail().equals(student.getEmail())) {
                    throw new DuplicateEmailException("Email already exists");
                }
            }
            List<Student> cred = List.of(StdRepo.save(student));
            return ResponseEntity.ok(cred);
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("attempt")
    public ResponseEntity<List<HashMap<String, Object>>> attempt(String email, String password) {
        try {
            List<Student> creds = StdRepo.findAll();
            boolean authenticated = false;

            for (Student student : creds) {
                if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    authenticated = true;
                    break;
                }
            }

            if (!authenticated) {
                throw new StudentAuthenticationException("Authentication failed");
            }

            return qc.questionPaper();
        } catch (StudentAuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}