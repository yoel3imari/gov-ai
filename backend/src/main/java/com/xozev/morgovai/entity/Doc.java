package com.xozev.morgovai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Doc extends BaseEntity {

    private String title;
    private String path;
    private Long size;
    private String lang;

    @ManyToOne
    private GovService service;
}
