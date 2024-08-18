package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseModel {
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "AMOUNT")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_METHOD")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    @Column(name = "PAYMENT_TIME")
    private LocalDateTime paymentTime;

    @ManyToOne
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
}
