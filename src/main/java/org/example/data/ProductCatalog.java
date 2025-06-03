package org.example.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProductCatalog implements ProductCatalogService {
    private Set<Product> productsList = new HashSet<>();

    public ProductCatalog(Set<Product> productsList) {
        this.productsList = productsList;
    }

    public Set<Product> getAllProducts() {
        return productsList;
    }

    public void setProductsList(Set<Product> productsList) {
        this.productsList = productsList;
    }

    public void addProduct(Product product) {
        productsList.add(product);
    }


    public void deleteProduct(Product product) {
        productsList.remove(product);
    }

    public Product getProductById(UUID id) {
        return productsList.stream().filter(product -> product.getId().equals(id))
                .findFirst().orElse(null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductCatalog:\n");
        for (Product p : productsList) {
            sb.append(" - ")
                    .append(p.getName())
                    .append(", Price: ").append(p.getDeliveryPrice())
                    .append(", Expiry: ").append(p.getExpireDate())
                    .append("\n");
        }
        return sb.toString();
    }
}


