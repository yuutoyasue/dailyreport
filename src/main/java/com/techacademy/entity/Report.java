package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "report")

public class Report {

    /** 主キー自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 日報の日付 */
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate report_date;

    /** タイトル */
    @Column(length = 255, nullable = false)
    private String title;

    /** 内容 */
    @Column(nullable = false)
    @Type(type="text")
    private String content;

    /** employee外部キー 多なので*/
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    /** 登録日時 null不許可 */
    @Column(nullable = false)
    private LocalDateTime created_at;

    /** 更新日時 null不許可 */
    @Column(nullable = false)
    private LocalDateTime updated_at;

}
