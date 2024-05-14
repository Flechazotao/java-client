package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Fee 消费流水实体类  保存学生消费流水的基本信息信息，
 * <p>Integer feeId 消费表 fee 主键 fee_id
 * <p>String day 消费日期
 * <p>String feeType 消费项目，又叫消费类别 比如饮食、购物啥的
 * <p>Long StudentId 学号
 * <p>String studentName 学生姓名
 * <p>Double money 消费金额
 * <p>String description 消费描述，描述具体买了啥
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeInfo implements Serializable {
    private Integer feeId;

    private String feeType;

    private Long studentId;

    private String studentName;

    private String day;

    private Double money;

    private String description;

    public FeeInfo(Fee fee){
        feeType=fee.getFeeType();
        studentId=fee.getStudent().getStudentId();
        studentName=fee.getStudent().getPerson().getName();
        day=fee.getDay();
        money= fee.getMoney();
        description=fee.getDescription();
    }
}
