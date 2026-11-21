package ru.mirea.animeshop.model;

public final class CartItem {
    private final int productId;
    private final int quantity;

    public CartItem(int productId, int quantity) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Идентификатор товара должен быть положительным.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным.");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
