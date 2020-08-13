package io.hill.jli;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.hill.jli.annotation.Argument;
import io.hill.jli.domain.ArgumentType;
import io.hill.jli.domain.CommandDefinition;

public class ArgumentBinder {

    private ArgumentConverter argumentConverter = new ArgumentConverter();

    void bindArguments(Object target, CommandDefinition commandDefinition) throws JliException {
        bindPositionalArguments(target, commandDefinition.getPositionalArguments());
        bindNamedArguments(target, commandDefinition.getNamedArguments());
    }

    void bindPositionalArguments(Object target, List<String> arguments) throws JliException {
        List<Field> fields = getFieldsByType(target, ArgumentType.POSITIONAL);
        for (int i = 0; i < arguments.size() && i < fields.size(); i++) {
            Field field = fields.get(i);
            if (!field.canAccess(target)) {
                field.setAccessible(true);
            }
            trySetValue(target, field, arguments.get(i));
        }
    }

    void bindNamedArguments(Object target, Map<String, String> arguments) throws JliException {
        List<Field> fields = getFieldsByType(target, ArgumentType.NAMED);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!field.canAccess(target)) {
                field.setAccessible(true);
            }
            trySetValue(target, field, arguments.get(fieldName));
        }
    }

    private void trySetValue(Object target, Field field, String argument) throws JliException {
        try {
            field.set(target, convertValue(argument, field.getType()));

        } catch (IllegalAccessException e) {
            throw new JliException(String.format(
                "Could not set value of %s.%s", target.getClass().getCanonicalName(), field.getName()
            ), e);
        }
    }

    private <T> T convertValue(String argument, Class<T> targetClass) throws JliException {
        T value = argumentConverter.convertToType(argument, targetClass);
        if (value == null) {
            throw new JliException(String.format(
                "Could not convert \"%s\" to type %s",
                argument, targetClass.getCanonicalName()));
        }
        return value;
    }

    private List<Field> getFieldsByType(Object target, ArgumentType type) {
        return Stream.of(target.getClass().getDeclaredFields())
            .filter(field -> field.getAnnotation(Argument.class) != null)
            .filter(field -> type == field.getAnnotation(Argument.class).type())
            .collect(Collectors.toList());
    }
}
