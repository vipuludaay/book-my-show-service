package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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
    private List<Feature> features;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "MOVIE_ACTOR",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    private List<Actor> actors;
}
