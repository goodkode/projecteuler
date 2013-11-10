package euler73;

public class Euler73 {
   private static final int limit = 12000;
   private static boolean[] primes;
   private static boolean[] column;
   
   public static void main(String[] args) {
      long start = System.currentTimeMillis();
      fillPrimes(limit);
      int count = 0;
      for(int d = 3; d <= limit; d++)
         count += columnCount(d);
      System.out.println("\nReduced proper fractions up to " + limit);
      System.out.println("\t\tbetween 1/3 and 1/2: " + count);
      System.out.println("\nTotal time: " + (System.currentTimeMillis() - start) + "ms");
   }
   
   private static int columnCount(int d) {
      int count = 0;
      final int top = (d - 1) / 2;
      if(primes[d]) {
         //System.out.println("column: *" + d + ", count: " + (top - d / 3));
         return (top - d / 3);
      }
      column = new boolean[top + 1];
      for(int i = 2; i <= top; i++)
         column[i] = true;
      for(int i = 2; i <= top; i++)
         if(primes[i] && (d % i == 0))
            for(int k = 2; i * k <= top; k++)
               column[i * k] = false;
      for(int i = d / 3 + 1; i <= top; i++)
         if(column[i])
            count++;
      //System.out.println("column:  " + d + ", count: " + count);
      return count;
   }
   
   private static void fillPrimes(int limit) {
      primes = new boolean[limit + 1];
      for(int i = 2; i <= limit; i++)
         primes[i] = true;
      for(int i = 2; i * i <= limit; i++)
         if(primes[i])
            for(int k = 2; k * i <= limit; k++)
               primes[i*k] = false;
   }
}
