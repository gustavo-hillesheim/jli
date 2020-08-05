package io.hill.jli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.hill.jli.annotation.Argument;
import io.hill.jli.annotation.Command;
import io.hill.jli.domain.ArgumentType;

import static org.junit.jupiter.api.Assertions.*;

class JliTest {

    static Runnable mockedRunnable;

    @BeforeEach
    void mockRunnable() {
        mockedRunnable = Mockito.mock(Runnable.class);
    }

    @Test
    void shouldRunCommandCorrectly() throws JliException {
        new Jli()
            .registerCommand(SimpleCommand.class)
            .getCommand("simple-command", "1" , "-bool");

        Mockito.verify(mockedRunnable);
    }

    @Command
    public static class SimpleCommand implements Runnable {

        @Argument(type = ArgumentType.POSITIONAL)
        private Integer tries;

        @Argument(type = ArgumentType.NAMED)
        private boolean bool;

        @Override
        public void run() {
            assertEquals(1, tries);
            assertTrue(bool);
            mockedRunnable.run();
        }
    }
}