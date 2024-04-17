package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>DailyActivity 日常活动类
 * <p>Integer activityId 主键id
 * <p>Student student 对应学生
 * <p>String beginTime 活动开始时间
 * <p>String endTime 活动结束时间
 * <p>String activityType 活动类型
 * <p>String location 活动地点
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyActivity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    @ManyToOne
//    @TableField("student_id")
    private Student student;

    private String beginTime;

    private String endTime;

    private String activityType;

    private String location;
}