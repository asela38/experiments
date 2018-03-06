package org.binary.tree;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class Node<T> {

    private Node<T> right;
    private Node<T> left;

    @NonNull
    private T       value;

    public Optional<Node<T>> right() {
        return Optional.ofNullable(right);
    }

    public Optional<Node<T>> left() {
        return Optional.ofNullable(left);
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(%s-[%s]-%s)", String.valueOf(left), String.valueOf(value), String.valueOf(right));
    }
}
