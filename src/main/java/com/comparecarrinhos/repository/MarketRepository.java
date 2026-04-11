package com.comparecarrinhos.repository;

import com.comparecarrinhos.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
