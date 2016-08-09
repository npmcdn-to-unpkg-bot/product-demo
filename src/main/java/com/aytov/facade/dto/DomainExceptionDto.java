package com.aytov.facade.dto;

public class DomainExceptionDto {
    private String key;
    private Object[] parameters;

    public DomainExceptionDto() {
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "DomainExceptionDto{" +
                "key='" + key + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
