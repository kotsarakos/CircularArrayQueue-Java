/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dequeue;

import java.util.Iterator;


/**
 * Authors:
 * - Konstantinos Kotsaras (it2022050)
 * - Ioulianos Polyzos (it2022091)
 * Team: 45
 */
public class App {

   public static void main(String[] args) {
     Queue<Integer> deQueue = new Queue<>();

     // Push elements
     System.out.println("Push First: 20");
     deQueue.pushFirst(20);
     deQueue.printElements();

     performDelay();

     System.out.println("Push First: 10");
     deQueue.pushFirst(10);
     deQueue.printElements();

     performDelay();

     System.out.println("Push Last: 30");
     deQueue.pushLast(30);
     deQueue.printElements();

     performDelay();

     System.out.println("Push Last: 40");
     deQueue.pushLast(40);
     deQueue.printElements();

     performDelay();

     System.out.println("Push Last: 50");
     deQueue.pushLast(50);
     deQueue.printElements();

     performDelay();

     // Print elements using iterator
     System.out.println("Printing elements using iterator:");
     Iterator<Integer> iterator = deQueue.iterator();
     while (iterator.hasNext()) {
       System.out.print(iterator.next() + " ");
     }
     System.out.println();

     performDelay();

     // Pop elements
     int first = deQueue.popFirst();
     System.out.println("Pop First Element: " + first);
     deQueue.printElements();

     performDelay();

     int last = deQueue.popLast();
     System.out.println("Pop Last Element : " + last);
     deQueue.printElements();

     performDelay();

     // Print remaining elements using descending iterator
     System.out.println("Printing remaining elements using descending iterator:");
     Iterator<Integer> descendingIterator = deQueue.descendingIterator();
     while (descendingIterator.hasNext()) {
       System.out.print(descendingIterator.next() + " ");
     }
     System.out.println();

     performDelay();

     // Check first and last elements
     System.out.println("First element: " + deQueue.first());
     System.out.println("Last element: " + deQueue.last());

     performDelay();

     // Clear the queue
     System.out.println("Clear Queue");
     deQueue.clear();
     deQueue.printElements();

     performDelay();

     // Check if the queue is empty
     System.out.println("Is The Queue Empty? " + deQueue.isEmpty());
   }

    // This method is used in order to delay the output for 1second
    // By doing this we perform the results step by step
    private static void  performDelay() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

}
