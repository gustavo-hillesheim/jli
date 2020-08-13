package io.hill.jli;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentConverter {

    public <T> T convertToType(List<String> values, Class<T> type) throws JliException {
        if (type.isArray()) {
            return (T) convertListValuesToType(values, type.getComponentType());

        } else if (values.size() == 1) {
            return convertToType(values.get(0), type);
        }
        throw new JliException(String.format("Could not convert values [%s] to type %s since neither " +
            "type is an array nor the values have only one entry", String.join(", ", values), type.getCanonicalName()));
    }

    private <T> T[] convertListValuesToType(List<String> values, Class<T> type) {
        return values
            .stream()
            .map(value -> convertToType(value, type))
            .collect(Collectors.toList())
            .toArray((T[]) Array.newInstance(type, 0));
    }

    @SuppressWarnings("unchecked")
    public <T> T convertToType(String value, Class<T> type) {
        if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
            return (T) Integer.valueOf(value);

        } else if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
            return (T) Long.valueOf(value);

        } else if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)) {
            return (T) Float.valueOf(value);

        } else if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) {
            return (T) Double.valueOf(value);

        } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
            return (T) Boolean.valueOf(value);

        } else if (String.class.isAssignableFrom(type)) {
            return (T) value;

        } else if (Character.class.isAssignableFrom(type)) {
            return (T) Character.valueOf(value.toCharArray()[0]);

        } else if (type.isEnum()) {
            for (Object enumValue : type.getEnumConstants()) {
                if (enumValue.toString().equalsIgnoreCase(value)) {
                    return (T) enumValue;
                }
            }
        }

        return null;
    }
}
