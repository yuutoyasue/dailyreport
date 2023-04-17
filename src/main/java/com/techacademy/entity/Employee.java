package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** 主キー自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 氏名20文字以下 null不許可 */
    @Column(length = 20, nullable = false)
    private String name;

    /** 削除フラグ null不許可 */
    @Column(nullable = false)
    private Integer delete_flag;

    /** 登録日時 null不許可 */
    @Column(nullable = false)
    private LocalDateTime created_at;

    /**更新日時 null不許可*/
    @Column(nullable = false)
    private LocalDateTime updated_at;

    @OneToOne(mappedBy ="employee", cascade = CascadeType.ALL )
    private Authentication authentication;

}
