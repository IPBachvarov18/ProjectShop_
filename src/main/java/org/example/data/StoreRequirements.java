package org.example.data;

import java.util.Map;

public class StoreRequirements {
    private int daysUntilExpireDiscount;
    private int expireDiscountPercentage;
    private Map<ProductCategory, Integer> categoryMarkup;

    public StoreRequirements(int daysUntilExpireDiscount, int expireDiscountPercentage, Map<ProductCategory, Integer> categoryMarkup) {
        this.setCategoryMarkup(categoryMarkup);
        this.setDaysUntilExpireDiscount(daysUntilExpireDiscount);
        this.setExpireDiscountPercentage(expireDiscountPercentage);
    }

    public int getDaysUntilExpireDiscount() {
        return daysUntilExpireDiscount;
    }

    public void setDaysUntilExpireDiscount(int daysUntilExpireDiscount) {
        this.daysUntilExpireDiscount = daysUntilExpireDiscount;
    }

    public int getExpireDiscountPercentage() {
        return expireDiscountPercentage;
    }

    public void setExpireDiscountPercentage(int expireDiscountPercentage) {
        this.expireDiscountPercentage = expireDiscountPercentage;
    }

    public Map<ProductCategory, Integer> getCategoryMarkup() {
        return categoryMarkup;
    }

    public void setCategoryMarkup(Map<ProductCategory, Integer> categoryMarkup) {
        this.categoryMarkup = categoryMarkup;
    }

}
