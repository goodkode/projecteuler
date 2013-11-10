
package euler87;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Euler87 {
   private final int MAX = 50000000;
   private int ceiling;
   private int howMany;
   private List<Integer> primes;
   private Set<Integer> numberSet;
   
   private void findCeiling() {
      ceiling = (int)Math.sqrt(MAX) + 1;
   }
   
   private void buildPrimes() {
      int[] primeSieve = new int[ceiling + 1];
      for(int i = 0; i <= ceiling; i++) 
          primeSieve[i] = 1;
      primeSieve[0] = primeSieve[1] = 0;
      primeSieve[2] = 1;
      for(int p = 2; p * p <= ceiling; p++)
         if(primeSieve[p] == 1) {
            int pointer = 2 * p;
            while(pointer <= ceiling) {
               primeSieve[pointer] = 0;
               pointer += p;
            }
      }
      primes = new ArrayList<Integer>();
      for(int i = 0; i <= ceiling; i++) 
          if(primeSieve[i] == 1)
            primes.add(i);
      //System.out.println("Count: " + primes.size());
      //System.out.println(primes.toString());
   }
   
   private void howMany() {
      this.numberSet = new HashSet<Integer>();
      int value;
      for(int a = 0; a < primes.size(); a++)
         for(int b = 0; b < primes.size(); b++) 
            for(int c = 0; c < primes.size(); c++) {
               value = (int)(Math.pow(primes.get(a), 2) + Math.pow(primes.get(b), 3) + Math.pow(primes.get(c), 4));
               if(value < this.MAX)
                  numberSet.add(value);
               else
                  c = primes.size();
            }
      System.out.println("Numbers below fifty million: " + this.numberSet.size());
   }
   
   public static void main(String[] args) {
      Euler87 euler87 = new Euler87();
      euler87.findCeiling();
      euler87.buildPrimes();
      euler87.howMany();
   }
}

