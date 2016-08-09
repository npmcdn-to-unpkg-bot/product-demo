package com.aytov.facade.dto;

import com.aytov.domain.model.Money;
import com.aytov.domain.model.Product;
import com.aytov.domain.model.ProductTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ProductDtoConverter implements DtoConverter<ProductDto, Product> {
    private ProductTagDtoConverter productTagDtoConverter = new ProductTagDtoConverter();

    public void setProductTagDtoConverter(ProductTagDtoConverter productTagDtoConverter) {
        this.productTagDtoConverter = productTagDtoConverter;
    }

    public ProductTagDtoConverter getProductTagDtoConverter() {
        return productTagDtoConverter;
    }

    @Override
    public ProductDto asDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());

        MoneyDto priceDto = new MoneyDto();
        priceDto.setValue(product.getPrice().getValue());
        priceDto.setCurrencyCode(product.getPrice().getCurrency().getCurrencyCode());
        productDto.setPrice(priceDto);

        productDto.setDescription(product.getDescription());

        List<ProductTagDto> tags = new ArrayList<>(product.getTags().size());
        product.getTags().forEach((tag -> {
            tags.add(productTagDtoConverter.asDto(tag));
        }));
        productDto.setTags(tags);

        List<MoneyDto> pricePoints = new ArrayList<>(product.getPricePoints().size());
        product.getPricePoints().forEach((pricePoint -> {
            MoneyDto pricePointDto = new MoneyDto();
            pricePointDto.setValue(pricePoint.getValue());
            pricePointDto.setCurrencyCode(pricePoint.getCurrency().getCurrencyCode());

            pricePoints.add(pricePointDto);
        }));
        productDto.setPricePoints(pricePoints);

        return productDto;
    }

    @Override
    public ProductsDto asDto(Collection<Product> entities) {
        List<ProductDto> productDtos = new ArrayList<>(entities.size());
        entities.forEach(product -> productDtos.add(asDto(product)));

        ProductsDto productsDto = new ProductsDto();
        productsDto.setList(productDtos);

        return productsDto;
    }

    @Override
    public Product asEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        List<ProductTag> tags = new ArrayList<>(productDto.getTags().size());
        productDto.getTags().forEach(productTagDto ->
                tags.add(productTagDtoConverter.asEntity(productTagDto)));
        product.setTags(tags);

        Money price = new Money(productDto.getPrice().getValue(), productDto.getPrice().getCurrencyCode());
        product.setPrice(price);

        return product;
    }
}
