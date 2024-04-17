package com.teach.javafx.models.DO;
/**
 * <p>LeaveInfo 请假信息类
 * <p>Integer leaveId 主键id
 * <p>Student student 对应学生
 * <p>String leaveBeginTime 请假开始时间
 * <p>String leaveEndTime 请假结束时间
 * <p>String approver 批准人
 * <p>String leaveReason 请假原因
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
public class LeaveInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveId;

    @ManyToOne
    private Student student;

    private String leaveBeginTime;

    private String leaveEndTime;

    private String approver;

    private String leaveReason;

}