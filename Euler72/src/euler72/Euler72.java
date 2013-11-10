package euler72;

public class Euler72 {
   private final static int D = 100000;
   private static long unique;
   private static boolean[] primes;

   public static void main(String[] args) {
      unique = (long)D * (D - 1) / 2;
      primes = primeSieve(D);
      System.out.println("Initial elements: " + unique);
      unique = reduction(D);
      System.out.println("The result is: " + unique);
   }
   
   private static long reduction(int D) {
      int factor;
      unique -= D / 2 - 1;
      for(int d = 3; d <= D/2; d++) {
         factor = 2 * (D / d - 1);
         unique -= factor;
         int n = 2;
         while(n < (d+1) / 2) {
            //System.out.println(n + "/" + d);
            if(isNotReducible(n, d))
               unique -= factor;
            n++;
         }
      }
      return unique;
   }
   
   private static boolean isNotReducible(int n, int d) {
      if(primes[d])
         return true;
      int i = 2;
      while(i <= n) {
         if(n % i == 0 && d % i == 0)
            return false;
         i++;
         while(!(primes[i]))
            i++;
      }
      return true;
   }
   
   private static boolean[] primeSieve(int D) {
      boolean[] myPrimes = new boolean[D+1];
      for(int i = 2; i <= D; i++)
         myPrimes[i] = true;
      for(int i = 2; i <= D; i++) {
         if(myPrimes[i])
            for(int k = 2; k * i <= D; k++)
               myPrimes[k * i] = false;
      }
      /*int count = 0;
      for(int i = 2; i <= D; i++)
         if(primes[i]) {
            count++;
            System.out.print(i + " ");
         }
      System.out.println("\nnumber of primes: " + count);*/
      return myPrimes;
   }
}
