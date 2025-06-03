package org.example.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientData {
    private CardType card;
    private BigDecimal avaiableCash;
    private Map<UUID, Integer> productList;

    public ClientData(CardType card, BigDecimal avaiableCash, Map<UUID, Integer> productList) {
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

    public Map<UUID, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<UUID, Integer> productList) {
        this.productList = productList;
    }

    public BigDecimal getAvaiableCash() {
        return avaiableCash;
    }

    public void setAvaiableCash(BigDecimal avaiableCash) {
        this.avaiableCash = avaiableCash;
    }
}
