package euler80;

import java.math.BigInteger;

public class Euler80 {

   public static void main(String[] args) {
      BigNum srs;
      for(int n = 2; n < 11; n++) {
         srs = new BigNum(n);
         srs.testSRS();
      }     
   }
}

class BigNum {
   BigInteger bigNum;
   int n;

   BigNum(int n) {
      this.n = n;
      long nl = (long)(Math.sqrt((double)n) * Math.pow(10,17));
      bigNum = BigInteger.valueOf(nl);
      System.out.println("New number created from " + n + " : " + bigNum.toString());
   }

   public BigInteger testSRS() {
      BigInteger bi = bigNum.pow(2);
      System.out.println("Squeared: " + bi.toString());
      BigInteger diff = bi.subtract(BigInteger.valueOf(n).multiply(BigInteger.TEN.pow(34)));
      System.out.println("Difference: " + diff.toString());
      return diff;
   }
   
   public BigInteger approximate() {
      BigInteger diff = this.testSRS();
      String diffString = diff.toString();
      return diff;
   }
   
}