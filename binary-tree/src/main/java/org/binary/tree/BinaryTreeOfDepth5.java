package org.binary.tree;

public class BinaryTreeOfDepth5<T extends Comparable<T>> {

    private BinaryTree<T> tree;

    public BinaryTreeOfDepth5(BinaryTree<T> tree) {
        super();
        this.tree = tree;
    }
    
    public void print() {
        System.out.println("              %s              ");
        System.out.println("      %s              %s      ");
        System.out.println("  %s      %s      %s      %s  ");
        System.out.println("  %s    %s  %s  %s  %s  %s  %s  %s");
        System.out.println("  %s      %s ");
        System.out.println("%s  %s  %s  %s");
    }
    
    
    
    
    
}
