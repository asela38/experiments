package org.binary.tree;

import org.junit.Test;

public class NodeTest {
    
    @Test
    public void toStringMethod() throws Exception {
        
        
          Node<Integer> node = Node.<Integer>builder().value(100).build();
          System.out.println(node);
          node.setLeft(Node.<Integer>builder().value(30).build());
          System.out.println(node);
          
          node.getLeft().setLeft(Node.<Integer>builder().value(20).build());
          node.getLeft().setRight(Node.<Integer>builder().value(50).build());
          
          System.out.println(node);
    }

}
