package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.SelectedCourse;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DO.Student;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedCourseView {
    private Integer selectedId;
    private Long studentId;
    private String studentName;
    private String number;
    private String name;
    private Double credit;
    /**
     * 课程时间
     * <p>xx:xx-xx:xx</p>
     */
    private String courseTime;
    private String teacherName;
    private String courseBeginWeek;
    private String courseEndWeek;
    /**
     * 考核方式
     * <p>考试、无、项目答辩、提交报告、其它</p>
     */
    private String wayOfTest;
    /**
     * 上课地点
     */
    private String courseLocation;
    /**
     * 课程类型 必修，选修，通选，限选，任选
     */
    private String type;

    public SelectedCourseView(SelectedCourse selectedCourse){
        selectedId=selectedCourse.getSelectedId();
        number=selectedCourse.getSelectedCourseInfo().getCourse().getNumber();
        name=selectedCourse.getSelectedCourseInfo().getCourse().getName();
        credit=selectedCourse.getSelectedCourseInfo().getCourse().getCredit();
        courseTime=selectedCourse.getSelectedCourseInfo().getCourse().getCourseTime();
        teacherName=selectedCourse.getSelectedCourseInfo().getCourse().getTeacherName();
        courseBeginWeek=selectedCourse.getSelectedCourseInfo().getCourse().getCourseBeginWeek();
        wayOfTest=selectedCourse.getSelectedCourseInfo().getCourse().getWayOfTest();
        courseLocation=selectedCourse.getSelectedCourseInfo().getCourse().getLocation();
        type=selectedCourse.getSelectedCourseInfo().getCourse().getType();
        studentName=selectedCourse.getStudent().getPerson().getName();
        studentId=selectedCourse.getStudent().getStudentId();
    }


}
