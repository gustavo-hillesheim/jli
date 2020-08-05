package io.hill.jli;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentBinderTest {

    @Test
    void shouldBindStringPositionalArgumentCorrectly() throws JliException {
        ArgumentBinder binder = new ArgumentBinder();
        TestCommand testCommand = new TestCommand();

        binder.bindPositionalArguments(testCommand, List.of("valor"));

        assertEquals("valor", testCommand.getPositionalArgument());
    }

    @Test
    void shouldBindNamedArgumentsCorrectly() throws JliException {
        ArgumentBinder binder = new ArgumentBinder();
        TestCommand testCommand = new TestCommand();

        binder.bindNamedArguments(testCommand, Collections.singletonMap("namedArgument", "valor"));

        assertEquals("valor", testCommand.getNamedArgument());
    }
}