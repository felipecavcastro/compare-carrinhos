package com.comparecarrinhos.repository;

import com.comparecarrinhos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Busca produtos que CONTÉM o nome ignorando maiúsculas/minúsculas
    List<Product> findByNameContainingIgnoreCase(String name);
}
