package org.vip.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACTOR")
public class Actor extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @Column(name = "RATING")
    private Double rating;
}
