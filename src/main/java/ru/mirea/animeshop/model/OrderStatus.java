package ru.mirea.animeshop.model;

public enum OrderStatus {
    AWAITING_PAYMENT("Ожидает оплаты"),
    PAID("Оплачен"),
    CANCELLED("Отменён");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
