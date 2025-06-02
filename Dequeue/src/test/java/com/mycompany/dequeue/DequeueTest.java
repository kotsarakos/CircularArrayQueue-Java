package com.mycompany.dequeue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

public class DequeueTest {

    @Test
    public void testQueue() {
        // Create a new instance of the Queue.
        Queue<Integer> queue = new Queue<>();

        // Ensure that the queue is initially empty.
        assertTrue(queue.isEmpty());

        // Define the number of elements to be added to the queue.
        int count = 100000;

        // Add elements to the queue and perform assertions.
        for (int i = 0; i < count; i++) {
            queue.pushLast(i);

            // Check if the size of the queue increases after each addition.
            assertEquals(i + 1, queue.size());

            // Check if the first element in the queue is always 0.
            assertEquals(Integer.valueOf(0), queue.first());
        }

        // Remove elements from the queue and perform assertions.
        int current = 0;
        while (!queue.isEmpty()) {
            // Check if the first element in the queue is as expected.
            assertEquals(Integer.valueOf(current), queue.first());

            // Check if the element returned by popFirst() is as expected.
            assertEquals(Integer.valueOf(current), queue.popFirst());

            // Move to the next expected element.
            current++;
        }

        // After all elements are removed, ensure that the queue is empty.
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPushAndPop() {
        // Create a new instance of the Queue.
        Queue<Integer> queue = new Queue<>();

        // Ensure that the queue is initially empty and has a size of 0.
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        // Push elements into the queue.
        queue.pushLast(1);
        queue.pushLast(2);
        queue.pushLast(3);

        // Check that the queue is not empty and has the correct size.
        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());

        // Pop elements from the queue and perform assertions.
        assertEquals(Integer.valueOf(1), queue.popFirst());
        assertEquals(Integer.valueOf(2), queue.popFirst());

        // After popping elements, check that the size is updated.
        assertEquals(1, queue.size());

        // Push more elements into the queue.
        queue.pushLast(4);
        queue.pushLast(5);

        // Pop the remaining elements and verify their values.
        assertEquals(Integer.valueOf(3), queue.popFirst());
        assertEquals(Integer.valueOf(4), queue.popFirst());
        assertEquals(Integer.valueOf(5), queue.popFirst());

        // After all elements are popped, ensure that the queue is empty and has a size of 0.
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    //Test forward traversal
    @Test
    public void testIterator() {
        // Create a new instance of the Queue.
        Queue<String> queue = new Queue<>();

        // Add elements to the queue.
        queue.pushLast("A");
        queue.pushLast("B");
        queue.pushLast("C");

        // Create a StringBuilder to store the result.
        StringBuilder result = new StringBuilder();

        // Obtain an iterator from the queue.
        Iterator<String> iterator = queue.iterator(); // Using the Iterator here

        // Iterate through the elements using the iterator.
        while (iterator.hasNext()) {
            // Append each element to the result StringBuilder.
            result.append(iterator.next());
        }

        // Check if the result matches the expected value.
        assertEquals("ABC", result.toString());
    }

    // Test backward traversal
    @Test
    public void testDescendingIterator() {
        // Create a new instance of the Queue.
        Queue<String> queue = new Queue<>();

        // Add elements to the queue in reverse order.
        queue.pushLast("C");
        queue.pushLast("B");
        queue.pushLast("A");

        // Create a StringBuilder to store the result.
        StringBuilder result = new StringBuilder();

        // Obtain a descending iterator from the queue.
        Iterator<String> descendingIterator = queue.descendingIterator(); // Using the Iterator here

        // Iterate through the elements using the descending iterator.
        while (descendingIterator.hasNext()) {
            // Append each element to the result StringBuilder.
            result.append(descendingIterator.next());
        }

        // Check if the result matches the expected value (original order due to backward traversal).
        assertEquals("ABC", result.toString());
    }


    @Test
    public void testDoubleCapacity() {
        // Create a queue with the default capacity.
        Queue<Integer> queue = new Queue<>();

        // Push elements until the capacity is doubled.
        for (int i = 0; i < queue.getCapacity(); i++) {
            queue.pushLast(i);
        }

        // Check the initial capacity.
        assertEquals(Queue.DEFAULT_CAPACITY, queue.getCapacity());

        // Perform an additional push to trigger doubleCapacity.
        queue.pushLast(queue.getCapacity() + 1);

        // Check if the capacity has been doubled.
        assertEquals(Queue.DEFAULT_CAPACITY * 2, queue.getCapacity());

        // Clear the queue.
        queue.clear();

        // Check if the queue is empty after removing all elements.
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testHalfCapacity() {
        // Create a queue with the default capacity.
        Queue<Integer> queue = new Queue<>();

        // Fill the queue with elements.
        for (int i = 1; i <= Queue.DEFAULT_CAPACITY * 2; i++) {
            queue.pushLast(i);
        }

        // Check if the capacity has doubled.
        assertEquals(Queue.DEFAULT_CAPACITY * 2, queue.getCapacity());

        // Remove elements from the queue.
        for (int i = 1; i <= Queue.DEFAULT_CAPACITY * 2; i++) {
            queue.popFirst();
        }

        // Check if the array is properly halved.
        assertEquals(Queue.DEFAULT_CAPACITY, queue.getCapacity());

        // Fill the queue with elements again.
        for (int i = 1; i <= Queue.DEFAULT_CAPACITY * 2; i++) {
            queue.pushLast(i);
        }

        // Use it to round up to the nearest integer
        int elementsToRemove = (int) Math.ceil(queue.size() * 3.0 / 4.0);

        // Remove elements from the queue again.
        for (int i = 1; i <= elementsToRemove; i++) {
            queue.popFirst();
        }

        // Check if the array is properly halved after removal.
        assertEquals(Queue.DEFAULT_CAPACITY, queue.getCapacity());
    }

    @Test
    public void testQueueOperations() {
        Queue<Integer> queue = new Queue<>();

        // Test pushFirst and pushLast
        queue.pushFirst(1);
        queue.pushLast(2);
        queue.pushFirst(3);
        queue.pushLast(4);
        assertEquals(4, queue.size());

        // Test first and last
        assertEquals(Integer.valueOf(3), queue.first());
        assertEquals(Integer.valueOf(4), queue.last());

        // Test popFirst and popLast
        assertEquals(Integer.valueOf(3), queue.popFirst());
        assertEquals(Integer.valueOf(4), queue.popLast());
        assertEquals(2, queue.size());

        // Test iterator for forward traversal
        StringBuilder forwardTraversal = new StringBuilder();
        Iterator<Integer> iterator = queue.iterator();
        iterator.forEachRemaining(element -> forwardTraversal.append(element).append(" "));
        assertEquals("1 2", forwardTraversal.toString().trim());

        // Test iterator for backward traversal
        StringBuilder backwardTraversal = new StringBuilder();
        Iterator<Integer> descendingIterator = queue.descendingIterator();
        descendingIterator.forEachRemaining(element -> backwardTraversal.append(element).append(" "));
        assertEquals("2 1", backwardTraversal.toString().trim());

        // Test clear
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testNoSuchElementException() {
        // Create a new instance of the Queue.
        Queue<Integer> queue = new Queue<>();

        try {
            // Attempt to perform an operation that should throw a NoSuchElementException.
            Integer element = queue.popFirst();

            // If the above line does not throw an exception, fail the test.
            assertTrue("Expected NoSuchElementException, but no exception was thrown", false);
        } catch (NoSuchElementException e) {
            // The exception was thrown as expected.
            assertTrue(true);
        }
    }
}    
