package com.comparecarrinhos.controller;

import com.comparecarrinhos.model.Product;
import com.comparecarrinhos.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping
    public List<Product> list() { return repository.findAll();
    }
}