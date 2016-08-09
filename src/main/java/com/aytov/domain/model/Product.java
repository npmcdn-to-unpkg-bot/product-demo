package com.aytov.domain.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "PRODUCT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@DynamicInsert
@DynamicUpdate
public class Product extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(name = "PRODUCT_X_TAG", joinColumns = {@JoinColumn(name = "TAG_ID")}, inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")})
    @OrderBy("name")
    private List<ProductTag> tags = Collections.EMPTY_LIST;

    @Column(name = "PRICE")
    @Convert(converter = MoneyConverter.class)
    private Money price = Money.ZERO;

    @Transient
    private List<Money> pricePoints = Collections.EMPTY_LIST;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public List<ProductTag> getTags() {
        return tags;
    }

    public void setTags(List<ProductTag> tags) {
        this.tags = tags;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public List<Money> getPricePoints() {
        return pricePoints;
    }

    public void setPricePoints(List<Money> pricePoints) {
        this.pricePoints = pricePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        if (!name.equals(product.name)) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (tags != null ? !tags.equals(product.tags) : product.tags != null) return false;
        return price.equals(product.price);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
