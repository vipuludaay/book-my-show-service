package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "AUDITORIUM")
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
