package org.shop.interfaces;

import org.shop.models.Product;

import java.util.Set;
import java.util.UUID;

public interface ProductCatalogService {
    void addProduct(Product product);

    void deleteProduct(Product product);

    Set<Product> getAllProducts();

    Product getProductById(UUID id);
}

