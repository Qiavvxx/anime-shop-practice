package ru.mirea.animeshop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class OrderLine {
    private final int productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPriceSnapshot;

    public OrderLine(int productId, String productName, int quantity, BigDecimal unitPriceSnapshot) {
        if (productId <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Позиция заказа задана некорректно.");
        }
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Название товара в заказе обязательно.");
        }
        if (unitPriceSnapshot == null || unitPriceSnapshot.signum() < 0) {
            throw new IllegalArgumentException("Цена в заказе должна быть неотрицательной.");
        }
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPriceSnapshot = unitPriceSnapshot.setScale(2, RoundingMode.UNNECESSARY);
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPriceSnapshot() {
        return unitPriceSnapshot;
    }

    public BigDecimal getLineTotal() {
        return unitPriceSnapshot.multiply(BigDecimal.valueOf(quantity));
    }
}
