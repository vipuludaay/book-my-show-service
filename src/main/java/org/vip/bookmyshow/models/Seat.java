package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
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

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
}
