package com.teach.javafx.models.DTO;

import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HexFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HonorInfoInfo implements Serializable {
    private Integer honorId;

    private String studentName;

    private Long studentId;

    private String type;

    /**
     * 荣誉等级：
     * <p>班级、年级、院级、校级、市级、省级、国家级、世界级</p>
     */
    private String level;

    private String honorTime;

    private String honorFrom;

    private String honorName;

    private String file;

    public HonorInfoInfo(HonorInfo honorInfo){
        honorId=honorInfo.getHonorId();
        studentName=honorInfo.getHonorName();
        studentId=honorInfo.getStudent().getStudentId();
        type=honorInfo.getType();
        level=honorInfo.getLevel();
        honorTime=honorInfo.getHonorTime();
        honorFrom=honorInfo.getHonorFrom();
        file=honorInfo.getFile();
    }
}
