package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>FamilyMember 家庭成员实体类
 * <p>Integer memberId 主键id
 * <p>Student student 对应学生
 * <p>String relation 与学生的关系
 * <p>String name 姓名
 * <p>String gender 性别
 * <p>String unit 工作单位
 * <p>String birthday 出生日期
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FamilyMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @ManyToOne
    private Student student;

    private String relation;

    private String name;

    private String gender;

    private String birthday;

    private String unit;
}