package io.hill.jli;

public class JliException extends Exception {

    public JliException(String message) {
        super(message);
    }

    public JliException(String message, Throwable cause) {
        super(message, cause);
    }
}
