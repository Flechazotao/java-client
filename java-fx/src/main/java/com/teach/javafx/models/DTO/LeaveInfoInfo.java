package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.LeaveInfo;
import com.teach.javafx.models.DO.Student;
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
public class LeaveInfoInfo implements Serializable {
    private Integer leaveId;

    private String studentName;

    private Long studentId;

    private String leaveBeginTime;

    private String leaveEndTime;

    private String leaveTime;

    private String approver;

    private String leaveReason;

    private String leaveStatus;

    public LeaveInfoInfo(LeaveInfo leaveInfo){
        leaveId=leaveInfo.getLeaveId();
        studentName=leaveInfo.getStudent().getPerson().getName();
        studentId=leaveInfo.getStudent().getStudentId();
        leaveBeginTime=leaveInfo.getLeaveBeginTime();
        leaveEndTime=leaveInfo.getLeaveEndTime();
        leaveTime=leaveInfo.getLeaveTime();
        approver=leaveInfo.getApprover();
        leaveReason=leaveInfo.getLeaveReason();
        leaveStatus=leaveInfo.getLeaveStatus();
    }
}

