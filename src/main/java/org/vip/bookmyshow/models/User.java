package org.vip.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends BaseModel {
    private String name;
    private Long phone;
}
