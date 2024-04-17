package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Course 课程表实体类  保存课程的的基本信息信息，
 * <p>Integer courseId 主键 course_id
 * <p>String number 课程编号
 * <p>String name 课程名称
 * <p>Integer credit 学分
 * <p>Course preCourse 前序课程 pre_course_id 关联前序课程的主键 course_id
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String number;
    private String name;
    private Integer credit;
    @ManyToOne
    private Course preCourse;
    private String coursePath;
}
