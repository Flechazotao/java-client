package com.teach.javafx.models.DO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>BeforeUniversity 存储入学前信息
 * <p>Integer BeforeUniversityId 主键id
 * <p>Student student 对应的学生
 * <p>String graduatedProvince 毕业省份
 * <p>String graduatedSchool 毕业学校
 * <p>
 * <p>
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BeforeUniversity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BeforeUniversityId;

    @OneToOne
    private Student student;

    private String graduatedProvince;

    private String graduatedSchool;
}
