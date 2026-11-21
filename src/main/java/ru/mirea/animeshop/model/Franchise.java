package ru.mirea.animeshop.model;

public final class Franchise {
    private final int id;
    private final String name;

    public Franchise(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Идентификатор франшизы должен быть положительным.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название франшизы обязательно.");
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
