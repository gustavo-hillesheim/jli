package io.hill.jli;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void shouldParseCommandSegments() {
        String[] input = "jli positional-one positional two \"string with spaces\" 'string inside \"quotes\"'".split(" ");
        String[] expectedOutput = new String[] {"jli", "positional-one", "positional", "two", "string with spaces", "string inside \"quotes\""};
        String[] commandSegments = new CommandParser().getCommandSegments(input);
        assertArrayEquals(commandSegments, expectedOutput);
    }

    @Test
    void shouldGetNamedParameters() {
        String[] input = new String[] {"jli", "positional-one", "-named=one", "positional-two", "-named-two=value", "-flag", "-named-string=Value string"};
        Map<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("named", "one");
        expectedOutput.put("named-two", "value");
        expectedOutput.put("flag", "true");
        expectedOutput.put("named-string", "Value string");
        assertEquals(expectedOutput, new CommandParser().getNamedArguments(input));
    }

    @Test
    void shouldGetPositionalParameters() {
        String[] input = new String[] {"jli", "positional-one", "-named=one", "positional-two", "-named-two=value", "-flag", "-named-string=Value string"};
        List<String> expectedOutput = Arrays.asList("jli", "positional-one", "positional-two");
        assertEquals(expectedOutput, new CommandParser().getPositionalArguments(input));
    }
}