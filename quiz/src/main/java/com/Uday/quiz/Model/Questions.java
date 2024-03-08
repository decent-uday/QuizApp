package com.Uday.quiz.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Questions {

    @Id
    private Integer _id;
    private String question;
    private List<String> options;
    private String answer;
}
