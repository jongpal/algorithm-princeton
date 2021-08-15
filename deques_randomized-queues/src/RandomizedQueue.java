package io.jongpal;


import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
	/**
	 * this RandomizedQueue implements resizable array data structure
	 * client would think this array's size is {@code currSize}
	 * 
	 * top        refers to the top index
	 * currSize   refers to current elements inside of entire array,
	 * tableSize  refers to table size, not the number of elements inside of it
	 * MIN_TABLE_SIZE  is initial array size
	 * 
	 * tableSize is not
	 */
    private Item[] randq;
    private int top;
    private int currSize;
    private int tableSize;
    private final int MIN_TABLE_SIZE = 2;

    
    public RandomizedQueue () {
        randq = (Item[]) new Object[MIN_TABLE_SIZE];
        top = -1;
        currSize = 0;
        tableSize = MIN_TABLE_SIZE;
    }
    
    public boolean isEmpty () {
        return currSize == 0;    
    }
      
    public int size(){
        return currSize;
    }
      
    /**
     * resizing array
     * this resize function will resize, and setup both top & tableSize
     * 
     * @param newSize resize to this size
     */
    private void resize (int newSize) {
        Item[] newRandq = (Item[]) new Object[newSize];
        int j = 0;
        for(int i = 0; i < tableSize; i++) {
            if(randq[i] == null) continue;
            newRandq[j++] = randq[i];
        }
        randq = null;
        top = j - 1;
        tableSize = newSize;
        randq = newRandq;
    }
    
    /**
     * enqueue item
     * this function will resize the table if necessary
     * 
     * @param item item to enqueue
     * @throws IllegalArgumentException if item is null
     */
    public void enqueue (Item item){
        if(item == null) throw new IllegalArgumentException("wrong argument");
        
        // if table is full, double the size of the table
        if (currSize == tableSize || top >= (tableSize - 1)) {
        	resize(2 * tableSize);
        	randq[++top] = item;
        	currSize++;
        }
        else {
        	randq[++top] = item;
        	currSize++;
        }
        
    }
    private void exch(Item []item, int i, int j) {
    	Item temp = item[i];
    	item[i] = item[j];
    	item[j] = temp;
    }
    /**
     * dequeue random element from randq
     * 
     * @return Item that was selected
     * @throws NoSuchElementException if the queue is empty
     */
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException("no more entry left");
        // generate random number 0 - size    
        int randInd = StdRandom.uniform(top + 1);
        
        while (randq[randInd] == null) {
        	exch(randq, randInd, top);
        	while (randq[top] == null) {
        		top--;
        		if (top < 0) break;
        	}
        	randInd = StdRandom.uniform(top + 1);
        }

        Item item = randq[randInd];
        randq[randInd] = null;
        if (randInd == top && top >= 0) {
        	while (randq[top] == null) {
        		top--;
        		if (top < 0) break;
        	}
        }
        currSize--;
        
        // downsizing rule : if currSize <= tableSize / 4
        // rep invariant : initial tableSize should always be MIN_TABLE_SIZE
        if (currSize <= tableSize / 4 && tableSize > MIN_TABLE_SIZE) {
        	resize(tableSize / 2);
        }
        return item;
    }
      
    /**
     * select random element from the queue and return it
     * 
     * @return Item that is uniformly selected
     * @throws NoSuchElementException if queue is empty
     */
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException("no more entry left");
        int randInd = StdRandom.uniform(top+1);
        
        while (randq[randInd] == null) {
        	exch(randq, randInd, top);
        	top--;
        	if (top < 0) break;
        	while (randq[top] == null) {
        		top--;
        		if (top < 0) break;
        	}
        }
        return randq[randInd];
    }
      
    /**
     * 
     * Random Array Iterator
     * @author jongpal
     * using knuth shuffle, randomly iterate the array
     *
     */
    private class RandArrayIterator implements Iterator<Item> {
    	/*
    	 * sampleSize  total number of sample
    	 * sampleArray array that stores shuffled index
    	 * currRandInd tracking current index
    	 */
        private final int sampleSize = top + 1;
        private final int[] sampleArray;
        private int currRandInd;
        
        public RandArrayIterator() {
            sampleArray = new int[sampleSize];
            for (int i = 0; i < sampleSize; i++) {
                sampleArray[i] = i;
            }
            for (int j = 0; j < sampleSize; j++) {
                int r = j + (int) (Math.random() * (sampleSize - j));
                int t = sampleArray[r];
                sampleArray[r] = sampleArray[j];
                sampleArray[j] = t;
            }
            currRandInd = 0;
        }
        
        public boolean hasNext() {
          if (currRandInd > sampleSize - 1) return false;
          while (randq[sampleArray[currRandInd]] == null) {
        	  currRandInd++;
        	  if (currRandInd > sampleSize - 1) return false;  
          }
          return currRandInd <= sampleSize - 1;   
        }
        public void remove() {
          throw new UnsupportedOperationException("operation currently not supported");
        }
        public Item next() {
          if (currRandInd > sampleSize - 1) throw new NoSuchElementException("no more items left");

          return randq[sampleArray[currRandInd++]];
        }   
        
    }
    public Iterator<Item> iterator() {
        return new RandArrayIterator();
    }
      
    public static void main(String[] args) {


        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(10);
        rq.enqueue(12);
        rq.enqueue(15);
        rq.enqueue(18);
        
//        rq.dequeue();
        
        
        Iterator<Integer> it = rq.iterator();
 
        while (it.hasNext()) {
           int i = it.next();
           System.out.println(i);
            
        }
        
        System.out.print("sample : ");
        System.out.println(rq.sample());
        
    	
    	/*
    	 * Resizing Testing Strategy
    	 * 
    	 * - this RandomQueue's table size starts at 2,
    	 *   so using this fact we are going to enqueue 3 elements and tableSize should be 4.
    	 * - for downSizing, the rule is if current elements' size is not bigger than a quarter
    	 *   of tableSize, then downsize it by 2. so we are going to dequeue 2 elements(which leads to
    	 *   total elements of 1) and see if this method halved the size.
    	 */
        
		rq = new RandomizedQueue<Integer>();
		System.out.printf("--Doubling the table size testing-- \nthe initial table size should be %d\n test result : %d\n", 2, rq.tableSize);
		rq.enqueue(3);
		rq.enqueue(4);
		rq.enqueue(5);
		System.out.printf("after enqueueing 3 elements, table size should be %d * 2\n test result : %d\n", 2, rq.tableSize);
		
		rq.dequeue();
		rq.dequeue();
		System.out.printf("after dequeueing 2 elements, table size should be %d\n test result : %d\n", 2, rq.tableSize);
    }
}