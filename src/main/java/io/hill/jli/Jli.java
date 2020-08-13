package io.hill.jli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.hill.jli.annotation.Command;
import io.hill.jli.domain.CommandDefinition;
import lombok.AccessLevel;
import lombok.Setter;

public class Jli {

    private ArgumentConverter argumentConverter = new ArgumentConverter();
    private ArgumentBinder argumentBinder = new ArgumentBinder();
    private CommandParser commandParser = new CommandParser();
    private CommandFactory commandFactory = new CommandFactory();

    @Setter(AccessLevel.NONE)
    private Map<String, Class<?>> commandClasses = new HashMap<>();

    public void execute(String... args) throws JliException {
        CommandDefinition definition = commandParser.parseCommand(args, this);
        Class<?> commandClass = getCommandClass(definition.getCommand());
        Object command = commandFactory.createCommand(commandClass);
        argumentBinder.bindArguments(command, definition);

        if (command instanceof Runnable) {
            ((Runnable) command).run();

        } else if (command instanceof Consumer) {
            List<String> positionalArguments = definition.getPositionalArguments();
            Class<?> argumentsType = definition.getArgumentsType();
            Object arguments = argumentConverter.convertToType(positionalArguments, argumentsType);
            ((Consumer) command).accept(arguments);
        }
    }

    public Jli registerCommand(Class<?> commandClass) throws JliException {
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

    Class<?> getCommandClass(String commandName) {
        return commandClasses.get(commandName);
    }

    String createNameFromClass(Class<?> commandClass) {
        return Utils.toKebabCase(commandClass.getSimpleName());
    }
}
