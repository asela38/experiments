package org.java.monads;

import java.util.function.Function;

public class Try<I> {

    private I i;

    private Try(I i) {
        this.i = i;
    }

    // Type Constructor
    public static <O> Try<O> of(O instance) {
        return new Try<O>(instance);
    }

    // Bind
    public <O> Try<O> attempt(Function<I, O> function) {
        try {
            O o = function.apply(i);
            return new Try<O>(o);
        } catch (Throwable t) {
            return new Try<O>(null);
        }
    }

    // Return
    public I get() {
        return i;
    }

    // Return
    public I getOrElse(I defaultValue) {
        return i == null ? defaultValue : i;
    }
}
