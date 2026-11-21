package ru.mirea.animeshop.model;

import java.math.BigDecimal;

public final class PaymentAttempt {
    private final int orderId;
    private final BigDecimal amount;
    private final String gatewayName;
    private final PaymentStatus status;

    public PaymentAttempt(int orderId, BigDecimal amount, String gatewayName, PaymentStatus status) {
        if (orderId <= 0 || amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Попытка оплаты задана некорректно.");
        }
        if (gatewayName == null || gatewayName.isBlank() || status == null) {
            throw new IllegalArgumentException("Не заполнены данные попытки оплаты.");
        }
        this.orderId = orderId;
        this.amount = amount;
        this.gatewayName = gatewayName;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
