package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "MOVIE")
public class Movie extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @Column(name = "DURATION_MINUTES")
    private Integer durationMinutes;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> movieFeatures;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "MOVIE_ACTOR",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    private List<Actor> actors;
}
