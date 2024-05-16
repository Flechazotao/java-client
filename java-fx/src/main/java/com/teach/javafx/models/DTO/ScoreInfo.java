package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.Score;
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
public class ScoreInfo implements Serializable {
    private Integer scoreId;

    private String courseNumber;

    private String courseName;

    private Double credit;

    private String studentName;

    private Long studentNumber;

    private Double mark;

    private Double markPoint;

    private Integer ranking;

    public ScoreInfo(Score score){
        scoreId=score.getScoreId();
        courseNumber=score.getCourse().getNumber();
        courseName=score.getCourse().getName();
        credit= score.getCourse().getCredit();
        studentName=score.getStudent().getPerson().getName();
        studentNumber=score.getStudent().getStudentId();
        mark= score.getMark();
        markPoint= score.getMark() /10-5;
        if(markPoint<0)markPoint=0.0;
        ranking=score.getRanking();
    }

}
