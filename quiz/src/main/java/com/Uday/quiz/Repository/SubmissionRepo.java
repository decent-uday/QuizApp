package com.Uday.quiz.Repository;

import com.Uday.quiz.Model.Submissions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubmissionRepo extends MongoRepository<Submissions, Integer> {
}
