package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>Course 课程表实体类  保存课程的的基本信息信息，
 * <p>Integer courseId 主键 course_id
 * <p>String number 课程编号
 * <p>String name 课程名称
 * <p>String credit 学分
 * <p>String courseTime 课程时间（一天中的时间）
 * <p>String teacherName 授课老师
 * <p>String courseBeginWeek 课程开始于第几周
 * <p>String courseEndWeek 课程结束于第几周
 * <p>String wayOfTest 考核方式
 * <p>String location 上课地点
 * <p>Course preCourse 前序课程 pre_course_id 关联前序课程的主键 course_id
 * <p>String type 课程类型
 * <p>String file 相关文件的路径
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String number;
    private String name;
    private Double credit;
    /**
     * 课程时间
     * <p>xx:xx-xx:xx</p>
     */
    private String courseTime;
    private String teacherName;
    private String courseBeginWeek;
    private String courseEndWeek;
    /**
     * 考核方式
     * <p>考试、无、项目答辩、提交报告、其它</p>
     */
    private String wayOfTest;
    /**
     * 上课地点
     */
    private String location;
    @ManyToOne
    @JoinColumn(name = "pre_course_id")
    private Course preCourse;
    /**
     * 课程类型 必修，选修，通选，限选，任选
     */
    private String type;

    private String file;
}
