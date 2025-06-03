package org.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.enums.ProductCategory;
import org.shop.models.Product;
import org.shop.models.StoreRequirements;
import org.shop.services.ProductServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    void whenExpireDateIsBeforeToday_isExpiredReturnsTrue() {
        // Създаваме продукт с expireDate в миналото
        Product expiredProduct = new Product(
                "Expired Cheese",
                new BigDecimal("5.00"),
                LocalDate.now().minusDays(1),
                ProductCategory.FOOD
        );

        assertTrue(
                productService.isExpired(expiredProduct),
                "isExpired трябва да върне true, когато expireDate е преди днешната дата"
        );
    }

    @Test
    void whenExpireDateIsToday_isExpiredReturnsFalse() {
        Product todayProduct = new Product(
                "Today Bread",
                new BigDecimal("3.00"),
                LocalDate.now(),
                ProductCategory.FOOD
        );

        assertFalse(
                productService.isExpired(todayProduct),
                "isExpired трябва да върне false, когато expireDate е равен на днешната дата"
        );
    }

    @Test
    void whenExpireDateIsAfterToday_isExpiredReturnsFalse() {
        Product futureProduct = new Product(
                "Future Milk",
                new BigDecimal("4.00"),
                LocalDate.now().plusDays(5),
                ProductCategory.FOOD
        );

        assertFalse(
                productService.isExpired(futureProduct),
                "isExpired трябва да върне false, когато expireDate е след днешната дата"
        );
    }

    @Test
    void getTotalPrice_appliesDiscountWhenCloseToExpiry() {
        Map<ProductCategory, Integer> categoryMarkup = new HashMap<>();
        categoryMarkup.put(ProductCategory.FOOD, 20);

        StoreRequirements req = new StoreRequirements(
                5,
                10,
                categoryMarkup,
                null
        );

        Product p = new Product(
                "Cheese",
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(3),
                ProductCategory.FOOD
        );


        BigDecimal markupPrice = new BigDecimal("120.00");
        BigDecimal discount = markupPrice.multiply(new BigDecimal("0.10"));
        BigDecimal expectedPrice = markupPrice.subtract(discount);

        BigDecimal actualPrice = productService.getTotalPrice(req, p);
        assertEquals(
                0,
                expectedPrice.compareTo(actualPrice),
                "Когато оставащи дни до изтичане < 5, трябва да се приложи 10% отстъпка"
        );
    }

    @Test
    void getTotalPrice_noDiscountWhenExpiryIsFar() {
        Map<ProductCategory, Integer> categoryMarkup = new HashMap<>();
        categoryMarkup.put(ProductCategory.FOOD, 20);

        StoreRequirements req = new StoreRequirements(
                5,
                10,
                categoryMarkup,
                null
        );

        Product p = new Product(
                "Butter",
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(7),
                ProductCategory.FOOD
        );

        BigDecimal expectedPrice = new BigDecimal("120.00");

        BigDecimal actualPrice = productService.getTotalPrice(req, p);
        assertEquals(
                0,
                expectedPrice.compareTo(actualPrice),
                "Когато оставащи дни до изтичане ≥ 5, не трябва да има отстъпка"
        );
    }

    @Test
    void getTotalPrice_zeroMarkupWhenCategoryNotInMap() {
        Map<ProductCategory, Integer> categoryMarkup = new HashMap<>();
        StoreRequirements req = new StoreRequirements(
                5,
                10,
                categoryMarkup,
                null
        );

        Product p = new Product(
                "Yogurt",
                new BigDecimal("50.00"),
                LocalDate.now().plusDays(10),
                ProductCategory.FOOD
        );


        assertThrows(
                NullPointerException.class,
                () -> productService.getTotalPrice(req, p),
                "Когато в categoryMarkup няма ключ за FOOD, методът хвърля NullPointerException"
        );

    }
}
