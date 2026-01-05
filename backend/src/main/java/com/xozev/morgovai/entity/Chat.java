package com.xozev.morgovai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Chat extends BaseEntity {

    private String title;

    @ManyToOne
    private GovService service;
}
