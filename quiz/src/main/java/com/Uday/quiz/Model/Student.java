package com.Uday.quiz.Model;

import com.Uday.quiz.Model.Validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {

    @Id
    Integer _id;
    @NotEmpty(message = "Name can't be empty")
    String name;
    @Email(message = "Enter a valid email!!")
    String email;
    @ValidPassword(message = "Password should contain at least one lowercase letter, one digit, and one special character")
    String password;
    Integer marks;
}
