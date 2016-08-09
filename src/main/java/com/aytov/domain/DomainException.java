package com.aytov.domain;

public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 8419196441212228922L;

    private Type type;
    private Object[] parameters;

    private DomainException(Throwable throwable) {
        super(throwable);
    }

    public DomainException(Type type, Throwable throwable, Object... parameters) {
        super(throwable);
        this.type = type;
        this.parameters = parameters;
    }

    public DomainException(Type type, Object... parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public Type getType() {
        return type;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getLocalizationMsgKey() {
        return type.getLocalizationMsgKey();
    }

    public enum Type implements MessageResourceResolvable {
        ProductNotFound,
        ProductTagNotFound,
        InvalidProductName,
        InvalidProductPrice,
        InvalidProductTagName,
        UnsupportedCurrency;

        public String getLocalizationMsgKey() {
            return "message." + this.name();
        }
    }

    public interface MessageResourceResolvable {
        public String getLocalizationMsgKey();
    }
}