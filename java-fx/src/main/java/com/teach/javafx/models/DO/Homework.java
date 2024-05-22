package com.teach.javafx.models.DO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>Homework 作业实体类
 * <p>Integer homeworkId 主键id
 * <p>String isSubmit 提交状态
 * <p>HomeworkInfo homeworkInfo 对应的作业的信息
 * <p>Student student 对应的学生
 * <p>String isChecked 批改状态
 * <p>String checkTime 批改时间
 * <p>String homeworkScore 作业成绩
 * <p>String submitTime 提交时间
 * <p>String file 学生提交的作业文件
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "homework")
@Entity
public class Homework implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer homeworkId;

    @ManyToOne
    @JoinColumn(name = "homework_info_id")
    private HomeworkInfo homeworkInfo;

    private String isSubmit;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String isChecked;

    private String checkTime;

    private String submitTime;

    private String homeworkScore;

    private String file;
}
