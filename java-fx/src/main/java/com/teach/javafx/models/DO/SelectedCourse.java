package com.teach.javafx.models.DO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>SelectedCourse 选课类
 * <p>Integer selectedId 主键id
 * <p>Student student 对应的学生
 * <p>Course course 对应的课程
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelectedCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer selectedId;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

}
