package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Auditorium extends BaseModel {
    private Integer floorNo;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> screenTypes;

    @ManyToOne
    private Theatre theatre;
}
