package com.teach.javafx.models.DO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Homework 作业实体类
 * <p>Integer homeworkId 主键id
 * <p>String homeworkScore 作业成绩
 * <p>String isSubmit 是否提交
 * <p>Course course 对应的课程
 * <p>Student student 对应的学生
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Homework implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer homeworkId;

    private String homeworkScore;

    private String isSubmit;
    @ManyToOne
    private Course course;

    @ManyToOne
    private Student student;
}
