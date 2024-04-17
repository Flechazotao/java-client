package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo extends Person {

    private String major;

    private String className;
}
