package io.hill.jli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentConverterTest {

    @Test
    void shouldConvertToLong() {
        assertEquals(1L, new ArgumentConverter().convertToType("1", Long.class));
        assertEquals(1, new ArgumentConverter().convertToType("1", long.class));
    }

    @Test
    void shouldConvertToInteger() {
        assertEquals(Integer.valueOf(1), new ArgumentConverter().convertToType("1", Integer.class));
        assertEquals(1, new ArgumentConverter().convertToType("1", int.class));
    }

    @Test
    void shouldConvertToDouble() {
        assertEquals(1D, new ArgumentConverter().convertToType("1", Double.class));
        assertEquals(1d, new ArgumentConverter().convertToType("1", double.class));
    }

    @Test
    void shouldConvertToFloat() {
        assertEquals(1F, new ArgumentConverter().convertToType("1", Float.class));
        assertEquals(1f, new ArgumentConverter().convertToType("1", float.class));
    }

    @Test
    void shouldConvertToString() {
        assertEquals("1", new ArgumentConverter().convertToType("1", String.class));
    }

    @Test
    void shouldConvertToBoolean() {
        assertTrue(new ArgumentConverter().convertToType("true", Boolean.class));
        assertFalse(new ArgumentConverter().convertToType("false", boolean.class));
    }

    @Test
    void shouldConvertToEnum() {
        assertEquals(TestEnum.VALOR_ENUM, new ArgumentConverter().convertToType("VALOR_ENUM", TestEnum.class));
        assertEquals(TestEnum.VALOR_ENUM, new ArgumentConverter().convertToType("valor_enum", TestEnum.class));
    }

    enum TestEnum {
        VALOR_ENUM
    }
}