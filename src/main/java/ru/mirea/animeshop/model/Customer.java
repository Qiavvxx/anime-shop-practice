package ru.mirea.animeshop.model;

public final class Customer {
    private final int id;
    private final String name;

    public Customer(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Идентификатор покупателя должен быть положительным.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя покупателя обязательно.");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
