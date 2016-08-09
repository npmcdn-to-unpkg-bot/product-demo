package com.aytov.domain.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRODUCT_TAG", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@DynamicInsert
@DynamicUpdate
public class ProductTag extends AbstractEntity <Long>{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "TAG_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTag that = (ProductTag) o;

        if (id != null && id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id != null ? id ^ (id >>> 32) : 31);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
