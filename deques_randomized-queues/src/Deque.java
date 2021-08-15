package io.jongpal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked List representation of abstract data type Deque
 * 
 * In Deque, user can add item at the first place of the list / last place of the list
 * also, user can remove item from either way.
 * @author Jongpal
 */


public class Deque<Item> implements Iterable<Item> {
    /**
     *  - Rep Invariant
     *  
     *  Below is the state of the empty list.
     *  {@code first} should always be at the first position of the list, its {@code prev} is always null
     *  {@code last} should always be at the last position of the list, its {@code next} is always null
     *         -----    -----
     * null <-|first|->| last|-> null
     *        |     |<-|     | 
     *         -----    -----
     *         
     *  - Safety From Rep Exposure
     *  
     *  {@code first}, and {@code last} are called final and private, and this ADT has no method returning these.
     *  also, inside of code there's no method that is changing invariant.
     *         
     */
		
	/**
	 *  - Abstract Function
	 *  
	 *  {@code first} is referencing the first place of the list
	 *	{@code last} for last place
	 */

    private final Node first;
    private final Node last;
    private int size;
    
    /**
     *  representing each entry of list
     *  {@code next} will be pointing to the next entry, {@code prec} to previous entry.
     */
     
    private class Node {
      Item item;
      Node next;
      Node prev;
    }
    
    // Initialize the Deque as illustrated above
    public Deque() {
      first = new Node();
      last = new Node();
      first.prev = null;
      first.next = last;
      last.prev = first;
      last.next = null;
      size = 0;
    }
    
    /**
     * Check if a list is empty
     * @return true if first.next is empty (pointing to last)
     */
    public boolean isEmpty() {
      return first.next == last;   
    }
        
    public int size() {
      return size;
    }
    
    /**
     * add item to the first position of the list(technically, right after {@code first}
     * 
     * @param item any data type you want to put in
     * @throws IllegalArgumentException if item is null
     */
    public void addFirst(Item item) {
      if (item == null) throw new IllegalArgumentException("got invalid item");
      Node oldFirst = first.next;
      Node newNode = new Node();
      
      newNode.item = item;
      
      newNode.next = oldFirst;
      oldFirst.prev = newNode;
      
      newNode.prev = first;
      first.next = newNode;
      item = null; // loitering
      size++;
    }
    
    /**
     * add item to the last position of the list(technically, right before {@code lastst}
     * 
     * @param item any data type you want to put in
     * @throws IllegalArgumentException if item is null
     */       
    public void addLast(Item item) {
      if (item == null) throw new IllegalArgumentException("got invalid item");
      Node oldLast = last.prev;
      Node newNode = new Node();
      newNode.item = item;
      
      last.prev = newNode;
      newNode.next = last;
      
      newNode.prev = oldLast;      
      oldLast.next = newNode;
      item = null; // loitering
      size++;
    }

    /**
     * remove item from the first position of the list(technically, right after {@code first}
     * 
     * @return Item that has been removed from a list
     * @throws NoSuchElementException if a list is empty
     */
    public Item removeFirst() {
      if (isEmpty()) throw new NoSuchElementException("no more element left");
      Item firstItem = first.next.item;
      
      Node curr = first.next;
      
      curr.prev.next = curr.next;
      curr.next.prev = curr.prev;
      
      curr = null;
      size--;
      return firstItem;
    }
    /**
     * remove item from the last position of the list(technically, right before {@code last}
     * 
     * @return Item that has been removed from a list
     * @throws NoSuchElementException if a list is empty
     */
    
    public Item removeLast() {
      if (isEmpty()) throw new NoSuchElementException("no more element left");
      Item lastItem = last.prev.item;
      
      Node curr = last.prev;
    
      curr.prev.next = curr.next;
      curr.next.prev = curr.prev;
      
      curr = null;
      size--;
      return lastItem;
    }
        
    /**
     * @return Iterator<Item> that iterates from {@code first.next} to {@code last.prev}
     */
    public Iterator<Item> iterator() {
      return new ListIterator();
    }
 
    private class ListIterator implements Iterator<Item> {
        
        private Node curr = first.next;
        
        public boolean hasNext() {
          return curr != last;   
        }
        public void remove() {
          throw new UnsupportedOperationException("operation currently not supported");
        }
        public Item next() {
          if (curr == last) throw new NoSuchElementException("no more items left");
          Item currItem = curr.item;
          curr = curr.next;
          return currItem;
        }
    }
        
    /**
     * testing methods
     * More on DequeTest.java
     * @param args not used in here
     */
    public static void main(String[] args) {
         Deque<Integer> deq = new Deque<Integer>();
         deq.addFirst(10);

         deq.addFirst(8);

         deq.addLast(12);
         Iterator<Integer> it = deq.iterator();
         while (it.hasNext()) {
           int i = it.next();
           System.out.println(i);
         }
         
         deq.removeLast();
         deq.removeFirst();

         
         deq.addFirst(13);

         deq.addFirst(15);
         
         System.out.print("Size of queue : ");
         System.out.println(deq.size());

         
         Iterator<Integer> it2 = deq.iterator();
         while (it2.hasNext()) {
           int i = it2.next();
           System.out.println(i);
         }
         
    }
    
}