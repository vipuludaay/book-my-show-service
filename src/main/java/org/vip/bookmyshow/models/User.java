package org.vip.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
