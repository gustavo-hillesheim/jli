package io.hill.jli.domain;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandDefinition {

    private String command;
    private Map<String, String> namedArguments;
    private List<String> positionalArguments;

    /**
     * Only available when command implements {@link java.util.function.Consumer}
     */
    private Class<?> argumentsType;
}
