package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <p>BeforeUniversity 存储入学前信息
 * <p>Integer BeforeUniversityId 主键id
 * <p>Student student 对应的学生
 * <p>String graduatedProvince 毕业省份
 * <p>String graduatedSchool 毕业学校
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "before_university")
@Entity
public class BeforeUniversity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BeforeUniversityId;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String graduatedProvince;

    private String graduatedSchool;
}
