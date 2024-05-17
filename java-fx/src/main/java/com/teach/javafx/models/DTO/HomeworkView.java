package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Homework;
import com.teach.javafx.models.DO.HomeworkInfo;
import com.teach.javafx.models.DO.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkView {
    private Integer homeworkId;

    private String courseNumber;
    private String courseName;
    private String homeworkName;
    private String studentId;
    private String studentName;
    private String isSubmit;

    private String isChecked;

    private String checkTime;

    private String submitTime;

    private String homeworkScore;

    private String file;

    public HomeworkView(Homework homework){
        homeworkId=homework.getHomeworkId();
        courseNumber=homework.getHomeworkInfo().getCourse().getNumber();
        courseName=homework.getHomeworkInfo().getCourse().getName();
        homeworkName=homework.getHomeworkInfo().getName();
        studentId= String.valueOf(homework.getStudent().getStudentId());
        studentName=homework.getStudent().getPerson().getName();
        isSubmit=homework.getIsSubmit();
        isChecked=homework.getIsChecked();
        checkTime=homework.getCheckTime();
        submitTime=homework.getSubmitTime();
        homeworkScore=homework.getHomeworkScore();
        file=homework.getFile();
    }
}
