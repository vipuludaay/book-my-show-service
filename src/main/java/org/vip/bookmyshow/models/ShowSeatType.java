package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SHOW_SEAT_TYPE_MAPPING")
public class ShowSeatType extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "SEAT_TYPE")
    private SeatType seatType;

    @Column(name = "PRICE")
    private Integer price;
}
