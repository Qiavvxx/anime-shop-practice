package ru.mirea.animeshop.delivery;

import java.math.BigDecimal;

public final class PickupDelivery extends DeliveryMethod {
    public PickupDelivery() {
        super("Самовывоз", new BigDecimal("0.00"));
    }
}
