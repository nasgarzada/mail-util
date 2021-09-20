package com.nicat.asgarzada.emailutil.functional;

/**
 * Functional interface for wrapping checked exceptions with return types.
 * @param <T>
 */
@FunctionalInterface
public interface GenericWrapper<T> {
    T wrap() throws Exception;
}
