package euler69;

public class Euler69 {
   public static void main(String[] args){
      final int limit = 1000000;
      int best = 0, record = 0;
      int array[] = new int[limit+1];
      boolean primes[] = getPrimes(20);
      for (int x = 1;x<array.length;x++){
         for (int y = 1; y<primes.length && y<x;y++){
            if (primes[y] && x%y==0){
                  array[x]++;
            }
         }
      }

      for (int x = 0; x<array.length;x++){
         if (array[x]>record){
            best = x;
            record = array[x];
         }
      } System.out.println(best);	
   }


   public static boolean[] getPrimes(int N){
         // initially assume all integers are prime
      boolean[] isPrime = new boolean[N + 1];
      for (int i = 2; i <= N; i++) {
         isPrime[i] = true;
      }

      // mark non-primes <= N using Sieve of Eratosthenes
      for (int i = 2; i*i <= N; i++) {

         // if i is prime, then mark multiples of i as nonprime
         // suffices to consider mutiples i, i+1, ..., N/i
         if (isPrime[i]) {
            for (int j = i; i*j <= N; j++) {
               isPrime[i*j] = false;
            }
         }
      } return(isPrime);
   }
}
