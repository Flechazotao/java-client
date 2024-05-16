package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourse;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedCourseInfoInfo extends Course implements Serializable {
    private Integer courseId;
    private String number;
    private String name;
    private String credit;
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
    private String location;
    /**
     * 课程类型 必修，选修，通选，限选，任选
     */
    private String type;

    private String file;

    private Integer numberOfSelected;

    private Integer MaxNumberOfSelected;

    public SelectedCourseInfoInfo(SelectedCourseInfo selectedCourseInfo){
        courseId=selectedCourseInfo.getCourse().getCourseId();
        number=selectedCourseInfo.getCourse().getNumber();
        name=selectedCourseInfo.getCourse().getName();
        credit=selectedCourseInfo.getCourse().getCredit();
        courseTime=selectedCourseInfo.getCourse().getWayOfTest();
        teacherName=selectedCourseInfo.getCourse().getTeacherName();
        courseBeginWeek=selectedCourseInfo.getCourse().getCourseBeginWeek();
        wayOfTest=selectedCourseInfo.getCourse().getWayOfTest();
        location=selectedCourseInfo.getCourse().getLocation();
        type=selectedCourseInfo.getCourse().getType();
        numberOfSelected=selectedCourseInfo.getCourse().getCourseId();
        MaxNumberOfSelected=selectedCourseInfo.getCourse().getCourseId();
    }
}
