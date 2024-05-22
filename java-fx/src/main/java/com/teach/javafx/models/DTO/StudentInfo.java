package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
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

    private String GPA;

    public StudentInfo(Student student){
        this.major=student.getMajor();
        this.className=student.getClassName();
        Person person= student.getPerson();
        this.setNumber(person.getNumber());
        this.setCard(person.getCard());
        this.setDept(person.getDept());
        this.setAddress(person.getAddress());
        this.setEmail(person.getEmail());
        this.setBirthday(person.getBirthday());
        this.setGender(person.getGender());
        this.setIntroduce(person.getIntroduce());
        this.setName(person.getName());
        this.setPersonId(person.getPersonId());
        this.setPhone(person.getPhone());
        this.setPoliticalStatus(person.getPoliticalStatus());
        this.setType(person.getType());
        this.setGPA(student.getGPA());
    }
}
