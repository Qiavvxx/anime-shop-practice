package ru.mirea.animeshop.payment;

import ru.mirea.animeshop.model.PaymentStatus;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentStatus processPayment(BigDecimal amount);

    String getName();
}
