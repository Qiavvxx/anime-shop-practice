package ru.mirea.animeshop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Product {
    private final int id;
    private final String name;
    private final Category category;
    private final Franchise franchise;
    private final BigDecimal unitPrice;
    private int stockQuantity;
    private int reservedQuantity;
    private final boolean active;

    public Product(
            int id,
            String name,
            Category category,
            Franchise franchise,
            BigDecimal unitPrice,
            int stockQuantity,
            int reservedQuantity,
            boolean active
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException("Идентификатор товара должен быть положительным.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название товара обязательно.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Категория товара обязательна.");
        }
        if (unitPrice == null || unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Цена должна быть неотрицательной.");
        }
        if (stockQuantity < 0 || reservedQuantity < 0 || reservedQuantity > stockQuantity) {
            throw new IllegalArgumentException("Остатки товара заданы некорректно.");
        }

        this.id = id;
        this.name = name;
        this.category = category;
        this.franchise = franchise;
        this.unitPrice = unitPrice.setScale(2, RoundingMode.UNNECESSARY);
        this.stockQuantity = stockQuantity;
        this.reservedQuantity = reservedQuantity;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public int getAvailableQuantity() {
        return stockQuantity - reservedQuantity;
    }

    public boolean isActive() {
        return active;
    }

    public void reserve(int quantity) {
        requirePositiveQuantity(quantity);
        if (getAvailableQuantity() < quantity) {
            throw new IllegalStateException("Недостаточно доступного остатка товара «" + name + "».");
        }
        reservedQuantity += quantity;
    }

    public void releaseReservation(int quantity) {
        requirePositiveQuantity(quantity);
        if (reservedQuantity < quantity) {
            throw new IllegalStateException("Резерв товара «" + name + "» не согласован.");
        }
        reservedQuantity -= quantity;
    }

    public void sellReserved(int quantity) {
        requirePositiveQuantity(quantity);
        if (reservedQuantity < quantity || stockQuantity < quantity) {
            throw new IllegalStateException("Невозможно списать зарезервированный товар «" + name + "».");
        }
        reservedQuantity -= quantity;
        stockQuantity -= quantity;
    }

    private static void requirePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным.");
        }
    }
}
