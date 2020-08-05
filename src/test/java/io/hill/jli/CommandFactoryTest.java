package io.hill.jli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void shouldCreateTestClassCorrectly() throws JliException {
        assertNotNull(new CommandFactory().createCommand(TestCommand.class));
    }

    @Test
    void shouldThrowExceptionWhenClassHasParameterizedConstructor() {
        assertThrows(
            JliException.class,
            () -> new CommandFactory().createCommand(ParameterizedTestCommand.class)
        );
    }

    @Test
    void shouldThrowExceptionWhenClassConstructorThrowsException() {
        assertThrows(
            JliException.class,
            () -> new CommandFactory().createCommand(ExceptionTestCommand.class)
        );
    }

    static class ParameterizedTestCommand implements Runnable {
        public ParameterizedTestCommand(Object parameter) {}

        @Override
        public void run() {}
    }

    static class ExceptionTestCommand implements Runnable {
        public ExceptionTestCommand() throws Exception {
            throw new Exception();
        }

        @Override
        public void run() {}
    }
}