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
 * <p>String activityName 活动名称
 * <p>String type 活动类型
 * <p>String attendanceTime 考勤时间
 * <p>String isAttended 是否考勤
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance_info")
@Entity
public class AttendanceInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String activityName;
    /**
     * 上课考勤、会议考勤、活动考勤
     */
    private String type;

    private String attendanceTime;

    /**
     * 已考勤、未考勤
     */
    private String isAttended;
}
