
package euler90; 

import java.util.*;

public class Euler90 {
    private int[] set;
    private List<int[]> arrs;
    private int solutions;

    public static void main(String[] args) {
        Euler90 problem = new Euler90();
        problem.go();
    }
    
    private Euler90() {
        set = new int[6];
        arrs = new ArrayList<int[]>();
        solutions = 0;
    }
    
    private void go() {
        buildSet(set, 6);
        System.out.println("**size of the set: " + arrs.size());
        for(int i = 0; i < arrs.size(); i++)
            for(int k = i + 1; k < arrs.size(); k++){
                if(isSolution(arrs.get(i), arrs.get(k))) {
                    solutions++;
                    printSet(arrs.get(i));
                    printSet(arrs.get(k));
                }}
        System.out.println("   Number of solutions: " + solutions);
    }
    
    private void buildSet(int[] set, int n) {
        if(n > 0) {
            int i, x0;
            i = 6 - n;
            x0 = (i == 0) ? 0 : set[i - 1] + 1;
            for(int x = x0; x <= 10 - n; x++) {
                set[i] = x;
                buildSet(set, n - 1);
            }              
        }
        else {
            printSet(set);
            arrs.add(set.clone());
        }
    }
    
    private void printSet(int[] set) {
        for(int i = 0; i < 6; i++)
            System.out.print(set[i] + " ");
        System.out.println();
    }
    
    private boolean isSolution(int[] set1, int[] set2) {
        for(Square sqr: Square.values())
            if(contains(set1, sqr.firstD()) && contains(set2, sqr.secondD()))
                continue;
            else if(contains(set1, sqr.secondD()) && contains(set2, sqr.firstD()))
                continue;
            else
                return false;
       return true;
    }
    
    private boolean contains(int[] set, int d) {
        if(d == 6 || d == 9) {
            for(int i = 0; i < 6; i++)
                if(set[i] == 6 || set[i] == 9)
                    return true;
        }
        else {
            for(int j = 0; j < 6; j++)
                if(set[j] == d)
                    return true;
        }
        return false;
    }
} 

enum Square {ONE(1), TWO(4), THREE(9), 
             FOUR(16), FIVE(25), SIX(36), 
             SEVEN(49), EIGTH(64), NINE(81);
             
    public final int square;
    Square(int square) {
        this.square = square;
    }
    
    public int firstD() {
        return this.square / 10;
    }
    
    public int secondD() {
        return this.square % 10;
    }
}