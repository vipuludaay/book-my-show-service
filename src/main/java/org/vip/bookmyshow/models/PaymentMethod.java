package org.vip.bookmyshow.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum PaymentMethod {
    DEBIT_CARD,
    CREDIT_CARD,
    NET_BANKING,
    UPI,
    WALLET
}
