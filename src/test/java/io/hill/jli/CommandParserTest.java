package io.hill.jli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void shouldParseCommandSegments() {
        String[] input = "jli positional-one positional two \"string with spaces\" 'string inside \"quotes\"'".split(" ");
        String[] expectedOutput = new String[] {"jli", "positional-one", "positional", "two", "string with spaces", "string inside \"quotes\""};
        String[] commandSegments = new CommandParser().getCommandSegments(input);
        assertArrayEquals(commandSegments, expectedOutput);
    }
}