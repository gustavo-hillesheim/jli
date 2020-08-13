package io.hill.jli;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.hill.jli.domain.CommandDefinition;

class CommandParser {

    private static final String NAMED_PARAMETER_SIGN = "-";
    private static final String NAMED_PARAMETER_SEPARATOR = "=";

    public CommandDefinition parseCommand(String[] args, Jli jli) {
        String[] commandSegments = getCommandSegments(args);
        Map<String, String> namedArguments = getNamedArguments(commandSegments);
        List<String> positionalArguments = getPositionalArguments(commandSegments);
        String commandName = positionalArguments.get(0);
        positionalArguments = positionalArguments.subList(1, positionalArguments.size());
        Class<?> argumentsType = getArgumentsType(jli.getCommandClass(commandName));

        return new CommandDefinition(commandName, namedArguments, positionalArguments, argumentsType);
    }

    private Class<?> getArgumentsType(Class<?> commandClass) {
        if (Consumer.class.isAssignableFrom(commandClass)) {
            Class<?> mostSpecificParameterType = null;
            for (Method method : commandClass.getDeclaredMethods()) {
                if (!"accept".equals(method.getName())) {
                    continue;
                }
                Class<?> methodParameterType = method.getParameterTypes()[0];
                if (mostSpecificParameterType == null || mostSpecificParameterType.isAssignableFrom(methodParameterType)) {
                    mostSpecificParameterType = methodParameterType;
                }
            }
            return mostSpecificParameterType;
        }
        return null;
    }

    String[] getCommandSegments(String[] args) {
        String command = String.join(" ", args).trim();
        List<String> commandSegments = new ArrayList<>();

        StringBuilder currentSegment = new StringBuilder();
        boolean isInsideQuotes = false;
        char quoteChar = 0;
        for (char c : command.toCharArray()) {
            if (c == ' ' && currentSegment.length() > 0 && !isInsideQuotes) {
                commandSegments.add(currentSegment.toString());
                currentSegment = new StringBuilder();

            } else if (c == '"') {
                if (isInsideQuotes && quoteChar == '"') {
                    isInsideQuotes = false;

                } else if (!isInsideQuotes) {
                    isInsideQuotes = true;
                    quoteChar = '"';
                } else {
                    currentSegment.append(c);
                }

            } else if (c == '\'') {
                if (isInsideQuotes && quoteChar == '\'') {
                    isInsideQuotes = false;

                } else if (!isInsideQuotes) {
                    isInsideQuotes = true;
                    quoteChar = '\'';
                } else {
                    currentSegment.append(c);
                }

            } else {
                currentSegment.append(c);
            }
        }
        if (currentSegment.length() > 0) {
            commandSegments.add(currentSegment.toString());
        }
        return commandSegments.toArray(new String[]{});
    }

    Map<String, String> getNamedArguments(String[] commandSegments) {
        return Stream.of(commandSegments)
            .filter(segment -> segment.startsWith(NAMED_PARAMETER_SIGN))
            .map(segment -> segment.substring(1))
            .collect(Collectors.toMap(
                segment -> segment.split(NAMED_PARAMETER_SEPARATOR)[0],
                segment -> {
                    if (segment.contains(NAMED_PARAMETER_SEPARATOR)) {
                        return segment.split(NAMED_PARAMETER_SEPARATOR)[1];
                    }
                    return Boolean.TRUE.toString();
                }
            ));
    }

    List<String> getPositionalArguments(String[] commandSegments) {
        return Stream.of(commandSegments)
            .filter(segment -> !segment.startsWith(NAMED_PARAMETER_SIGN))
            .collect(Collectors.toList());
    }
}
