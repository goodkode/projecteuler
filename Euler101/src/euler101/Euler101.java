package euler101;

import java.text.NumberFormat;
import java.util.Arrays;

public class Euler101 {
    private static long[] nums;
    private static int limit = 11;
    private static long FITs;

    public static void main(String[] args) {
        Euler101 problem = new Euler101();
        problem.setUp();
        problem.iterate();
        System.out.println("\nThe FITs are: " + FITs);
    }
    
    private void setUp() {
        nums = new long[limit];
        FITs = 1;
        for(int k = 0; k < limit; k++) {
            System.out.println("k = " + (k+1) + ": " + un(k+1));
            nums[k] = un(k+1); }
    }
    
    private void iterate() {
        for(int k = 2; k <= limit; k++) {
            System.out.println("Looking at " + k + " items...");
            FITs += findFunction(Arrays.copyOfRange(nums, 0, k)); }
    }
    
    private long findFunction(long[] arr) {
        //for(int i = 0; i < arr.length; i++) System.out.print(arr[i] + " ");
        long thisFIT = 0;
        int dim = arr.length;
        double[][] A = new double[dim][dim];
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
                A[i][j] = Math.pow((i+1), (dim-j-1));
        Matrix left = new Matrix(A);
        //System.out.print("Matrix: "); left.print(NumberFormat.getInstance(), 3);
        
        double[][] R = new double[dim][1];
        for(int i = 0; i < dim; i++)
            R[i][0] = arr[i];
        Matrix right = new Matrix(R);
        //System.out.print("Equals: "); right.print(NumberFormat.getInstance(), 1);
                
        Matrix Coef = left.inverse().times(right);
        System.out.print("Coefficients: "); Coef.print(NumberFormat.getInstance(), 3);
        
        for(int pow = dim - 1; pow >= 0; pow--) {
            long cf = (long) Math.round(Coef.get(dim-1-pow, 0));
            //System.out.println(cf + "*" + (dim+1) + "^" + pow);
            long tf = cf * lPow((dim+1), pow); 
            thisFIT += tf; }
        if(thisFIT != un(dim+1)) {
            System.out.print("  -> this FIT is: " + thisFIT);
            System.out.println(" (" + un(dim+1) + ")\n"); }
        else {
            System.out.println("  -> CORRECT! (" + un(dim+1) + ")");
            thisFIT = 0; }
        return thisFIT;
    }
    
    private long un(long n) {
        long result;
        result = 1 - n + n*n - n*n*n + n*n*n*n - n*n*n*n*n + n*n*n*n*n*n 
                - n*n*n*n*n*n*n + n*n*n*n*n*n*n*n - n*n*n*n*n*n*n*n*n 
                + n*n*n*n*n*n*n*n*n*n;
        //result = n * n * n;
        return result;
    }
    // helper function
    private long lPow(long bs, long pw) {
        long base = (long) bs;
        long pow = (long) pw;
        long res = 1;
        //System.out.print("   " + base + "^" + pow);
        if(pow == 0) {
            //System.out.println(" -> " + res);
            return res; }
        for(int i = 1; i <= pow; i++)
            res *= base;
        //System.out.println(" -> " + res);
        return res;
    }
}

