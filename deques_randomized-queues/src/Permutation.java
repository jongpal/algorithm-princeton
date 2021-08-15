package io.jongpal;

// import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    private final RandomizedQueue<String> rq;
    private Permutation() {
        rq = new RandomizedQueue<String>();
    }
    
    private void push(String s) {
        rq.enqueue(s);    
    }
    private String randPop() {
        return rq.dequeue();
    }
    
    
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        Permutation perm = new Permutation();
        
        while (!StdIn.isEmpty()) {
            String val = StdIn.readString();
            perm.push(val);
        }
        
        for (int i = 0; i < num; i++) {
            System.out.println(perm.randPop());
        }
        
    }
}