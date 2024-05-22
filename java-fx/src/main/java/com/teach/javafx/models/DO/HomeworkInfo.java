package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>HomeworkInfo 作业信息实体类 用来记录作业的要求和对应的课程
 * <p>Integer homeworkInfoId 主键id
 * <p>Course course 对应的课程
 * <p>String demand 作业要求
 * <p>String name 作业名称
 * <p>String file 作业文件
 * <p>String time 发布时间</p>
 */
@Data
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

    private String name;

    private String time;

    private String file;


}
