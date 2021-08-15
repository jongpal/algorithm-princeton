package io.jongpal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing Deque ADT")
class DequeTest {
	
	/*
	 * Testing Strategy
	 * 
	 * Partition : by size 0, 1, n 
	 * 			  
	 * 
	 * 1) No element
	 *   - check if size is 0
	 *   - check if removeFirst(), removeLast() throws Exception
	 *   - check if counting iterate number is 0
	 * 2) one element is added
	 *   - addFirst() -> check size -> check Iterator -> removeFirst() -> check size, Iterator
	 *   - same for addLast()
	 * 3) adding several elements 
	 *   - and removing those, check the order of it
	 */
	
	@DisplayName("if there's no element")
	@Test
	public void testEmptyDeque() {
		Deque<Integer> d = new Deque<Integer>();
		Iterator<Integer> it = d.iterator();
		assertAll(
				() -> assertTrue(d.size() == 0),
				() -> assertThrows(NoSuchElementException.class, () -> d.removeFirst()),
				() -> assertThrows(NoSuchElementException.class, () -> d.removeLast()),
				() -> assertFalse(it.hasNext())
				);

	}
	
	
	@DisplayName("if there's one element added")
	@Test
	public void testOneElementDeque() {
		Deque<Integer> d = new Deque<Integer>();
		Iterator<Integer> it;
		d.addFirst(2);
		assertTrue(d.size() == 1);
		it = d.iterator();
		assertTrue(it.hasNext());
		assertTrue(it.next() == 2);
		d.removeFirst();
		it = d.iterator();
		assertTrue(d.size() == 0);
		assertFalse(it.hasNext());
		
		d.addLast(3);
		it = d.iterator();
		assertTrue(d.size() == 1);
		assertTrue(it.hasNext());
		assertTrue(it.next() == 3);
		d.removeLast();
		it = d.iterator();
		assertTrue(d.size() == 0);
		assertFalse(it.hasNext());
	}
		

	/*
	 *  add elements 3, 4 by addFirst(), 7, 1 by addLast()
	 *  so the order from first is 4, 3, 7, 1
	 *  
	 */
	
	@Test
	@DisplayName("if there are several elements added")
	public void testSeveralElementsDeque() {
		Deque<Integer> d = new Deque<Integer>();
		Iterator<Integer> it;
		int []order = new int[]{4, 3, 7, 1};
		d.addFirst(3);
		d.addFirst(4);
		d.addLast(7);
		d.addLast(1);
		
		it = d.iterator();
		System.out.println(d.size());
		int cur = 0;
		while(it.hasNext()) {
			assertEquals(order[cur++], it.next());
		}
		assertTrue(cur == 4);
	}
			


}
