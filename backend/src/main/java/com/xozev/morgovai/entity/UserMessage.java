package com.xozev.morgovai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserMessage extends BaseEntity {

    @ManyToOne
    private Chat chat;

    @Column(columnDefinition = "TEXT")
    private String text;
}
