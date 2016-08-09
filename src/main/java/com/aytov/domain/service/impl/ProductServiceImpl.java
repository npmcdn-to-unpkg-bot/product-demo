package com.aytov.domain.service.impl;

import com.aytov.domain.DomainException;
import com.aytov.domain.model.Money;
import com.aytov.domain.model.Product;
import com.aytov.domain.model.ProductTag;
import com.aytov.domain.repository.ProductRepository;
import com.aytov.domain.repository.ProductTagRepository;
import com.aytov.domain.service.CurrencyConverter;
import com.aytov.domain.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private CurrencyConverter currencyConverter;
    @Inject
    private ProductValidator productValidator;
    @Inject
    private ProductTagRepository productTagRepository;

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setCurrencyConverter(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void setProductValidator(ProductValidator productValidator) {
        this.productValidator = productValidator;
    }

    public void setProductTagRepository(ProductTagRepository productTagRepository) {
        this.productTagRepository = productTagRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> addPricePointsTo(product));
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        Product product = productRepository.findOne(id);

        if (product == null) {
            throw new DomainException(DomainException.Type.ProductNotFound);
        }

        return addPricePointsTo(product);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        productValidator.validate(product);

        product.setTags(createTags(product.getTags()));

        Product savedProduct = productRepository.save(product);
        return addPricePointsTo(savedProduct);
    }

    @Override
    @Transactional
    public Product updateProductPrice(Product product, Money price) {
        product.setPrice(price);
        return save(product);
    }

    private Product addPricePointsTo(Product product) {
        product.setPricePoints(currencyConverter.getDefaultPricePoints(product.getPrice()));
        return product;
    }

    private List<ProductTag> createTags(List<ProductTag> tags) {
        Set<ProductTag> savedTags = new HashSet<>(tags.size());

        tags.forEach(tag -> {
            ProductTag savedTag = productTagRepository.findProductTagByName(tag.getName());

            if (savedTag == null) {
                savedTag = productTagRepository.save(tag);
            }
            savedTags.add(savedTag);
        });

        return new ArrayList<>(savedTags);
    }
}
