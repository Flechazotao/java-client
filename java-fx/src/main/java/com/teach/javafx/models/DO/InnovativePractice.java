package com.teach.javafx.models.DO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>InnovativePractice 创新实践信息类
 * <p>Integer innovativeId 主键id
 * <p>Student student 对应的学生
 * <p>String activityName 活动名称
 * <p>String teacherName 指导老师的姓名
 * <p>String achievement 成果
 * <p>String beginTime 开始时间
 * <p>String endTime 结束时间
 * <p>String type 类型
 * <p>String file 文件路径
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "innovative_practice")
@Entity
public class InnovativePractice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer innovativeId;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String activityName;

    private String teacherName;

    /**
     * 成果：
     * <p>特等奖、一等奖、二等奖、三等奖、优秀奖</p>
     * <p>金奖、银奖、铜奖、参与</p>
     * <p>发明专利、实用新型专利、外观设计专利</p>
     * <p>志愿者、负责人</p>
     * <p>校级优秀团队（队长）、校级优秀团队（队员）</p>
     * <p>其他</p>
     */
    private String achievement;

    private String beginTime;

    private String endTime;

    /**
     * 创新实践类型：
     * <p>社会实践、学科竞赛、科技成果、培训讲座、创新项目、校外实习、志愿服务</p>
     */
    private String type;

    private String file;
}
