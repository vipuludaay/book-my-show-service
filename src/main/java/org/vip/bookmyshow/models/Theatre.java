package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "THEATRE")
public class Theatre extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "theatre")
    private List<Auditorium> auditoriums;
}
