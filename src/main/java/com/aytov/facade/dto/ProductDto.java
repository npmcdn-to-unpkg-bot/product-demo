package com.aytov.facade.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductDto implements Transferable {
    private Long id;
    private String name;
    private String description;
    private List<ProductTagDto> tags;
    private MoneyDto price;
    private List<MoneyDto> pricePoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductTagDto> getTags() {
        return tags;
    }

    public void setTags(List<ProductTagDto> tags) {
       Collections.sort(tags, new Comparator<ProductTagDto>() {
            @Override
            public int compare(ProductTagDto o1, ProductTagDto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        this.tags = tags;
    }

    public MoneyDto getPrice() {
        return price;
    }

    public void setPrice(MoneyDto price) {
        this.price = price;
    }

    public List<MoneyDto> getPricePoints() {
        return pricePoints;
    }

    public void setPricePoints(List<MoneyDto> pricePoints) {
        this.pricePoints = pricePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDto that = (ProductDto) o;

        if (!name.equals(that.name)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        return price.equals(that.price);

    }

    @Override
    public int hashCode() {
        int result = 31 * name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", price=" + price +
                ", pricePoints=" + pricePoints +
                '}';
    }
}
