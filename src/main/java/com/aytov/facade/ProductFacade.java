package com.aytov.facade;

import com.aytov.domain.model.Money;
import com.aytov.domain.model.Product;
import com.aytov.domain.service.ProductService;
import com.aytov.facade.dto.MoneyDto;
import com.aytov.facade.dto.ProductDto;
import com.aytov.facade.dto.ProductDtoConverter;
import com.aytov.facade.dto.ProductsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductFacade extends ProductDtoConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ProductFacade.class);

    @Inject
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    public ProductDto getProduct(@PathVariable("productId") long id) {
        LOG.debug("Getting product with id:" + id);

        return asDto(productService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ProductsDto getAllProducts() {
        LOG.debug("Getting all products...");

        List<Product> products = productService.getAllProducts();

        LOG.debug("Products count:" + products.size());
        return asDto(products);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        LOG.debug("Creating product: " + productDto);

        Product product = productService.save(asEntity(productDto));
        LOG.debug("Created product with id: " + product.getId());

        return asDto(product);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{productId}")
    public ProductDto updateProduct(@PathVariable("productId") long id, @RequestBody ProductDto productDto) {

        LOG.debug("Updating product: " + productDto);

        productService.getById(id);
        Product product = productService.save(asEntity(productDto));

        LOG.debug("Updated product with id:" + product.getId());

        return asDto(product);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{productId}/price")
    public ProductDto updateProductPrice(@PathVariable("productId") long id, @RequestBody MoneyDto priceDto) {

        LOG.debug("Updating product with id:" + id + " with price: " + priceDto);

        Product product = productService.getById(id);
        productService.updateProductPrice(product, new Money(priceDto.getValue(), priceDto.getCurrencyCode()));

        LOG.debug("Updated price of product with id:" + product.getId());

        return asDto(product);
    }
}