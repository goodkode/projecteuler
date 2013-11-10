
package euler93;
import java.util.*;

public class Euler93 {
    private static List<int[]> sets;
    private static SortedSet<Integer> nums;
    private static Operation[] ops;
    private static int maxLength, longest;

    public static void main(String[] args) {
        Euler93 problem = new Euler93();
        problem.iterateSets();
    }
    
    public Euler93() {
        sets = new ArrayList<>();
        Operation add, sub, mult, div;
        int maxLength = 0;
        int a, b, c, d;
        for(a = 1; a < 10; a++) {
            for(b = a + 1; b < 10; b++) {
                for(c = b + 1; c < 10; c++) {
                    for(d = c + 1; d < 10; d++) {
                        sets.add(new int[] {a, b, c, d});
        }}}}
        add = new Operation() {
            @Override public int op(int a, int b) { return (a + b); }
            @Override public String type() { return " + "; }
        };
        sub = new Operation() {
            @Override public int op(int a, int b) { return (a - b); }
            @Override public String type() { return " - "; }
        };
        mult = new Operation() {
            @Override public int op(int a, int b) { return (a * b); }
            @Override public String type() { return " * "; }
        };
        div = new Operation() {
            @Override public int op(int a, int b) { return (a / b); }
            @Override public String type() { return " / "; }
        };
        ops = new Operation[] {add, sub, mult, div};
    }
    
    private void iterateSets() {
        for(int[] set: sets){
            nums = new TreeSet<>();
            System.out.print("** new set: ");
            printSet(set);
            checkSet(set, nums);
            checkLongest(nums, set, maxLength, longest);
        }
        System.out.println("The max length is: " + maxLength + ", the 'abcd' are: " + longest);
    }

    private void checkSet(int[] set, Set nums) {
        // create variation of order
        int[] varSet;
        int[] order = new int[4];
        for(order[0] = 0; order[0] < 4; order[0]++) {
            for(order[1] = 0; order[1] < 4; order[1]++) {
                if(order[1] == order[0]) {
                    continue; }
                for(order[2] = 0; order[2] < 4; order[2]++) {
                    if(order[2] == order[0] || order[2] == order[1]) {
                    continue; }
                    for(order[3] = 0; order[3] < 4; order[3]++) {
                        if(order[3] == order[0] || order[3] == order[1] || order[3] == order[2]) {
                        continue; }
                        varSet = new int[] {set[order[0]], set[order[1]], set[order[2]], set[order[3]]};
                        //printSet(varSet);
                        checkVarSet(varSet, nums);
        }}}}
    }
    
    private void checkVarSet(int[] varSet, Set nums) {
        int x;
        for(int firstOp = 0; firstOp < 4; firstOp++) {
            for(int secondOp = 0; secondOp < 4; secondOp++) {
                for(int thirdOp = 0; thirdOp < 4; thirdOp++) {
                    x = ops[firstOp].op(varSet[0], varSet[1]);
                    x = ops[secondOp].op(x, varSet[2]);
                    x = ops[thirdOp].op(x, varSet[3]);
                    /*System.out.println("  " + varSet[0] + ops[firstOp].type() + varSet[1] 
                            + ops[secondOp].type()+ varSet[2] + ops[thirdOp].type() 
                            + varSet[3] + " = " + x); */
                    nums.add(x);
        }}}
    }
    
    private void checkLongest(SortedSet nums, int[] set, int maxLength, int longest) {
        int length, theNum;
        if(nums.contains(1)) {
            length = 1;
            nums = nums.tailSet(1);
            System.out.println("set size: " + nums.size() + "\n" + nums);
            Iterator itr = nums.iterator();
            itr.next();
            while(itr.hasNext() && itr.next() == length + 1) {
                length++;
            }
            theNum = 1000 * set[0] + 100 * set[1] + 10 * set[2] + set[3];
            System.out.println("length: " + length + ", num: " + theNum);
            if(length > maxLength) {
                Euler93.maxLength = length;
                Euler93.longest = theNum;
                System.out.println("-> new max so far");
            }
        }
        System.out.println();
    }
        
    private void printSet(int[] set) {
        for(int i: set) {
            System.out.print(i + " "); }
        System.out.println();
    }
}

interface Operation {
    public int op(int a, int b);
    public String type();
}
