package com.sparta.springauth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // username 중복 불가
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true) // email 중복 불가
    private String email;

    @Column(nullable = false)
    // Enum 데이터 타입을 데이터베이스에 저장할 때 활용
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String email, String password, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}