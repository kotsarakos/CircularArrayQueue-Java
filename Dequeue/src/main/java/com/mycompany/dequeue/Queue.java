/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dequeue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Authors:
 * - Konstantinos Kotsaras (it2022050)
 * - Ioulianos Polyzos (it2022091)
 * Team: 45
 */
public class Queue<E> implements DeQueue<E> , Iterable<E>{

    //Variables
    protected  static final int DEFAULT_CAPACITY = 4;
    private E[] array;
    private int f, r, size;
    private volatile int modCount;
    private  int expectedModCount;

    /**
     * Constructs a new Queue with default capacity.
     * Initializes the array, front (f), rear (r), and size to their default values.
     */
    public Queue() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        f = 0;
        r = 0;
        size = 0;
    }


    protected  int getCapacity() {
        return array.length;
    }

    /**
     * Adds the specified element to the front of the queue.
     * If the dequeue is at full capacity, it doubles its size before adding the element.
     *
     * @param elem The element to be added to the front of the queue.
     */
    @Override
    public void pushFirst(E elem) {
        // Check for space
        if (size == getCapacity()) {
            doubleCapacity(); // Double the size of the array if needed
        }

        // Calculate the new position of the front
        f = (f - 1 + getCapacity()) % getCapacity();

        // Add the element to the front of the queue
        array[f] = elem;

        // Increase the size of the queue
        size++;

        // Increase the modification count to track structural modifications
        modCount++;
    }

    /**
     * Adds the specified element to the end of the queue.
     * If the dequeue is at full capacity, it doubles its size before adding the element.
     *
     * @param elem The element to be added to the end of the queue.
     */
    @Override
    public void pushLast(E elem) {
        // Check for space
        if (size == getCapacity()) {
            doubleCapacity(); // If needed, double the array size
        }

        // Add the element to the end of the queue
        array[r] = elem;

        // Calculate the new position of rear
        r = (r + 1) % getCapacity();

        // Increase the size of the queue
        size++;

        // Increase the modification count to track structural modifications
        modCount++;

    }

    /**
     * Removes and returns the element at the front of the queue.
     * If the queue becomes less than one-fourth full after removal, it shrinks its capacity.
     *
     * @return The element at the front of the queue.
     * @throws NoSuchElementException If the queue is empty.
     */
    @Override
    public E popFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E elem = array[f];

        // Set the removed element to null
        array[f] = null;

        f = (f + 1) % getCapacity();
        size--;
        modCount++;

        // Check if shrinking is needed
        if (4 * size <= array.length) {
            halfCapacity();
        }

        return elem;
    }

    /**
     * Removes and returns the element at the end of the queue.
     * If the queue becomes less than one-fourth full after removal, it shrinks its capacity.
     *
     * @return The element at the end of the queue.
     * @throws NoSuchElementException If the queue is empty.
     */
    @Override
    public E popLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        if (r == 0) {
            r = getCapacity() - 1;
        } else {
            r--;
        }

        E elem = array[r];
        // Set the removed element to null
        array[r] = null;

        size--;
        modCount++;

        // Check if shrinking is needed
        if (4 * size <= array.length) {
            halfCapacity();
        }

        return elem;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return The element at the front of the queue.
     * @throws NoSuchElementException If the queue is empty.
     */
    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[f];
    }

    /**
     * Returns the element at the end of the queue without removing it.
     *
     * @return The element at the end of the queue.
     * @throws NoSuchElementException If the queue is empty.
     */
    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        // Calculate the position of the last element
        int lastRear = (r - 1 + getCapacity()) % getCapacity();

        // Return the last element
        return array[lastRear];
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return {@code true} if the queue contains no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return The number of elements in the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the queue and resets its internal state.
     * After invoking this method, the queue will have a capacity equal to the default capacity.
     * The front and rear indices are reset to the starting position, and the size becomes 0.
     */
    @Override
    public void clear() {
        array = (E[]) new Object[DEFAULT_CAPACITY]; // Reset the array to the default capacity
        f = 0; // Reset the front index
        r = 0; // Reset the rear index
        size = 0; // Reset the size of the deque
    }
    /**
     * IteratorImpl is the iterator for forward traversal of the circular array.
     * It keeps track of the current index (cur) and checks for concurrent modifications.
     */

    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<E> {
        private int cur;
    // Constructor initializes the current index to f and checks for concurrent modifications.
          public IteratorImpl(){
            cur=f;
            expectedModCount = modCount;
    }
    /**
     * Method to check structural modifications.
     * @throws ConcurrentModificationException()
     * 
     */   
    private void checkForComodification() {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
    /**
     * Check for structural modifications.
     * @return true if there is an element after current element in queue 
     */    
        @Override
        public boolean hasNext() {
            checkForComodification();
            return cur != r;
        }
    /**
     * Check for structural modifications.
     * Check if there is an element after current element with hasNext() method
     * Retrieve the element at the current index in the circular array and store it in the variable elem type E
     * Increment cur by 1 to move to the next element
     * modulo getCapacity():Ensure that the incremented current index is within the bounds of the array. If the current index is at the end of the array, it loops back to the start 
     * @return The next element in the queue
     * @throws NoSuchElementException() if there is no next element in queue
     */      
        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E elem = array[cur];
            cur = (cur + 1) % getCapacity();
            return elem;
        }
    }
    /**
     * 
     * DescendingIteratorImpl for backward traversal of the array
     * DescendingIteratorImpl keeps track of the current index (cur) and checks for concurrent modifications.
     */

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIteratorImpl();
    }

    private class DescendingIteratorImpl implements Iterator<E> {
        private int cur;
    // Constructor initializes the current index to [(r - 1 + getCapacity()) % getCapacity()] and checks for concurrent modifications.
        public DescendingIteratorImpl() {
            cur = (r - 1 + getCapacity()) % getCapacity();
            expectedModCount = modCount;
        }
        private void checkForComodification() {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
    /**
     * Check for structural modifications.
     * f-1 to go to previous element
     * @return true if there is an element before current element in queue 
     */  
        @Override
        public boolean hasNext() {
            checkForComodification();
            return cur != (f - 1 + getCapacity()) % getCapacity();
        }
    /**
     * Check for structural modifications.
     * Check if there is an element after current element with hasNext() method
     * Retrieve the element at the current index in the circular array and store it in the variable elem type E
     * decrease cur by 1 to move to the previous element
     * modulo getCapacity():Ensure that the decreased current index is within the bounds of the array. If the current index is at the start of the array, it loops back to the end
     * @return The previous element in the queue
     * @throws NoSuchElementException() if there is no previous element in queue
     */          

        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E elem = array[cur];
            cur = (cur - 1 + getCapacity()) % getCapacity();
            return elem;
        }
    }

    private void doubleCapacity() {
        // Doubles the capacity of the dequeue by creating a new array with double the current capacity
        int newCapacity = 2 * getCapacity();
        E[] newArray = (E[]) new Object[newCapacity];

        // Copy elements to the new array, maintaining the correct order
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(f + i) % getCapacity()];
        }

        // Update front and rear indices
        f = 0;
        r = size;

        // Update array
        array = newArray;
    }

    private void halfCapacity() {
        // Check if shrinking is needed and if the resulting capacity is not below the default capacity
        if (array.length <= DEFAULT_CAPACITY) {
            return;
        }

        // Calculate the new capacity as half of the current capacity
        int newCapacity = getCapacity() / 2;

        // Create a new array with the updated capacity
        E[] newArray = (E[]) new Object[newCapacity];

        // Copy elements to the new array, maintaining the correct order
        for (int i = 0; i < Math.min(size, newCapacity); i++) {
            newArray[i] = array[(f + i) % getCapacity()];
        }

        // Update the size to the minimum of the current size and the new capacity
        size = Math.min(size, newCapacity);

        // Update front and rear indices
        f = 0;
        r = size;

        // Update the array reference to the new array
        array = newArray;
    }

    /**
     * Prints the elements of the queue, enclosed in square brackets and separated by commas, to the standard output.
     * If the queue is empty, an empty line is printed.
     */
    public void printElements() {
        for (E element : this.array) {
            System.out.print("[" + element + "]");
        }
        System.out.println();
    }

}  