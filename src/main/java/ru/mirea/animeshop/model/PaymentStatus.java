package ru.mirea.animeshop.model;

public enum PaymentStatus {
    ACCEPTED("Принята"),
    FAILED("Отклонена");

    private final String label;

    PaymentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
