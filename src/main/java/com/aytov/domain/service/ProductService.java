package com.aytov.domain.service;

import com.aytov.domain.model.Money;
import com.aytov.domain.model.Product;

import java.util.List;

public interface ProductService {
    /**
     * Gets all products.
     * @return List of products
     */
    List<Product> getAllProducts();

    /**
     * Getsa product by id.
     * @param id
     * @return
     */
    Product getById(Long id);

    /**
     * Creates or update a product.
     * @param product
     * @return
     */
    Product save(Product product);

    /**
     * Updates product's price.
     * @param product
     * @param price
     * @return
     */
    Product updateProductPrice(Product product, Money price);
}
