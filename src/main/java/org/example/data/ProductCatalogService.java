package org.example.data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductCatalogService {
    void addProduct(ProductCatalog catalog,Product product);

    void deleteProduct(ProductCatalog catalog,Product product);

    Set<Product> getAllProducts(ProductCatalog catalog);

    Product getProductById(ProductCatalog catalog, UUID id);
}

