package euler78;

public class Euler78 {

   public static void main(String[] args) {
      Coins myCoin = new Coins();
      myCoin.display();
   }
}

class Coins {
   private static final int TOP = 1000;
   private static long[][] sumz = new long[TOP+1][TOP+1];
   
   public Coins() {
      sumz[0][0] = 1;
      sumz[1][0] = sumz[1][1] = 1;
      for(int n = 2; n <= TOP; n++)
         for(int max = 1; max <= n; max++)
            sumz[n][max] = summs(n, max);
   }
   
   private long summs(int n, int max) {
      if(max == 1)
         return 1;
      else {
         long c = 0;
         for(int i = 1; i <= max; i++) {
            c += sumz[n - i][i > (n-i) ? (n-i) : i];
         }
         c %= 1000000;
         if(c % 1000000 == 0)
            System.out.println("     Found it!!! " + n + ":  " + c);
         return c;
      }
   }
   
   public void display() {
      for(int i = 1; i <= TOP; i++) {
         //System.out.println("For " + i + ":  " + sumz[i][i]);
         if(sumz[i][i] % 100000 == 0)
            System.out.println("     Found it!!! " + i + ":  " + sumz[i][i]);
      }
   }
}
