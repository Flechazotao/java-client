package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>HonorInfo 荣誉信息类
 * <p>Integer honorId 主键id
 * <p>Student student 对应的学生
 * <p>String level 荣誉等级
 * <p>String honorTime 获得荣誉的时间
 * <p>String honorFrom 授予荣誉的单位
 * <p>String honorName 荣誉名称
 * <p>String type 荣誉类别
 * <p>String file 证明文件的路径
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "honor_info")
@Entity
public class HonorInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer honorId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String type;

    /**
     * 荣誉等级：
     * <p>班级、年级、院级、校级、市级、省级、国家级、世界级</p>
     */
    private String level;

    private String honorTime;

    private String honorFrom;

    private String honorName;

    private String file;
}
