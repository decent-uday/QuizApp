package com.Uday.quiz.Repository;

import com.Uday.quiz.Model.Questions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends MongoRepository<Questions, Integer> {
}
