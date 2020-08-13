package io.hill.jli.domain;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArgumentSettings {

    private Field field;
    private int position;
    private ArgumentType type;
}
