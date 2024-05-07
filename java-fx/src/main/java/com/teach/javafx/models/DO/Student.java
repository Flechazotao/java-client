package com.teach.javafx.models.DO;

import com.teach.javafx.models.DTO.StudentInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * <p>Student学生表实体类 保存每个学生的信息，
 * <p>Integer studentId 用户表 student 主键 student_id
 * <p>Person person 关联到该用户所用的Person对象，账户所对应的人员信息 person_id 关联 person 表主键 person_id
 * <p>String major 专业
 * <p>String className 班级
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
@Entity
public class Student implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private String major;

    private String className;

    public Student(StudentInfo studentInfo){
        studentId= studentInfo.getNumber();
        major=studentInfo.getMajor();
        className=studentInfo.getClassName();
    }
}
