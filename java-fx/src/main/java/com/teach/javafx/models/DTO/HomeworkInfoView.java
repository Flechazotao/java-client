package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.HomeworkInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkInfoView {
    private Integer homeworkInfoId;

    private String courseName;

    private String courseNumber;

    private String demand;

    private String homeworkName;

    private String time;

    private String file;

    public HomeworkInfoView(HomeworkInfo homeworkInfo){
        homeworkInfoId=homeworkInfo.getHomeworkInfoId();
        courseName=homeworkInfo.getCourse().getName();
        courseNumber=homeworkInfo.getCourse().getNumber();
        homeworkName=homeworkInfo.getName();
        file=homeworkInfo.getFile();
    }
}
