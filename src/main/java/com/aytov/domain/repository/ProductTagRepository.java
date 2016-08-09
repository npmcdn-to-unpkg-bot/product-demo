package com.aytov.domain.repository;

import com.aytov.domain.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data Product Tag Repository.
 */
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    ProductTag findProductTagByName(@Param("name") String name);
}
