package io.hill.jli;

public interface Utils {

    static String toKebabCase(String value) {
        return value.replaceAll("([A-Z])([a-z0-9]*)", "-$1$2")
            .toLowerCase()
            .substring(1);
    }
}
