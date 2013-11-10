
package euler92;

import java.util.*;

public class Euler92 {
    private final int MAXKEY = 567;
    private int[] key;
    private static int ends89;

    public static void main(String[] args) {
        Euler92 problem = new Euler92();
        problem.checkNums(10000000);
        System.out.println("\n\nThe count of numbers arriving to 89 is: " + ends89);
    }
    
    public Euler92() {
        this.key = new int[MAXKEY + 1];
        for(int k: key) {
            k = 0; }
        this.ends89 = 0;
    }
    
    private void checkNums(int max) {
        int n;
        for(int i = 1; i <= max; i++) {
            n = i;
            //System.out.print("\n" + i);
            do {
                n = nextFactor(n);
                //System.out.print(" " + n);
                if(n > MAXKEY) {
                    System.out.println("ERROR"); }
            }
            while(n != 1 && n != 89);
            if(n == 89) {
                Euler92.ends89++; }
        }
    }
    
    private int nextFactor(int i) {
        int d, value = 0;
        do {
            d = i % 10;
            value += d * d;
            i /= 10;
        }
        while(i != 0);
        return value;
    }
}
