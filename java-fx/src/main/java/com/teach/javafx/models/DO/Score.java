package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Score 成绩表实体类  保存成绩的的基本信息信息，
 * <p>Integer scoreId 人员表 score 主键 score_id
 * <p>Student student 关联学生 student_id 关联学生的主键 student_id
 * <p>Course course 关联课程 course_id 关联课程的主键 course_id
 * <p>Integer mark 成绩
 * <p>Integer ranking 排名
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score")
@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer mark;

    private Integer ranking;

}