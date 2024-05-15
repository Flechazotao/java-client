package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DO.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceInfoInfo {
    private Integer attendanceId;

    private Long studentId;

    private String studentName;

    private String activityName;
    /**
     * 上课考勤、会议考勤、活动考勤
     */
    private String type;

    private String attendanceTime;

    private String isAttended;

    public AttendanceInfoInfo(AttendanceInfo attendanceInfo){
        this.attendanceId=attendanceInfo.getAttendanceId();
        this.studentId=attendanceInfo.getStudent().getStudentId();
        this.studentName=attendanceInfo.getStudent().getPerson().getName();
        this.activityName=attendanceInfo.getActivityName();
        this.type=attendanceInfo.getType();
        this.attendanceTime=attendanceInfo.getAttendanceTime();
        this.isAttended=attendanceInfo.getIsAttended();
    }
}
