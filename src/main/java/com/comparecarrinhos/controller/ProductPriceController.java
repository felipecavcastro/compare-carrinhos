package com.comparecarrinhos.controller;

import com.comparecarrinhos.model.ProductPrice;
import com.comparecarrinhos.repository.ProductPriceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
public class ProductPriceController {

    private final ProductPriceRepository repository;

    public ProductPriceController(ProductPriceRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ProductPrice create(@RequestBody ProductPrice productPrice) {
        return repository.save(productPrice);
    }

    @GetMapping("/compare/{productId}")
    public List<ProductPrice> compare(@PathVariable Long productId) {
        return repository.findByProductIdOrderByPriceAsc(productId);
    }

}
