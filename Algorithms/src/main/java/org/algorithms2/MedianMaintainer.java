package org.algorithms2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LongSummaryStatistics;

import org.junit.Test;

public class MedianMaintainer {
    static enum HeapType {
        MAX((a, b) -> a.compareTo(b)), MIN((a, b) -> b.compareTo(a));

        private Comparator<Integer> comparator;

        private HeapType(Comparator<Integer> comparator) {
            this.comparator = comparator;
        }

        public int compare(int i, int j) {
            return comparator.compare(i, j);
        }
    }

    private static class Heap {
        private int      lastIndex = -1;
        private HeapType type;

        private int[]    heap;

        private Heap(HeapType type, int initialSize) {
            this.type = type;
            heap = new int[initialSize];
        }

        // increase heap by 10%
        private void grow() {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }

        public void insert(int... i) {
            for (int j : i) {
                insert(j);
            }
        }

        public void insert(int i) {
            if (++lastIndex >= heap.length - 3) {
                grow();
            }
            heap[lastIndex] = i;
            siftUp(lastIndex);
        }

        public int peek() {
            return heap[0];
        }

        public int take() {
            if (lastIndex < 0) {
                throw new IllegalStateException("Heap is empty");
            }
            int r = heap[0];
            heap[0] = heap[lastIndex--];
            siftDownRoot();

            return r;

        }

        private void siftDownRoot() {
            int index = 2;

            while (lastIndex + 1 >= index) {

                if (lastIndex >= index) {
                    index += type.compare(heap[index - 1], heap[index]) > 0 ? 0 : 1;
                }

                if (type.compare(heap[index / 2 - 1], heap[index - 1]) < 0) {
                    swap(index - 1, index / 2 - 1);
                }

                index <<= 1;
            }
        }

        private void siftUp(int index) {
            index += 1;
            if (index < 2)
                return;
            while (type.compare(heap[index - 1], heap[(index >> 1) - 1]) > 0) {
                swap(index - 1, index / 2 - 1);
                index >>= 1;

                if (index < 2)
                    break;
            }
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;

        }

        public int size() {
            return lastIndex + 1;
        }

    }

    @Test
    public void testMinHeap() throws Exception {
        Heap minHeap = new Heap(HeapType.MIN, 8);
        minHeap.insert(4, 3, 1, 5, 6, 2, 3, 1);
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 8)), is("[1, 1, 2, 4, 6, 3, 3, 5]"));
        assertThat(minHeap.take(), is(1));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 7)), is("[1, 4, 2, 5, 6, 3, 3]"));
        assertThat(minHeap.take(), is(1));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 6)), is("[2, 4, 3, 5, 6, 3]"));
        assertThat(minHeap.take(), is(2));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 5)), is("[3, 4, 3, 5, 6]"));
        assertThat(minHeap.take(), is(3));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 4)), is("[3, 4, 6, 5]"));
        assertThat(minHeap.take(), is(3));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 3)), is("[4, 5, 6]"));
        assertThat(minHeap.take(), is(4));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 2)), is("[5, 6]"));
        assertThat(minHeap.take(), is(5));
        assertThat(Arrays.toString(Arrays.copyOf(minHeap.heap, 1)), is("[6]"));
        assertThat(minHeap.take(), is(6));
    }

    @Test
    public void testMaxHeapInsert() throws Exception {
        Heap maxHeap = new Heap(HeapType.MAX, 8);
        maxHeap.insert(4, 3, 1, 5, 6, 2, 3, 1);
        assertThat(Arrays.toString(Arrays.copyOf(maxHeap.heap, 8)), is("[6, 5, 3, 3, 4, 1, 2, 1]"));
        assertThat(maxHeap.take(), is(6));
        assertThat(maxHeap.take(), is(5));
        assertThat(maxHeap.take(), is(4));
        assertThat(maxHeap.take(), is(3));
        assertThat(maxHeap.take(), is(3));
        assertThat(maxHeap.take(), is(2));
        assertThat(maxHeap.take(), is(1));
        assertThat(maxHeap.take(), is(1));
    }

    @Test
    public void testMaxHeapInsert2() throws Exception {
        Heap maxHeap = new Heap(HeapType.MAX, 8);
        maxHeap.insert(1, 10, 100, 2);
        maxHeap.take();
        assertThat(Arrays.toString(Arrays.copyOf(maxHeap.heap, 3)), is("[10, 2, 1]"));

    }

    @Test
    public void testMidPoint() throws Exception {

        assertThat(10 >> 1, is(5));
        assertThat(10 + 1 >> 1, is(5));
        assertThat(10 - 1 >> 1, is(4));
        assertThat(10 - 1 >> 1 + 1, is(2));
    }

    private static class MedianTracker {
        private Heap maxHeap;
        private Heap minHeap;

        public MedianTracker(int initCapacity) {
            maxHeap = new Heap(HeapType.MAX, initCapacity + 1 >> 1);
            minHeap = new Heap(HeapType.MIN, initCapacity >> 1);
        }

        public int median() {
            return maxHeap.peek();
        }

        public boolean balance() {
            while (maxHeap.size() < minHeap.size())
                maxHeap.insert(minHeap.take());

            boolean isSizeEven = (maxHeap.size() + minHeap.size()) % 2 == 0;

            if (isSizeEven) {
                if (maxHeap.size() == minHeap.size())
                    return true;
                else
                    minHeap.insert(maxHeap.take());
            } else {
                if (maxHeap.size() == minHeap.size() + 1)
                    return true;
                else
                    minHeap.insert(maxHeap.take());
            }
            return false;
        }

        public MedianTracker feed(int... i) {
            for (int j : i) {
                feed(j);
            }
            return this;
        }

        public MedianTracker feed(int i) {
            if (i > median())
                minHeap.insert(i);
            else
                maxHeap.insert(i);

            while (!balance())
                ;
            return this;
        }

    }

    @Test
    public void testMedianMaintainer() throws Exception {
        MedianTracker medianTracker = new MedianTracker(10);
        assertThat(medianTracker.feed(1).median(), is(1));
        assertThat(medianTracker.feed(2).median(), is(1));
        assertThat(medianTracker.feed(2).median(), is(2));
        assertThat(medianTracker.feed(1).median(), is(1));
        assertThat(medianTracker.feed(2).median(), is(2));

        assertThat(new MedianTracker(10).feed(1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1).median(), is(1));
    }

    @Test
    public void nonTrivial() throws Exception {
        MedianTracker medianTracker = new MedianTracker(10);
        assertThat(medianTracker.feed(63, 27, 16).median(), is(27));

    }

    @Test
    public void testMedianWithFile() throws Exception {
        MedianTracker tracker = new MedianTracker(1000 << 1);
        LongSummaryStatistics summaryStatistics = Files
                .readAllLines(new File("C:\\Asela\\mydocs\\Coursera\\Median.txt").toPath()).stream()
                .mapToInt(Integer::parseInt).map(x -> tracker.feed(x).median()).peek(System.out::println).asLongStream()
                .summaryStatistics();
        System.out.println(summaryStatistics);
        System.out.printf("%s-%s%n", summaryStatistics.getSum(), summaryStatistics.getSum() % 10000);
    }

    @Test
    public void testMedianWithFile2() throws Exception {
        MedianTracker tracker = new MedianTracker(100 << 1);
        LongSummaryStatistics summaryStatistics = Files
                .readAllLines(new File("C:\\Asela\\mydocs\\Coursera\\Mediank.txt").toPath()).stream()
                .mapToInt(Integer::parseInt).peek(a -> System.out.printf("M:%s, SUM:", a))
                .map(x -> tracker.feed(x).median()).peek(System.out::println).asLongStream().summaryStatistics();
        System.out.println(summaryStatistics);
        System.out.printf("%s-%s%n", summaryStatistics.getSum(), summaryStatistics.getSum() % 10000);
    }

}
