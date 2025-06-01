package org.example.data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductCatalogService {
    void addProduct(Product product);

    void deleteProduct(Product product);

    Set<Product> getAllProducts();

    Product getProductById(UUID id);
}

