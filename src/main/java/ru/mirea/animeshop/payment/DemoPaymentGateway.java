package ru.mirea.animeshop.payment;

import ru.mirea.animeshop.model.PaymentStatus;

import java.math.BigDecimal;

public final class DemoPaymentGateway implements PaymentGateway {
    private final boolean paymentAccepted;

    public DemoPaymentGateway(boolean paymentAccepted) {
        this.paymentAccepted = paymentAccepted;
    }

    @Override
    public PaymentStatus processPayment(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть неотрицательной.");
        }
        return paymentAccepted ? PaymentStatus.ACCEPTED : PaymentStatus.FAILED;
    }

    @Override
    public String getName() {
        return "Учебный эмулятор платёжного сервиса";
    }
}
