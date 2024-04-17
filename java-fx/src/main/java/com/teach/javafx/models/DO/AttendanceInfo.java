package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Course 考勤信息实体类  保存课程或活动的考勤信息
 * <p>Integer attendanceId 主键
 * <p>Student student 对应学生 student_id
 * <p>Integer activityId 对应活动的id
 * <p>String type 活动类型
 * <p>String attendanceTime 考勤时间
 * <p>String isAttended 是否考勤
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttendanceInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    private Student student;

    private Integer activityId;

    private String type;

    private String attendanceTime;

    private String isAttended;
}
