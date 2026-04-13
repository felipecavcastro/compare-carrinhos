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

        // 1. Detalhamento dos itens (para o usuário ver os preços individuais)
        for (var item : request.getItems()) {
            var productPrices = priceRepository.findAll().stream()
                    .filter(p -> p.getProduct().getId().equals(item.getProductId()))
                    .toList();

            if (!productPrices.isEmpty()) {
                String name = productPrices.get(0).getProduct().getName() + " (x" + item.getQuantity() + ")";
                Map<String, Double> marketPrices = new HashMap<>();
                for (var p : productPrices) {
                    marketPrices.put(p.getMarket().getName(), round(p.getPrice() * item.getQuantity()));
                }
                details.add(new CartResponse.ProductDetail(name, marketPrices));
            }
        }

        // 2. Cálculo do Total por Mercado (Ignorando quem não tem todos os itens)
        for (Market market : markets) {
            Double total = 0.0;
            boolean temTodosOsProdutos = true;

            for (var item : request.getItems()) {
                var priceOpt = priceRepository.findAll().stream()
                        .filter(p -> p.getProduct().getId().equals(item.getProductId()) && p.getMarket().getId().equals(market.getId()))
                        .findFirst();

                if (priceOpt.isPresent()) {
                    total += priceOpt.get().getPrice() * item.getQuantity();
                } else {
                    // Se falta um produto, esse mercado não pode entrar na soma total
                    temTodosOsProdutos = false;
                    break;
                }
            }

            if (temTodosOsProdutos && total > 0) {
                totalsByMarket.put(market.getName(), round(total));
            }
        }

        // 3. Verificação de segurança caso nenhum mercado tenha o carrinho completo
        if (totalsByMarket.isEmpty()) {
            return new CartResponse("Nenhum mercado possui todos os itens", 0.0, 0.0, totalsByMarket, details);
        }

        // 4. Lógica de melhor mercado
        String bestMarket = Collections.min(totalsByMarket.entrySet(), Map.Entry.comparingByValue()).getKey();
        Double lowestPrice = totalsByMarket.get(bestMarket);
        Double highestPrice = Collections.max(totalsByMarket.entrySet(), Map.Entry.comparingByValue()).getValue();
        Double savings = round(highestPrice - lowestPrice);

        return new CartResponse(bestMarket, lowestPrice, savings, totalsByMarket, details);
    }

    // Metodo auxiliar para arredondar (adicione-o no final da classe, antes da última chave })
    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }


}