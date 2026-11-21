package ru.mirea.animeshop.service;

import ru.mirea.animeshop.delivery.DeliveryMethod;
import ru.mirea.animeshop.model.CartItem;
import ru.mirea.animeshop.model.Customer;
import ru.mirea.animeshop.model.OrderLine;
import ru.mirea.animeshop.model.Product;
import ru.mirea.animeshop.model.StoreOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OrderService {
    private final Map<Integer, Product> products;

    public OrderService(Map<Integer, Product> products) {
        if (products == null) {
            throw new IllegalArgumentException("Каталог товаров не задан.");
        }
        this.products = products;
    }

    public StoreOrder createOrder(
            Customer customer,
            List<CartItem> cart,
            int orderId,
            DeliveryMethod deliveryMethod
    ) {
        if (customer == null || orderId <= 0 || deliveryMethod == null) {
            throw new IllegalArgumentException("Не заполнены данные покупателя, заказа или доставки.");
        }
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Корзина должна содержать хотя бы один товар.");
        }

        Map<Integer, Integer> groupedItems = groupCartItems(cart);
        List<OrderLine> orderLines = new ArrayList<>();
        BigDecimal goodsTotal = new BigDecimal("0.00");

        // Сначала проверяются все позиции и рассчитывается сумма. Остатки пока не меняются.
        for (Map.Entry<Integer, Integer> entry : groupedItems.entrySet()) {
            Product product = products.get(entry.getKey());
            int quantity = entry.getValue();
            if (product == null || !product.isActive()) {
                throw new IllegalArgumentException(
                        "Товар с идентификатором " + entry.getKey() + " не найден или неактивен."
                );
            }
            if (product.getAvailableQuantity() < quantity) {
                throw new IllegalStateException(
                        "Недостаточно доступного остатка товара «" + product.getName() + "»."
                );
            }

            orderLines.add(new OrderLine(
                    product.getId(),
                    product.getName(),
                    quantity,
                    product.getUnitPrice()
            ));
            goodsTotal = goodsTotal.add(
                    product.getUnitPrice().multiply(BigDecimal.valueOf(quantity))
            );
        }

        BigDecimal total = goodsTotal
                .add(deliveryMethod.getCost())
                .setScale(2, RoundingMode.UNNECESSARY);
        StoreOrder order = new StoreOrder(
                orderId,
                customer,
                orderLines,
                deliveryMethod,
                total
        );

        // Резерв создаётся только после успешной проверки всей корзины.
        for (Map.Entry<Integer, Integer> entry : groupedItems.entrySet()) {
            products.get(entry.getKey()).reserve(entry.getValue());
        }
        return order;
    }

    public Product getProduct(int productId) {
        return products.get(productId);
    }

    private static Map<Integer, Integer> groupCartItems(List<CartItem> cart) {
        Map<Integer, Integer> grouped = new LinkedHashMap<>();
        for (CartItem item : cart) {
            if (item == null) {
                throw new IllegalArgumentException("В корзине есть пустая позиция.");
            }
            grouped.merge(item.getProductId(), item.getQuantity(), (left, right) -> Math.addExact(left, right));
        }
        return grouped;
    }
}
