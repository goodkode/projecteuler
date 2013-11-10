
package euler100;
import java.math.BigInteger;

public class Euler100 {
    static private String start = "1000000000000";
    static private Side left, right;
    static long diff;
    
    public static void main(String[] args) {
        Euler100 problem = new Euler100();
        for (int i = 0; i < 1; i++) 
            problem.iterate();
    }
    
    private class Side {
        BigInteger value;
        long increase;
        boolean leftSide;
        long leftValue;
        Side(String side) {
            BigInteger a;
            if (side.equals("left")) {
                leftValue = (long) (Long.parseLong(start) * Math.sqrt(8) / 4);
                a = BigInteger.valueOf(leftValue);
                leftSide = true;
                value = a.multiply(a.subtract(BigInteger.ONE)).
                                     multiply(BigInteger.valueOf(2));
                increase = a.multiply(a.add(BigInteger.ONE)).
                             multiply(BigInteger.valueOf(2)).subtract(value).longValue();
            }
            else {
                a = new BigInteger(start);
                leftSide = false;
                value = a.multiply(a.subtract(BigInteger.ONE));
                increase = a.multiply(a.add(BigInteger.ONE)).subtract(value).longValue();
            }
            System.out.print("* Initialized "+side+": " + a + " ("+value+")");
            System.out.println("  (increase: "+increase+")");
        }
        void increase() {
            if (leftSide) {
                diff -= increase;
                leftValue++;
                increase += 4; }
            else {
                diff += increase;
                increase += 2; }
        }
    }

    private Euler100() {
        left = new Side("left");
        right = new Side("right");
        diff = right.value.subtract(left.value).longValue();
    }
    
    private void iterate() {
        while (diff != 0) {
            if (diff > 0) 
                left.increase();
            if (diff < 0)
                right.increase();
        }
        System.out.println("*** value: "+left.leftValue);
        right.increase();
    }
 }
