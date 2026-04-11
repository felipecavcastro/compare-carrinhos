package com.comparecarrinhos.controller;

import com.comparecarrinhos.model.*;
import com.comparecarrinhos.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
@RequestMapping("/import")
public class    ImportController {

    private final ProductRepository productRepo;
    private final MarketRepository marketRepo;
    private final ProductPriceRepository priceRepo;

    public ImportController(ProductRepository productRepo, MarketRepository marketRepo, ProductPriceRepository priceRepo) {
        this.productRepo = productRepo;
        this.marketRepo = marketRepo;
        this.priceRepo = priceRepo;
    }

    @PostMapping("/csv")
    public String importCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Erro: O arquivo enviado está vazio.";
        }

        int count = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignora linhas vazias
                if (line.trim().isEmpty()) continue;

                // Suporta vírgula ou ponto e vírgula
                String[] data = line.split("[,;]");

                if (data.length >= 3) {
                    String productName = data[0].trim();
                    String marketName = data[1].trim();
                    Double price = Double.parseDouble(data[2].trim());

                    // Busca ou Cria o Produto
                    Product product = productRepo.findByNameContainingIgnoreCase(productName).stream().findFirst()
                            .orElseGet(() -> productRepo.save(new Product(null, productName)));

                    // Busca ou Cria o Mercado
                    Market market = marketRepo.findAll().stream()
                            .filter(m -> m.getName().equalsIgnoreCase(marketName)).findFirst()
                            .orElseGet(() -> marketRepo.save(new Market(null, marketName)));

                    // Salva o Preço
                    priceRepo.save(new ProductPrice(null, product, market, price));
                    count++;
                }
            }
        } catch (Exception e) {
            return "Erro ao processar: " + e.getMessage();
        }
        return "Sucesso! " + count + " itens importados.";
    }

}
