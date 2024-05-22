package com.teach.javafx.models.DO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>SelectedCourse 选课类
 * <p>Integer selectedId 主键id
 * <p>Student student 对应的学生
 * <p>Course course 对应的课程
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "selected_course")
@Entity
public class SelectedCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer selectedId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private SelectedCourseInfo selectedCourseInfo;

}
