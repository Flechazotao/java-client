package com.teach.javafx.models.DO;
/**
 * <p>InnovativePractice 创新实践信息类
 * <p>Integer innovativeId 主键id
 * <p>Student student 对应的学生
 * <p>String teacherName 指导老师
 * <p>String location 地点
 * <p>String beginTime 开始时间
 * <p>String endTime 结束时间
 * <p>String type 类型
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InnovativePractice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer innovativeId;

    @ManyToOne
    private Student student;

    private String teacherName;

    private String location;

    private String beginTime;

    private String endTime;

    private String type;
}
