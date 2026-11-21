package ru.mirea.animeshop.delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class DeliveryMethod {
    private final String name;
    private final BigDecimal cost;

    protected DeliveryMethod(String name, BigDecimal cost) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название способа доставки обязательно.");
        }
        if (cost == null || cost.signum() < 0) {
            throw new IllegalArgumentException("Стоимость доставки не может быть отрицательной.");
        }
        this.name = name;
        this.cost = cost.setScale(2, RoundingMode.UNNECESSARY);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
