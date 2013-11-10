package euler83;
import java.io.*;

public class Euler83 {
   public static void main(String[] args) {
      Matrix matrix = new Matrix("matrix.txt", 80);
      matrix.printMatrix();
      matrix.moveDown();
      System.out.println("\nThe shortest path is: " + matrix.numbers[Matrix.DIM-1][Matrix.DIM-1]);
   }   
}

class Matrix {
   static int DIM;
   public int[][] numbers; 
   
   public Matrix(String fileName, int DIM) {
      numbers = new int[DIM][DIM];
      Matrix.DIM = DIM;
      String[] lineItems;
      try{
         BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fileName))));
         for(int i = 0; i < DIM; i++) {
            lineItems = br.readLine().split(",");
            for(int k = 0; k < DIM; k++) {
               numbers[i][k] = Integer.parseInt(lineItems[k]);
            }
         }
      }
      catch(Exception e) {
         System.out.println("Shit happens: " + e.getMessage());
      }
      
   }
   
   public void printMatrix () {
      for(int i = 0; i < DIM; i++) {
         for(int k = 0; k < DIM; k++) {
            System.out.print(numbers[i][k]+ " ");
         }
         System.out.println();
      }
   }
   
   public void moveDown() {
      int i, k;
      for(int line = 1; line < DIM; line++) {  // line 1 through 79
         k = 0;
         for(i = line; i >= 0; i--) {
            System.out.print(numbers[i][k] + " ");
            findMin(i, k);
            k++;
         }
         System.out.println();
      } 
      for(int line = DIM; line < DIM * 2 - 1; line++) {   // line 80 thourgh 158
         i = DIM - 1;
         for(k = line - i; k < DIM; k++) {
            System.out.print(numbers[i][k] + " ");
            findMin(i, k);
            i--;
         }
         System.out.println();
      } 
   }
   
   public void findMin(int i, int k) {
      int min = 999999999;
      int i2, k2, route;
      
      i2 = i; k2 = k - 1;        // top left
      if(k2 >= 0) {
         route = numbers[i2][k2];
         min = route;
      }
      
      i2 = i + 1;  k2 = k - 1;   // left
      if(i2 < DIM && k2 >= 0) {
         route = numbers[i2][k2] + numbers[i2][k2+1];
         if(route < min)
            min = route;
      }
      
      i2 = i + 2;  k2 = k - 2;   // left twice
      if(i2 < DIM && k2 >= 0) {
         route = numbers[i2][k2] + numbers[i2][k2+1] + numbers[i2][k2+2] + numbers[i2-1][k2+2];
         if(route < min)
            min = route;
      }
      
      i2 = i - 1; k2 = k;        // top right
      if(i2 >= 0) {
         route = numbers[i2][k2];
         if(route < min)
            min = route;
      }
      
      i2 = i - 2; k2 = k + 1;        // top right twice
      if(i2 >= 0 && k2 < DIM) {
         route = numbers[i2][k2] + numbers[i2+1][k2] + numbers[i2+2][k2];
         if(route < min)
            min = route;
      }
      
      
      numbers[i][k] += min;
   }
}
