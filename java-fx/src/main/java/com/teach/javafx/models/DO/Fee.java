package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>Fee 消费流水实体类  保存学生消费流水的基本信息信息，
 * <p>Integer feeId 消费表 fee 主键 fee_id
 * <p>Integer studentId  student_id 对应student 表里面的 student_id
 * <p>String day 消费日期
 * <p>String feeType 消费项目，又叫消费类别 比如饮食、购物啥的
 * <p>Double money 消费金额
 * <p>String description 消费描述，描述具体买了啥
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fee")
@Entity
public class Fee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feeId;

    private String feeType;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String day;

    private Double money;

    private String description;
}
