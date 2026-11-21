package ru.mirea.animeshop.service;

import ru.mirea.animeshop.model.OrderLine;
import ru.mirea.animeshop.model.OrderStatus;
import ru.mirea.animeshop.model.PaymentAttempt;
import ru.mirea.animeshop.model.PaymentStatus;
import ru.mirea.animeshop.model.Product;
import ru.mirea.animeshop.model.StoreOrder;
import ru.mirea.animeshop.payment.PaymentGateway;

import java.util.ArrayList;
import java.util.List;

public final class PaymentService {
    private final OrderService orderService;

    public PaymentService(OrderService orderService) {
        if (orderService == null) {
            throw new IllegalArgumentException("Сервис заказов не задан.");
        }
        this.orderService = orderService;
    }

    public StoreOrder processPayment(StoreOrder order, PaymentGateway gateway) {
        if (order == null || gateway == null) {
            throw new IllegalArgumentException("Не заданы заказ или платёжный сервис.");
        }
        if (order.getStatus() != OrderStatus.AWAITING_PAYMENT) {
            throw new IllegalStateException("Обработать можно только заказ, ожидающий оплаты.");
        }

        PaymentStatus paymentStatus = gateway.processPayment(order.getTotal());
        if (paymentStatus == null) {
            throw new IllegalStateException("Платёжный сервис не вернул результат.");
        }

        List<ReservedLine> checkedLines = new ArrayList<>();
        for (OrderLine line : order.getItems()) {
            Product product = orderService.getProduct(line.getProductId());
            if (product == null) {
                throw new IllegalStateException(
                        "Товар с идентификатором " + line.getProductId() + " больше не существует."
                );
            }
            if (product.getReservedQuantity() < line.getQuantity()) {
                throw new IllegalStateException(
                        "Резерв товара «" + product.getName() + "» не согласован."
                );
            }
            if (paymentStatus == PaymentStatus.ACCEPTED
                    && product.getStockQuantity() < line.getQuantity()) {
                throw new IllegalStateException(
                        "Физический остаток товара «" + product.getName() + "» не согласован."
                );
            }
            checkedLines.add(new ReservedLine(product, line.getQuantity()));
        }
        if (checkedLines.isEmpty()) {
            throw new IllegalStateException("В заказе нет товарных позиций.");
        }

        // Все резервы проверены до начала изменений.
        for (ReservedLine line : checkedLines) {
            if (paymentStatus == PaymentStatus.ACCEPTED) {
                line.product().sellReserved(line.quantity());
            } else {
                line.product().releaseReservation(line.quantity());
            }
        }

        order.recordPaymentAttempt(new PaymentAttempt(
                order.getId(),
                order.getTotal(),
                gateway.getName(),
                paymentStatus
        ));
        return order;
    }

    private record ReservedLine(Product product, int quantity) {
    }
}
