package org.vip.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SEAT")
public class Seat extends BaseModel {
    @Column(name = "SEAT_NUMBER")
    private String seatNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "SEAT_TYPE")
    private SeatType seatType;

    @Column(name = "ROW")
    private Integer row;

    @Column(name = "COLUMN")
    private Integer column;

    @JsonIgnoreProperties({"seats"})    // seats attribute belongs to Auditorium class
    @ManyToOne
    @JoinColumn(name = "AUDITORIUM_ID")
    private Auditorium auditorium;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEAT_STATUS")
    private SeatStatus seatStatus;
}
