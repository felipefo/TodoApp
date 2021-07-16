package com.todo.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    AFAZER("Afazer"),
    FAZENDO("Fazendo"),
    FEITO("Feito");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
