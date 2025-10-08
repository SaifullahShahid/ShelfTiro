package com.example.shelftiro.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "user_email",nullable = false, unique = true)
    private String email;

    @Column(name="user_password", nullable = false)
    private String password;

    @Column(name="user_role")
    private String role;

    @Column(name = "user_age")
    private Integer age;

    @Column(name = "join_date",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @OneToMany(mappedBy = "userEntity")
    private List<LoanEntity> loansEntity;
}
