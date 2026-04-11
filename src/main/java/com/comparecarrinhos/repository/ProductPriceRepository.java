package com.comparecarrinhos.repository;

import com.comparecarrinhos.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    // Isso aqui busca os preços de um produto e já traz do menor para o maior!
    List<ProductPrice> findByProductIdOrderByPriceAsc(Long productId);
}
