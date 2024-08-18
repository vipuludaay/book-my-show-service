package org.vip.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CITY")
public class City extends BaseModel {
    @Column(name = "NAME")
    private String name;
}
