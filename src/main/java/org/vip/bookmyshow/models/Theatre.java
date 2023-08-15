package org.vip.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Theatre extends BaseModel {
    private String name;

    @ManyToOne
    private City city;

    private String address;
}
