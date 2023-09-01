package org.vip.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "AUDITORIUM")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Auditorium extends BaseModel {
    @Column(name = "FLOOR_NO")
    private Integer floorNo;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> screenTypes;

    @ManyToOne
    @JoinColumn(name = "THEATRE_ID")
    private Theatre theatre;

    @OneToMany(mappedBy = "auditorium")
    private List<Seat> seats;
}

/*
@JsonIdentityInfo - will include only one unique object of Auditorium with property = "id". Rest other objects will
have only the "id" value instead of whole Auditorium object during Serialization.
*/