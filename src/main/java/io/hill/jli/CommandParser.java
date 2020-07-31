package io.hill.jli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.hill.jli.domain.CommandDefinition;

class CommandParser {

    public CommandDefinition parseCommand(String[] args) {
        String[] commandSegments = getCommandSegments(args);
        Map<String, String> namedArguments = getNamedArguments(commandSegments);
        List<String> positionalArguments = getPositionalArguments(commandSegments);

        return new CommandDefinition(positionalArguments.get(0), namedArguments, positionalArguments);
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

    private Map<String, String> getNamedArguments(String[] commandSegments) {
        return new HashMap<>();
    }

    private List<String> getPositionalArguments(String[] commandSegments) {
        return new ArrayList<>();
    }
}
