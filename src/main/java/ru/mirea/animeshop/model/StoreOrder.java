package ru.mirea.animeshop.model;

import ru.mirea.animeshop.delivery.DeliveryMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StoreOrder {
    private final int id;
    private final Customer customer;
    private final List<OrderLine> items;
    private final DeliveryMethod deliveryMethod;
    private final BigDecimal total;
    private final List<PaymentAttempt> paymentAttempts = new ArrayList<>();
    private OrderStatus status;

    public StoreOrder(
            int id,
            Customer customer,
            List<OrderLine> items,
            DeliveryMethod deliveryMethod,
            BigDecimal total
    ) {
        if (id <= 0 || customer == null || items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Заказ задан некорректно.");
        }
        if (deliveryMethod == null || total == null || total.signum() < 0) {
            throw new IllegalArgumentException("Не заполнены доставка или сумма заказа.");
        }
        this.id = id;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.deliveryMethod = deliveryMethod;
        this.total = total;
        this.status = OrderStatus.AWAITING_PAYMENT;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderLine> getItems() {
        return Collections.unmodifiableList(items);
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<PaymentAttempt> getPaymentAttempts() {
        return Collections.unmodifiableList(paymentAttempts);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void recordPaymentAttempt(PaymentAttempt attempt) {
        if (status != OrderStatus.AWAITING_PAYMENT) {
            throw new IllegalStateException("Оплатить можно только заказ, ожидающий оплаты.");
        }
        if (attempt == null || attempt.getOrderId() != id) {
            throw new IllegalArgumentException("Попытка оплаты не соответствует заказу.");
        }
        paymentAttempts.add(attempt);
        status = attempt.getStatus() == PaymentStatus.ACCEPTED
                ? OrderStatus.PAID
                : OrderStatus.CANCELLED;
    }
}
