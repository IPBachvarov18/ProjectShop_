package org.shop.models;

import org.shop.enums.CardType;

import java.math.BigDecimal;
import java.util.Map;

public class ClientData {
    private CardType card;
    private BigDecimal avaiableCash;

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    private Map<Product, Integer> productList;
    private BigDecimal total;
    private BigDecimal totalDiscount;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ClientData(CardType card, BigDecimal avaiableCash, Map<Product, Integer> productList) {
        this.card = card;
        this.avaiableCash = avaiableCash;
        this.productList = productList;
    }

    public CardType getCard() {
        return card;
    }

    public void setCard(CardType card) {
        this.card = card;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    public BigDecimal getAvaiableCash() {
        return avaiableCash;
    }

    public void setAvaiableCash(BigDecimal avaiableCash) {
        this.avaiableCash = avaiableCash;
    }
}
