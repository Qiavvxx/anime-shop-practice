package ru.mirea.animeshop.delivery;

import java.math.BigDecimal;

public final class CourierDelivery extends DeliveryMethod {
    public CourierDelivery(BigDecimal cost) {
        super("Курьер", cost);
    }
}
