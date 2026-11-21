package ru.mirea.animeshop.data;

import ru.mirea.animeshop.model.Category;
import ru.mirea.animeshop.model.Franchise;
import ru.mirea.animeshop.model.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SampleData {
    private SampleData() {
    }

    public static Map<Integer, Product> createProducts() {
        Franchise naruto = new Franchise(1, "Наруто");
        Franchise jujutsuKaisen = new Franchise(2, "Магическая битва");

        Category figures = new Category(1, "Фигурки");
        Category manga = new Category(2, "Манга");
        Category accessories = new Category(3, "Аксессуары");

        Map<Integer, Product> products = new LinkedHashMap<>();
        products.put(1001, new Product(
                1001, "Фигурка Наруто", figures, naruto,
                new BigDecimal("3490.00"), 3, 0, true
        ));
        products.put(1002, new Product(
                1002, "Манга Наруто, том 1", manga, naruto,
                new BigDecimal("690.00"), 8, 0, true
        ));
        products.put(1003, new Product(
                1003, "Брелок Годжо", accessories, jujutsuKaisen,
                new BigDecimal("490.00"), 2, 0, true
        ));
        return products;
    }
}
