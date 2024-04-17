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
 * <p>String proveInfo 证明信息
 * <p>String level 荣誉等级
 * <p>String honorTime 获得荣誉的时间
 * <p>String honorFrom 授予荣誉的单位
 * <p>String honorName 荣誉名称
 * <p>String type 荣誉类别
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HonorInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer honorId;

    @ManyToOne
    private Student student;
    private String proveInfo;

    private String type;

    private String level;

    private String honorTime;

    private String honorFrom;

    private String honorName;
}
