package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Payment extends BaseModel {
    private String transactionId;

    private Double amount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTime;

    @ManyToOne
    private Ticket ticket;  // check for cyclic dependency issue, as Ticket also has List<Payment> attribute.
}
