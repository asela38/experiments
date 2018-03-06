package org.binary.tree;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Node<T> {

    private Node<T> right;
    private Node<T> left;
    private T       value;

    @Override
    public String toString() {
        return String.format("(%s-[%s]-%s)", String.valueOf(left), String.valueOf(value), String.valueOf(right));
    }
}
