package com.aytov.facade.dto;

import com.aytov.domain.model.ProductTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductTagDtoConverter implements DtoConverter<ProductTagDto, ProductTag> {

    @Override
    public ProductTagDto asDto(ProductTag tag) {
        ProductTagDto tagDto = new ProductTagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    @Override
    public ProductTagsDto asDto(Collection<ProductTag> tags) {
        List<ProductTagDto> tagDtos = new ArrayList<>(tags.size());
        tags.forEach((tag -> {
            ProductTagDto tagDto = new ProductTagDto();
            tagDto.setId(tag.getId());
            tagDto.setName(tag.getName());
            tagDtos.add(tagDto);
        }));
        ProductTagsDto productTagsDto = new ProductTagsDto();
        productTagsDto.setList(tagDtos);

        return productTagsDto;
    }

    @Override
    public ProductTag asEntity(ProductTagDto productTagDto) {
        ProductTag tag = new ProductTag();
        tag.setId(productTagDto.getId());
        tag.setName(productTagDto.getName());
        return tag;
    }
}
