package io.jongpal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomizedQueueTest {

	/*
	 * Testing Strategy
	 * 
	 * - testing uniformness when some of elements are missing (cause they are dequeued)
	 * - all elements should be uniform to some point. (within 10%)
	 */
	@Test
	@DisplayName("Randomness Test")
	public void testDequeuedQueueRandom() {
		
		int trials = 1000;
		
		int []occurenceCounts = new int[9];
		
		for (int i = 0; i < trials; i++) {
			RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
			rq.enqueue(0);
			rq.enqueue(1);
			rq.enqueue(2);
			rq.enqueue(3);
			rq.enqueue(4);
			rq.enqueue(5);
			rq.enqueue(6);
			rq.enqueue(7);
			rq.enqueue(8);
			
			rq.dequeue();
			rq.dequeue();
			rq.dequeue();
			
			Iterator<Integer> it = rq.iterator();
			// see the leftover elements;
			while (it.hasNext()) {
				int c = it.next();
				switch(c) {
					case 0:
						occurenceCounts[0]++;
						break;
					case 1:
						occurenceCounts[1]++;
						break;
					case 2:
						occurenceCounts[2]++;
						break;
					case 3:
						occurenceCounts[3]++;
						break;
					case 4:
						occurenceCounts[4]++;
						break;
					case 5:
						occurenceCounts[5]++;
						break;
					case 6:
						occurenceCounts[6]++;
						break;
					case 7:
						occurenceCounts[7]++;
						break;
					case 8:
						occurenceCounts[8]++;
						break;
				}
			}
		
		}
		int sum = 0;
		for (int j = 0; j < 9; j++) {
			sum += occurenceCounts[j];
		}
		double mean = sum / 9.0;
		for (int j = 0; j < 9; j++) {
			assertTrue((Math.abs(occurenceCounts[j] - mean) / occurenceCounts[j]) < 0.1);
		}
		
	}

}
