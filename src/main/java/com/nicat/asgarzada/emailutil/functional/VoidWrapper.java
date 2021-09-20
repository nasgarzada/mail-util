package com.nicat.asgarzada.emailutil.functional;

/**
 * Functional interface for wrapping checked exceptions without return type.
 */
@FunctionalInterface
public interface VoidWrapper {
    void wrap() throws Exception;
}
