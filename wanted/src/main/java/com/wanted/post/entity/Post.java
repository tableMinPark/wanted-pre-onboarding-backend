package com.wanted.post.entity;

import com.wanted.auth.entity.Member;
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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
//    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "upd_dt")
    private LocalDateTime updDt;
    @CreationTimestamp
    @Column(name = "reg_dt")
    private LocalDateTime regDt;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
