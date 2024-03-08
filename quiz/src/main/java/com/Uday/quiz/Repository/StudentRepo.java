package com.Uday.quiz.Repository;

import com.Uday.quiz.Model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends MongoRepository<Student, Integer> {
}
