package org.vip.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "USER")
public class User extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "EMAIL")
    private String email;
}
