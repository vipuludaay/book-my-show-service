package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "SHOW_SEAT_TYPE_MAPPING")
public class ShowSeatType extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @Column(name = "PRICE")
    private Integer price;
}
