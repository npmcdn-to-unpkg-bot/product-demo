package com.aytov.domain.repository;

import com.aytov.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data Product Repository.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
