package io.hill.jli;

public class ArgumentConverter {

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
