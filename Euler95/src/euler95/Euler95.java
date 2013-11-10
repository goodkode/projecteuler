package euler95;

import java.util.*;

public class Euler95 {
    private Set<Integer> inChain = new HashSet<Integer>();
    private static final int LIMIT = 12500;
    
    public static void main(String[] args) {
        Euler95 problem = new Euler95();
        problem.solve();
    }
    
    private void solve() {
        Set<Integer> newChain;
        int next, length;
        System.out.println("Solving...");
        for (int n = 10; n <= LIMIT; n++) {
            if (inChain.contains(n))
                continue;
            newChain = new LinkedHashSet<Integer>();
            next = generateNext(n);
            newChain.add(n);
            length = 1;
            while (newChain.contains(next) == false) {
                newChain.add(next);
                length++;
                next = generateNext(next);
            }
            printChain(newChain.iterator());
        }
    }
    
    private int generateNext(int n) {
        int next = 1;
        List<Integer> factors = new ArrayList<Integer>();
        for (int d = 2; d * d<= n; d++) {
            if (n % d == 0) {
                factors.add(d);
                factors.add(n / d);
        } }
        for(int i: factors)
            next += i;
        return next;
    }
    
    private void printChain(Iterator<Integer> chain) {
        int next = 0;
        System.out.print(chain.next());
        while (chain.hasNext()) {
            next = chain.next();
            System.out.print(" -> " + next);
        }
        System.out.println(" (-> " + generateNext(next) + ")");
    }
}
