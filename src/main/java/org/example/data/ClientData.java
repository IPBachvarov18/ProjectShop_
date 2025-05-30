package org.example.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class ClientData {
    CardType card;
    BigDecimal avaiableCash;
    HashMap<UUID, Integer> productList;
}
