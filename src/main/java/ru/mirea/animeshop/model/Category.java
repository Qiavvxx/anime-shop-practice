package ru.mirea.animeshop.model;

public final class Category {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Идентификатор категории должен быть положительным.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название категории обязательно.");
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
