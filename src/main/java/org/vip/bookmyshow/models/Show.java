package org.vip.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Show extends BaseModel {
    @ManyToOne
    private Movie movie;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    private Auditorium auditorium;
}
