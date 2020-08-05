package io.hill.jli;

import io.hill.jli.annotation.Argument;
import io.hill.jli.annotation.Command;
import io.hill.jli.domain.ArgumentType;
import lombok.Getter;

@Getter
@Command
public class TestCommand implements Runnable {

    @Argument(type = ArgumentType.POSITIONAL)
    String positionalArgument;

    @Argument(type = ArgumentType.NAMED)
    String namedArgument;

    @Override
    public void run() {

    }
}
