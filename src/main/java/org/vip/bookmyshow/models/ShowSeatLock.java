package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SHOW_SEAT_LOCK")
public class ShowSeatLock extends BaseModel {
    @OneToOne
    @JoinColumn(name = "SHOW_SEAT_ID")
    private ShowSeat showSeat;

    @Column(name = "EXPIRY")
    private LocalDateTime expiry;
}
