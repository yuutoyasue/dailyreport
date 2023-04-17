package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {

    // 列挙型
    public static enum Role {
        管理者,一般
    }

    // **主キー自動生成*/
    @Id
    @Column(length = 20, nullable = false)
    private String code;

    // **パスワード255以内 null不許可*/
    @Column(length = 255, nullable = false)
    private String password;

    // **権限10文字以下 null不許可*/
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // **従業員テーブルのID null不許可 外部キー*/
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;
}
