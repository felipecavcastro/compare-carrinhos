package com.comparecarrinhos.config;

import com.comparecarrinhos.model.*;
import com.comparecarrinhos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepo;
    private final MarketRepository marketRepo;
    private final ProductPriceRepository priceRepo;

    public DataSeeder(ProductRepository productRepo, MarketRepository marketRepo, ProductPriceRepository priceRepo) {
        this.productRepo = productRepo;
        this.marketRepo = marketRepo;
        this.priceRepo = priceRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // SÓ ENTRA AQUI SE O BANCO ESTIVER TOTALMENTE VAZIO
        if (productRepo.count() == 0) {
            System.out.println("🌱 Banco vazio! Semeando dados de teste...");

            // 1. Criar Mercados
            Market m1 = marketRepo.save(new Market(null, "Supermercado A"));
            Market m2 = marketRepo.save(new Market(null, "Mercado do Bairro B"));

            // 2. Criar Produtos
            Product p1 = productRepo.save(new Product(null, "Leite Integral 1L"));
            Product p2 = productRepo.save(new Product(null, "Café 500g"));
            Product p3 = productRepo.save(new Product(null, "Açúcar 1kg"));

            // 3. Criar Preços (Simulando diferenças para teste)
            priceRepo.save(new ProductPrice(null, p1, m1, 5.50));
            priceRepo.save(new ProductPrice(null, p1, m2, 5.99));

            priceRepo.save(new ProductPrice(null, p2, m1, 18.00));
            priceRepo.save(new ProductPrice(null, p2, m2, 16.50));

            priceRepo.save(new ProductPrice(null, p3, m1, 4.20));
            priceRepo.save(new ProductPrice(null, p3, m2, 4.50));

            System.out.println("✅ Dados de teste criados com sucesso!");
        } else {
            System.out.println("ℹ️ O banco já possui dados. Pulando a semente (seeder).");
        }
    }
}
