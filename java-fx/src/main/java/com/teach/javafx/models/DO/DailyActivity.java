package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>DailyActivity 日常活动类
 * <p>Integer activityId 主键id
 * <p>String studentName 学生们的姓名
 * <p>String beginTime 活动开始时间
 * <p>String endTime 活动结束时间
 * <p>String activityType 活动类型
 * <p>String location 活动地点
 * <p>String activityName 活动名称
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@TableName("daily_activity")
@Table(name = "daily_activity")
@Entity
public class DailyActivity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    private String studentName;

    private String beginTime;

    private String activityName;

    private String endTime;

    /**
     * 活动类型：
     * <p>聚会、旅游、文艺演出、体育活动</p>
     */
    private String activityType;

    private String location;
}
