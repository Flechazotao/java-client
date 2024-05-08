package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo extends Person{
    private String title;

    private String degree;

    public TeacherInfo(Teacher teacher){
        this.title=teacher.getTitle();
        this.degree=teacher.getDegree();
        Person person= teacher.getPerson();
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
    }
}
