package org.algorithms2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class HeapExploration {

    /*
     *  (2)___
     *   |    \
     *  (4)__  (5)_____
     *   |   \    \    \
     *  (6)_  (6) (8)  (7)
     *   |  \
     *  (9) (7)
     *  
     *  {2,4,5,6,6,8,7,9,7}
     * 
     */
    public int[] createHeap() {
        return new int[] { 2, 4, 5, 6, 6, 8, 7, 9, 7 };
    }

    @Test
    public void testInsert() throws Exception {

        int[] heap = createHeap();

        heap = insert(heap, 3);
        /*
         *  (2)___
         *   |    \
         *  (3)__  (5)_____
         *   |   \    \    \
         *  (6)_  (4) (8)  (7)
         *   |  \   \
         *  (9) (7) (6)
         *  
         *  {2,3,5,6,4,6,8,7,9,7,6}
         * 
         */
        assertThat(Arrays.toString(heap), is("[2, 3, 5, 6, 4, 8, 7, 9, 7, 6]"));

        heap = insert(heap, 2);
        /*
         *  (2)______
         *   |       \
         *  (2)__     (5)_____
         *   |   \       \    \
         *  (6)_  (3)___  (8)  (7)
         *   |  \   \   \
         *  (9) (7) (6) (4)
         *  
         *  {2,3,5,6,4,6,8,7,9,7,6}
         * 
         */
        assertThat(Arrays.toString(heap), is("[2, 2, 5, 6, 3, 8, 7, 9, 7, 6, 4]"));

        heap = insert(heap, 1);
        /*
         *  (1)______
         *   |       \
         *  (2)__     (2)_____
         *   |   \       \    \
         *  (6)_  (3)___  (5)  (7)
         *   |  \   \   \   \
         *  (9) (7) (6) (4) (8)
         *  
         *  {2,3,5,6,4,6,8,7,9,7,6}
         * 
         */
        assertThat(Arrays.toString(heap), is("[1, 2, 2, 6, 3, 5, 7, 9, 7, 6, 4, 8]"));

    }

    @Test
    public void consturctHeapFromEmpty() throws Exception {
        int[] heap = new int[0];
        System.out.println(Arrays.toString(heap = insert(heap, 10)));
        System.out.println(Arrays.toString(heap = insert(heap, 15)));
        System.out.println(Arrays.toString(heap = insert(heap, 20)));
        System.out.println(Arrays.toString(heap = insert(heap, 14)));
        System.out.println(Arrays.toString(heap = insert(heap, 8)));

    }

    private int[] insert(int[] heapOld, int i) {
        int newIndex = heapOld.length;
        int[] heap = Arrays.copyOf(heapOld, newIndex + 1);
        heap[newIndex] = i;
        int parentIndex = 0;
        System.out.println(Arrays.toString(heap));
        while (heap[parentIndex = Math.max((newIndex - 1) >> 1, 0)] > heap[newIndex]) {
            int temp = heap[newIndex];
            heap[newIndex] = heap[parentIndex];
            heap[newIndex = parentIndex] = temp;
            System.out.println(Arrays.toString(heap));
        }
        return heap;
    }

    @Test
    public void testTake() throws Exception {

        /*
         *  (2)___
         *   |    \
         *  (4)__  (5)_____
         *   |   \    \    \
         *  (6)_  (6) (8)  (7)
         *   |  \
         *  (9) (7)
         *  
         *  {2,4,5,6,6,8,7,9,7}
         * 
         */
        int[] heap = new int[] { 2, 4, 5, 6, 6, 8, 7, 9, 7 };
        int x = take(heap);
        heap = Arrays.copyOf(heap, heap.length - 1);

        /*
         *  (4)___
         *   |    \
         *  (6)__  (5)_____
         *   |   \    \    \
         *  (7)   (6) (8)  (7)
         *   |   
         *  (9)    
         * 
         */

        /*
         *  (4)___
         *   |    \
         *  (6)__  (5)_____
         *   |   \    \    \
         *  (6)   (7) (8)  (7)
         *   |   
         *  (9)    
         * 
         */
        assertThat(Arrays.toString(heap), is("[4, 6, 5, 7, 6, 8, 7, 9]"));

    }

    private int take(int[] heap) {
        int r = heap[0];

        heap[0] = heap[heap.length - 1];
        int i = 1;
  //      System.out.println(Arrays.toString(heap));
        i = siftDown(heap, i);

        return r;
    }

    private int siftDown(int[] heap, int i) {
        while (2 * i < heap.length) {
            int c = 2 * i + (heap[2 * i - 1] <= heap[Math.min(2 * i, heap.length - 1)] ? 0 : 1);
            if (heap[c - 1] < heap[i - 1]) {
                int temp = heap[c - 1];
                heap[c - 1] = heap[i - 1];
                heap[i - 1] = temp;
            }
            i = c;
            // System.out.println(Arrays.toString(heap));
        }
        return i;
    }

    private void heapify(int[] heap) {
        for (int i = heap.length >> 1; i > 0; i--) {
            siftDown(heap, i);
        }
    }

    @Test
    public void testOrder() throws Exception {
        int[] heap = ThreadLocalRandom.current().ints(0, 20).limit(10).toArray();
        System.out.println(Arrays.toString(heap));
        heapify(heap);
        System.out.println(Arrays.toString(heap));
        for (int i = 0; i < 10; i++) {
            System.out.print(take(heap) + " ");
            heap = Arrays.copyOf(heap, heap.length - 1);
        }
    }

}
