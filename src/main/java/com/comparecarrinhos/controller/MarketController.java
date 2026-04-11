package com.comparecarrinhos.controller;

import com.comparecarrinhos.model.Market;
import com.comparecarrinhos.repository.MarketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/markets")
public class MarketController {

    private final MarketRepository repository;

    public MarketController(MarketRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Market create(@RequestBody Market market) {
        return repository.save(market);
    }

    @GetMapping
    public List<Market> list() {
        return repository.findAll();
    }
}
