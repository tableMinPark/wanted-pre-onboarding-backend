package com.wanted.auth.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "role")
    private String role;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @CreationTimestamp
    @Column(name = "reg_dt")
    private LocalDateTime regDt;
}

