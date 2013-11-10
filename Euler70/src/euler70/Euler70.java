package euler70;
import java.util.ArrayList;

public class Euler70 {
   private static final int MAX = 10000000;
   private static boolean[] primes;

   public static void main(String[] args) {
      long startTime = System.currentTimeMillis();
      //Prime Sieve
      primes = fillPrimes(MAX);
      System.out.println("Primes filled in " + (System.currentTimeMillis() - startTime) + " miliseconds...");
      
      ArrayList<Integer> factors;
      int phi, maxAt = 1;
      int bestN = 0;
      double ratio, minRatio = 100;
      for(int n = 2; n < MAX; n++) {
         factors = primeFactors(n);
         phi = totientFunction(n, factors);
         //System.out.println(n + ": " + factors + " phi(n): " + phi);
         if(isPermutation(n, phi)) {
            System.out.println("phi(" + n + ")=" + phi + " is a solution");
            ratio = n * 1.0 / phi;
            if(ratio < minRatio) {
               minRatio = ratio;
               bestN = n;
            }
         }
      }
      System.out.println("\nThe solution is: " + bestN);      
   }
   
   private static boolean isPermutation(int n, int phi) {
      if(n == phi+1)
         return false;
      int[] nArr, phiArr;
      nArr = new int[7];
      phiArr = new int[7];
      int i = 0;
      while(phi != 0) {
         nArr[i] = n % 10;
         phiArr[i] = phi % 10;
         n /= 10;
         phi /= 10;
         i++;
         if(phi == 0 && n != 0)
            return false;
      }
      int max, mIndex, j, k;
      for(j = 0; j < i; j++) {
         max = -1;
         mIndex = -1;
         for(k = 0; k < i; k++)
            if(max < nArr[k]) {
               max = nArr[k];
               mIndex = k;
            }
         nArr[mIndex] = -99;
         for(k = 0; k < i; k++)
            if(phiArr[k] == max) {
               phiArr[k] = -99;
               k = 99;
            }
         if(k != 100)
            return false;
      }
      return true;
   }
   
   private static int totientFunction(int n, ArrayList<Integer> factors) {
      int phi = n;
      for(int f : factors)
         phi = (f - 1) * (phi / f);
      return phi;
   }
   
   private static ArrayList primeFactors(int n) {
      ArrayList<Integer> factors = new ArrayList<Integer>();      
      int p = 2;
      while(p <= n) {
         if(primes[n]) {
            factors.add(n);
            return factors;
         }
         if(primes[p] && (n % p == 0)) {
            factors.add(p);
            do
               n /= p;
            while(n % p == 0);
         }
         p++;
      }
      return factors;
   }
   
   private static boolean[] fillPrimes(int max) {
      boolean[] bPrimes = new boolean[max+1];
      for(int n = 0; n <= max; n++)
         bPrimes[n] = true;
      for(int i = 2; i*i <= max; i++)
         if(bPrimes[i])
            for(int j = 2; j*i <= max; j++)
               bPrimes[i*j] = false;        
      return bPrimes;
   } 
}
