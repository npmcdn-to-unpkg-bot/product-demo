package com.aytov.facade.dto;

public class ProductTagDto implements Transferable {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

        ProductTagDto that = (ProductTagDto) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = 31 * name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductTagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
