package io.hill.jli;

import java.lang.reflect.InvocationTargetException;

public class CommandFactory {

    Object createCommand(Class<?> commandClass) throws JliException {
        try {
            return commandClass.getConstructor().newInstance();

        } catch (NoSuchMethodException e) {
            throw new JliException("Could not create " + commandClass + " instance since no default public constructor was found", e);

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new JliException("An exception has occurred while instantiating " + commandClass + " via it's default constructor", e);
        }
    }
}
