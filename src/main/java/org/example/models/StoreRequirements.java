package org.example.models;

import org.example.enums.CardType;
import org.example.enums.ProductCategory;

import java.util.Map;

public class StoreRequirements {
    private int daysUntilExpireDiscount;
    private int expireDiscountPercentage;
    private Map<ProductCategory, Integer> categoryMarkup;
    private Map<CardType, Integer> cardTypeDiscount;

    public StoreRequirements(int daysUntilExpireDiscount, int expireDiscountPercentage, Map<ProductCategory, Integer> categoryMarkup, Map<CardType, Integer> cardTypeDiscount) {
        this.setCategoryMarkup(categoryMarkup);
        this.setDaysUntilExpireDiscount(daysUntilExpireDiscount);
        this.setExpireDiscountPercentage(expireDiscountPercentage);
        this.cardTypeDiscount = cardTypeDiscount;
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

    public Map<CardType, Integer> getCardTypeDiscount() {
        return cardTypeDiscount;
    }

    public void setCardTypeDiscount(Map<CardType, Integer> cardTypeDiscount) {
        this.cardTypeDiscount = cardTypeDiscount;
    }

}
