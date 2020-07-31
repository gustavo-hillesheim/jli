package io.hill.jli.domain;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommandDefinition {

    private String command;
    private Map<String, String> namedArguments;
    private List<String> positionalArguments;
}
