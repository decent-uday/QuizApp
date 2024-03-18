package com.Uday.quiz.Controller;

import com.Uday.quiz.Model.Questions;
import com.Uday.quiz.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class QuizController {

    @Autowired
    private QuestionRepo quizRepo;

    @Autowired
    private MarksController mc;

    @GetMapping("allQns")
    public ResponseEntity<List<Questions>> allQns() {
        try {
            List<Questions> questions = quizRepo.findAll(Sort.by("_id"));
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("questionPaper")
    public ResponseEntity<List<HashMap<String, Object>>> questions() {
        try {
            List<Questions> quiz = quizRepo.findAll(Sort.by("_id"));
            List<HashMap<String, Object>> qns = new ArrayList<>();

            for (Questions question : quiz) {
                HashMap<String, Object> qn = new HashMap<>();
                qn.put("_id", question.get_id());
                qn.put("Question", question.getQuestion());
                qn.put("Options", question.getOptions());
                qns.add(qn);
            }
            return ResponseEntity.ok(qns);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<HashMap<String, Object>>> questionPaper() {
        try {
            List<Questions> quiz = quizRepo.findAll(Sort.by("_id"));
            List<HashMap<String, Object>> qns = new ArrayList<>();
            ArrayList<Integer> order = mc.getNums();

            for (Integer j : order) {
                HashMap<String, Object> qn = new HashMap<>();
                qn.put("_id", quiz.get(j - 1).get_id());
                qn.put("Question", quiz.get(j - 1).getQuestion());
                qn.put("Options", quiz.get(j - 1).getOptions());
                qns.add(qn);
            }

            return ResponseEntity.ok(qns);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}