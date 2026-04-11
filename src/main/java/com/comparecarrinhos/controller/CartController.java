package com.comparecarrinhos.controller;

import com.comparecarrinhos.dto.CartRequest;
import com.comparecarrinhos.dto.CartResponse;
import com.comparecarrinhos.model.Market;
import com.comparecarrinhos.repository.MarketRepository;
import com.comparecarrinhos.repository.ProductPriceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ProductPriceRepository priceRepository;
    private final MarketRepository marketRepository;

    public CartController(ProductPriceRepository priceRepository, MarketRepository marketRepository) {
        this.priceRepository = priceRepository;
        this.marketRepository = marketRepository;
    }

    @PostMapping("/compare")
    public CartResponse compareCarts(@RequestBody CartRequest request) {
        List<Market> markets = marketRepository.findAll();
        Map<String, Double> totalsByMarket = new HashMap<>();
        List<CartResponse.ProductDetail> details = new ArrayList<>();

        // 1. Pegar todos os produtos solicitados
        for (Long productId : request.getProductIds()) {
            var productPrices = priceRepository.findAll().stream()
                    .filter(p -> p.getProduct().getId().equals(productId))
                    .toList();

            if (!productPrices.isEmpty()) {
                String name = productPrices.get(0).getProduct().getName();
                Map<String, Double> marketPrices = new HashMap<>();

                for (var p : productPrices) {
                    marketPrices.put(p.getMarket().getName(), p.getPrice());
                }
                details.add(new CartResponse.ProductDetail(name, marketPrices));
            }
        }

        // 2. Calcular totais por mercado
        for (Market market : markets) {
            Double total = priceRepository.findAll().stream()
                    .filter(p -> request.getProductIds().contains(p.getProduct().getId()) && p.getMarket().getId().equals(market.getId()))
                    .mapToDouble(p -> p.getPrice())
                    .sum();
            totalsByMarket.put(market.getName(), total);
        }

        // Lógica para encontrar o melhor e o pior preço
        String bestMarket = Collections.min(totalsByMarket.entrySet(), Map.Entry.comparingByValue()).getKey();
        Double lowestPrice = round(totalsByMarket.get(bestMarket));
        Double highestPrice = Collections.max(totalsByMarket.entrySet(), Map.Entry.comparingByValue()).getValue();
        Double savings = round(highestPrice - lowestPrice);

        // Arredonda todos os totais no mapa para o JSON ficar limpo
        totalsByMarket.replaceAll((k, v) -> round(v));

        return new CartResponse(bestMarket, lowestPrice, savings, totalsByMarket, details);
    }

    // Metodo auxiliar para arredondar (adicione-o no final da classe, antes da última chave })
    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }


}