package io.hill.jli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

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
    void shouldExecuteRunnableCommandCorrectly() throws JliException {
        new Jli()
            .registerCommand(RunnableCommand.class)
            .execute("runnable-command", "1" , "-bool");

        Mockito.verify(mockedRunnable);
    }

    @Test
    void shouldExecuteConsumerCommandCorrectly() throws JliException {
        new Jli()
            .registerCommand(ConsumerCommand.class)
            .execute("consumer-command", "parameter");

        Mockito.verify(mockedRunnable);
    }

    @Command
    public static class RunnableCommand implements Runnable {

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

    @Command
    public static class ConsumerCommand implements Consumer<String> {

        @Override
        public void accept(String args) {
            assertEquals("parameter", args);
            mockedRunnable.run();
        }
    }
}