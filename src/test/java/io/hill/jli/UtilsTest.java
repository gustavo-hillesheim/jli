package io.hill.jli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void shouldCreateNameFromClassCorrectly() {
        assertEquals("test-command", Utils.toKebabCase("TestCommand"));
    }
}