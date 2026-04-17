package com.comparecarrinhos;

import com.comparecarrinhos.dto.CartRequest;
import com.comparecarrinhos.dto.CartResponse;
import com.comparecarrinhos.model.Market;
import com.comparecarrinhos.model.Product;
import com.comparecarrinhos.model.ProductPrice;
import com.comparecarrinhos.repository.MarketRepository;
import com.comparecarrinhos.repository.ProductPriceRepository;
import com.comparecarrinhos.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private MarketRepository marketRepo;

    @Autowired
    private ProductPriceRepository priceRepo;

    @Test
    void deveEscolherOMercadoMaisBarato() {
        // GIVEN: Criamos um cenário controlado
        Market m1 = marketRepo.save(new Market(null, "Mercado Caro"));
        Market m2 = marketRepo.save(new Market(null, "Mercado Barato"));

        Product p = productRepo.save(new Product(null, "Produto Teste"));

        priceRepo.save(new ProductPrice(null, p, m1, 10.0)); // Caro: 10.0
        priceRepo.save(new ProductPrice(null, p, m2, 7.0));  // Barato: 7.0

        // Montamos o carrinho (1 unidade do Produto Teste)
        CartRequest.CartItemDTO item = new CartRequest.CartItemDTO();
        item.setProductId(p.getId());
        item.setQuantity(1);

        CartRequest request = new CartRequest();
        request.setItems(List.of(item));

        // WHEN: Chamamos a API de comparação
        ResponseEntity<CartResponse> response = restTemplate.postForEntity("/cart/compare", request, CartResponse.class);

        // THEN: Verificamos se o "Mercado Barato" foi o escolhido
        assertEquals("Mercado Barato", response.getBody().getBestMarket());
        assertEquals(7.0, response.getBody().getLowestPrice());
    }
}
