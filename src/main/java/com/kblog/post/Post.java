package com.kblog.post;


import com.kblog.user.User;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post extends AbstractAuditable<User, Long> {
    
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
