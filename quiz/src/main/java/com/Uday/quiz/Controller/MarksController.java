package com.Uday.quiz.Controller;

import com.Uday.quiz.Exception.QuizSubmissionException;
import com.Uday.quiz.Model.Student;
import com.Uday.quiz.Model.Submissions;
import com.Uday.quiz.Repository.StudentRepo;
import com.Uday.quiz.Repository.SubmissionRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.*;

@RestController
public class MarksController {

    @Autowired
    private StudentRepo StdRepo;

    @Autowired
    private SubmissionRepo subRepo;
    private boolean submitted = false;

    private ArrayList<Integer> nums = new ArrayList<Integer>
            (List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));

    public ArrayList<Integer> getNums() {
        Collections.shuffle(nums, new Random());
        return nums;
    }

    public void setNums(ArrayList<Integer> nums) {
        this.nums = nums;
    }

    private static final HashMap<Integer, Integer> answers = new HashMap<>() {{
        put(1, 2);
        put(2, 1);
        put(3, 1);
        put(4, 2);
        put(5, 4);
        put(6, 1);
        put(7, 2);
        put(8, 1);
        put(9, 2);
        put(10, 1);
        put(11, 4);
        put(12, 2);
        put(13, 3);
        put(14, 3);
        put(15, 1);
        put(16, 1);
        put(17, 1);
        put(18, 2);
        put(19, 1);
        put(20, 2);
    }};

    @PostMapping("submit")
    public ResponseEntity<String> submit(@RequestBody List<Integer> ans, Integer _id) {
        try {
            System.out.println("I'm in submit bro!");
            Optional<Submissions> sub = subRepo.findById(_id);

            if (sub.isPresent()) {
                Submissions submission = sub.get();
                submission.setAns(ans);
                subRepo.save(submission);
                System.out.println("ans submitted bro!");
                submitted = true;
            } else {
                throw new QuizSubmissionException("Student not found for ID: " + _id);
            }
            return new ResponseEntity<>("ans submitted!", HttpStatus.OK);
        } catch (QuizSubmissionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("result")
    public ResponseEntity<Integer> Result(Integer _id){
        try{
            Optional<Submissions> sub = subRepo.findById(_id);
            int marks = 0;
            if(sub.isPresent()) {
                List<Integer> ans = sub.get().getAns();
                while(ans.size() != 20){
                    ans.add(0);
                }

                for (Integer i : nums) {
                    if (ans.get(i - 1).equals(answers.get(i))) {
//                        System.out.println(ans.get(i - 1).equals(answers.get(i)));
                        marks += 1;
//                        System.out.println(marks);
                    }
                }
                System.out.println(marks);

                Optional<Student> stdById = StdRepo.findById(_id);

                if (stdById.isPresent()) {
                    Student student = stdById.get();
                    student.setMarks(marks);
                    StdRepo.save(student);
                } else {
                    throw new QuizSubmissionException("Student not found for ID: " + _id);
                }
            }
            return new ResponseEntity<>(marks, HttpStatus.OK);
        } catch (QuizSubmissionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Async
    public void quizDuration(LocalTime now, HttpSession session) {
        LocalTime lt = now.plusMinutes(30);
        try{
            while(true) {
                System.out.println("Im in thread class bro!!");
                if (!submitted) {
                    if (LocalTime.now().compareTo(lt) >= 0) {
                        System.out.println("Time limit");
                        submit(Collections.EMPTY_LIST, (Integer) session.getAttribute("_id"));
                        break;
                    }
                    Thread.sleep(10000);
                }else{
                    System.out.println("Already submitted bro! So thread broken.");
                    break;
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
