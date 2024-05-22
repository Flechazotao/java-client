package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>DailyActivityStudent 日常活动与学生的对应类
 * <p>Student student 对应学生
 * <p>DailyActivity dailyActivity 对应日常活动
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "daily_activity_student")
@Entity
public class DailyActivityStudent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dailyActivityStudentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="daily_activity_id")
    private DailyActivity dailyActivity;
}
