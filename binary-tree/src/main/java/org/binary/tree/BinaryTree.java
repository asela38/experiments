package org.binary.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class BinaryTree<T extends Comparable<T>> {

    private Optional<Node<T>> root = Optional.empty();

    public BinaryTree() {
        super();
    }

    private BinaryTree(Node<T> root) {
        this();
        this.root = Optional.ofNullable(root);
    }

    public Optional<T> getRoot() {
        return root.map(node -> node.value());
    }

    public void insert(T t) {
        Function<T,Node<T>> createNode = tt -> new Node<T>(tt);

        Consumer<T> consumer = null;
        if (!root.isPresent())
            consumer = tt -> { root = Optional.of(createNode.apply(tt)); };
        else {
            Node<T> rn = root.get();
            if (rn.value().compareTo(t) < 0)
                if(rn.right().isPresent())
                    consumer = tt -> this.getRightTree().ifPresent( rt -> rt.insert(tt));
                else
                    consumer = tt ->  rn.setRight(createNode.apply(tt));
            else
                if(rn.left().isPresent())
                    consumer = tt -> this.getLeftTree().ifPresent(lt -> lt.insert(tt));
                else
                    consumer = tt ->  rn.setLeft(createNode.apply(tt));

        }
        
        consumer.accept(t);
    }

    public Optional<BinaryTree<T>> getRightTree() {
        return root.map(node -> node.right().orElse(null)).map(node -> new BinaryTree<T>(node));
    }

    public Optional<BinaryTree<T>> getLeftTree() {
        return root.map(node -> node.left().orElse(null)).map(node -> new BinaryTree<T>(node));
    }
    
    public List<T> postOrderTraverseList() {
        if (!root.isPresent())
            return Collections.emptyList();
        
        Node<T> rn = root.get();
        List<T> potList = new LinkedList<>();
        if(rn.left().isPresent())
            potList.addAll( this.getLeftTree().get().postOrderTraverseList() );
      
        potList.add(root.get().value());
        
        if(rn.right().isPresent())
            potList.addAll( this.getRightTree().get().postOrderTraverseList() );
        
      
        return potList;
    }

    @Override
    public String toString() {
        return String.valueOf(root);
    }

    
    @SafeVarargs
    public static <T extends Comparable<T>> BinaryTree<T> with(T...t) {
        BinaryTree<T> tree = new BinaryTree<>();
        Arrays.stream(t)
            .forEach(a -> tree.insert(a));
        return tree;
    }
}
