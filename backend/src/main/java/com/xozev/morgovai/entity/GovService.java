package com.xozev.morgovai.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GovService extends BaseEntity {

    private String name;
}
