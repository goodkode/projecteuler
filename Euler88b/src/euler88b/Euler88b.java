package euler88b;

import java.util.*;

public class Euler88b {
    private static int LIMIT = 12000;
    private int result = 0;
    private int vars;
    private Map<Integer, Integer> theKs;

    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
    	new Euler88b().go();
    	System.out.println("\n...execution time " + (System.currentTimeMillis() - startTime)/1000.0);
    }
    
    private void go() {
    	theKs = new HashMap<Integer, Integer>();
        int maxVar = (int) (Math.log(2 * LIMIT) / Math.log(2));
        for(vars = 2; vars <= maxVar; vars++) {
            arrayBuilder(vars, new int[vars]);
        }
        System.out.println(theKs);
        Set<Integer> theNs = new HashSet<Integer>();
        theNs.addAll(theKs.values());
        System.out.println("\n" + theNs);
        for(int n: theNs)
            result += n;
        System.out.println("\nFor k = " + LIMIT + ":\n   The result is: " + result);
    }
    
    private void arrayBuilder(int v, int[] x) {
        int a, index = this.vars - v;
    	if(index == this.vars) {
            //printArray("", x);
            checkCombo(this.vars, x);
        }
    	else {
            if(index == 0)
                a = 2;
            else
                a = x[index - 1];
            while(arrayProd(vars, x) < LIMIT * 2) {
                x[index] = a;
                arrayBuilder((v - 1), x);
                for(int i = index + 1; i < this.vars; i++)
                    x[i] = a;
                a++;
            }
        }
    }
    
    private int arrayProd(int vars, int[]x) {
        int prod = 1;
        for(int i = 0; i < vars; i++)
            prod *= x[i];
        return prod;
    }
    
    private int arraySum(int vars, int[] x) {
        int sum = 0;
        for(int i = 0; i < vars;i++)
            sum += x[i];
        return sum;
    }
    
    private void checkCombo(int vars, int[] x) {
        int diff, k;
        int N = arrayProd(vars, x);
        diff = N - arraySum(vars, x);
        if(diff < 0)
            return;
        else {
            k = diff + vars;
            if(k <= LIMIT && (theKs.get(k) == null || theKs.get(k) > N))
                theKs.put(k,N);
        }
    }
    
    private void printArray(String str, int[] x) {
        System.out.print(str);
        for(int i = 0; i < vars; i++)
           System.out.print(x[i] + " ");
        System.out.println();
     }
} 
