package org.vip.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "SHOW")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
