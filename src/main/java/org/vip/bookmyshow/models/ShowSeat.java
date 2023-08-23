package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "SHOW_SEAT_MAPPING")
public class ShowSeat extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "SEAT_ID")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatStatus showSeatStatus;
}
