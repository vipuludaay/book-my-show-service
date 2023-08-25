package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "SHOW")
public class Show extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "AUDITORIUM_ID")
    private Auditorium auditorium;
}
