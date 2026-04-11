package com.comparecarrinhos.dto;

import lombok.*;
import java.util.List;
import java.util.Map;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CartResponse {
    private String bestMarket;
    private Double lowestPrice;
    private Double potentialSavings;
    private Map<String, Double> allTotals;
    private List<ProductDetail> details; // <--- NOVA LISTA DETALHADA

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ProductDetail {
        private String productName;
        private Map<String, Double> pricesPerMarket;
    }
}
