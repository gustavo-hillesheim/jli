package io.hill.jli;

import java.util.HashMap;
import java.util.Map;

import io.hill.jli.annotation.Command;
import io.hill.jli.domain.CommandDefinition;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Jli {

    private ArgumentBinder argumentBinder = new ArgumentBinder();
    private CommandParser commandParser = new CommandParser();
    private CommandFactory commandFactory = new CommandFactory();

    @Setter(AccessLevel.NONE)
    private Map<String, Class<? extends Runnable>> commandClasses = new HashMap<>();

    public Runnable getCommand(String... args) throws JliException {
        CommandDefinition definition = commandParser.parseCommand(args);
        Class<? extends Runnable> commandClass = commandClasses.get(definition.getCommand());
        Runnable command = commandFactory.createCommand(commandClass);
        argumentBinder.bindArguments(command, definition);
        return command;
    }

    public Jli registerCommand(Class<? extends Runnable> commandClass) throws JliException {
        Command commandConfiguration = commandClass.getAnnotation(Command.class);
        if (commandConfiguration != null) {
            commandClasses.put(getCommandName(commandClass, commandConfiguration), commandClass);
        }
        return this;
    }

    String getCommandName(Class<?> commandClass, Command commandConfiguration) throws JliException {
        String specifiedName = commandConfiguration.name();
        if (Command.DEFAULT.equals(specifiedName)) {
            return createNameFromClass(commandClass);
        }
        if (specifiedName.contains(" ")) {
            throw new JliException("Name of command " + commandClass.getCanonicalName() + " contains spaces");
        }
        return specifiedName;
    }

    String createNameFromClass(Class<?> commandClass) {
        return Utils.toKebabCase(commandClass.getSimpleName());
    }
}
