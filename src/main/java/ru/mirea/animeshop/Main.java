package ru.mirea.animeshop;

import ru.mirea.animeshop.data.SampleData;
import ru.mirea.animeshop.delivery.CourierDelivery;
import ru.mirea.animeshop.delivery.PickupDelivery;
import ru.mirea.animeshop.model.CartItem;
import ru.mirea.animeshop.model.Customer;
import ru.mirea.animeshop.model.Product;
import ru.mirea.animeshop.model.StoreOrder;
import ru.mirea.animeshop.payment.DemoPaymentGateway;
import ru.mirea.animeshop.service.CatalogService;
import ru.mirea.animeshop.service.OrderService;
import ru.mirea.animeshop.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Map<Integer, Product> products = SampleData.createProducts();
        CatalogService catalogService = new CatalogService(products);
        OrderService orderService = new OrderService(products);
        PaymentService paymentService = new PaymentService(orderService);
        Customer customer = new Customer(501, "Покупатель №501");

        List<Product> found = catalogService.searchProducts(
                "наруто",
                null,
                null,
                true
        );
        System.out.println("Найдено товаров по запросу «Наруто»: " + found.size());

        StoreOrder paidOrder = orderService.createOrder(
                customer,
                List.of(new CartItem(1001, 1), new CartItem(1002, 2)),
                1,
                new CourierDelivery(new BigDecimal("450.00"))
        );
        System.out.println("Заказ №" + paidOrder.getId()
                + ": " + paidOrder.getTotal().toPlainString()
                + " руб.; статус «" + paidOrder.getStatus().getLabel() + "»");
        paymentService.processPayment(paidOrder, new DemoPaymentGateway(true));
        System.out.println("После успешной оплаты: статус «"
                + paidOrder.getStatus().getLabel()
                + "»; фигурок доступно: " + products.get(1001).getAvailableQuantity());

        StoreOrder cancelledOrder = orderService.createOrder(
                customer,
                List.of(new CartItem(1003, 1)),
                2,
                new PickupDelivery()
        );
        paymentService.processPayment(cancelledOrder, new DemoPaymentGateway(false));
        System.out.println("После отклонения оплаты: статус «"
                + cancelledOrder.getStatus().getLabel()
                + "»; брелоков доступно: " + products.get(1003).getAvailableQuantity());
    }
}
