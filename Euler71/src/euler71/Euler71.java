
package euler71;

public class Euler71 {
   static final int LIMIT = 1000000;
   static final double REFERENCE = 3.0 / 7;
   static final double EPSILON = 0.00000001;
   static private double smallest = 1;

   public static void main(String[] args) {
      int[] rightResult = moveToRight();
      System.out.println(rightResult[0] + "/" + rightResult[1]);
      int[] leftResult = moveToLeft();
      System.out.println(leftResult[0] + "/" + leftResult[1]);
      
   }
   
   static private int[] moveToRight() {
      int[] arr = new int[2];
      int N, n, D, d;
      double diff;
      N = n = 3;
      D = d = 7;
      while(d > 2) {
         d--;
         while(n * 1.0 / d >= REFERENCE)
            n--;
         diff = REFERENCE - n * 1.0 / d;
         if(diff < smallest) {
            smallest = diff;
            arr[0] = n;
            arr[1] = d;
         }
      }     
      return arr;
   }
   
   static private int[] moveToLeft() {
      int[] arr = new int[2];
      int N, n, d;
      double diff;
      N = 3;
      d = 7;
      while(d < LIMIT) {
         n = N;
         d++;
         while((n * 1.0 / d) < REFERENCE) {
            N = n;
            n++      ;
         }
         diff = REFERENCE - (--n) * 1.0 / d;
         if(diff < EPSILON)
            System.out.println("-> " + n + "/" + d + " (" + diff + ")");
         if(diff < smallest) {
            smallest = diff;
            arr[0] = n;
            arr[1] = d;
         }
      }     
      return arr;
   }
}
