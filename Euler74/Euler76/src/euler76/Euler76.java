package euler78;

public class Euler78 {
   static int count = 0;

   public static void main(String[] args) {
      final int TOP = 5;
      summs(100, 99);
      System.out.println("Possibilities: " + count);
   }
   
   private static int summs(int n, int max) {
      if(n == 0) {
         //System.out.println();
         count++;
         return 1;
      }
      else {
         for(int i = max; i > 0; i--) {
            //System.out.print(i);
            summs(n - i, (n - i) > i ? i : (n-i));
         }
      }
      return 0;
   }
}
