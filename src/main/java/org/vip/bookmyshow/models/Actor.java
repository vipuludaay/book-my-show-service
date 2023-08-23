package org.vip.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ACTOR")
public class Actor extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @Column(name = "RATING")
    private Double rating;
}
