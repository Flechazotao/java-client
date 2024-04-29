package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>HomeworkInfo 作业信息实体类 用来记录作业的要求和对应的课程
 * <p>Integer homeworkInfoId 主键id
 * <p>Course course 对应的课程
 * <p>String demand 作业要求
 * <p>String time 发布时间</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "homework_info")
@Entity
public class HomeworkInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer homeworkInfoId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String demand;

    private String time;


}
