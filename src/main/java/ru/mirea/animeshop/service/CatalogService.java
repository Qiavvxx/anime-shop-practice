package ru.mirea.animeshop.service;

import ru.mirea.animeshop.model.Franchise;
import ru.mirea.animeshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class CatalogService {
    private final Map<Integer, Product> products;

    public CatalogService(Map<Integer, Product> products) {
        if (products == null) {
            throw new IllegalArgumentException("Каталог товаров не задан.");
        }
        this.products = products;
    }

    public List<Product> searchProducts(
            String query,
            Integer categoryId,
            Integer franchiseId,
            boolean availableOnly
    ) {
        String needle = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        List<Product> result = new ArrayList<>();

        for (Product product : products.values()) {
            if (!product.isActive()) {
                continue;
            }
            if (categoryId != null && product.getCategory().getId() != categoryId) {
                continue;
            }

            Franchise franchise = product.getFranchise();
            Integer productFranchiseId = franchise == null ? null : franchise.getId();
            if (franchiseId != null && !franchiseId.equals(productFranchiseId)) {
                continue;
            }
            if (availableOnly && product.getAvailableQuantity() <= 0) {
                continue;
            }

            String productName = product.getName().toLowerCase(Locale.ROOT);
            String franchiseName = franchise == null
                    ? ""
                    : franchise.getName().toLowerCase(Locale.ROOT);
            if (!needle.isEmpty()
                    && !productName.contains(needle)
                    && !franchiseName.contains(needle)) {
                continue;
            }
            result.add(product);
        }
        return result;
    }
}
