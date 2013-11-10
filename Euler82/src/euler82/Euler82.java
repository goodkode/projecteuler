package euler82;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Euler82 {
   int[][] numbers;
   final int DIM = 80;
   
   Euler82(String fileName) throws Exception{
      numbers = new int[DIM][DIM];
      DataInputStream in = new DataInputStream(new FileInputStream(fileName));
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String[] lineItems;
      for(int i = 0; i < DIM; i++) {
         lineItems = br.readLine().split(",");
         for(int k = 0; k < DIM; k++)
            numbers[i][k] = Integer.parseInt(lineItems[k]);
      }
   }
   
   void findPath() {
      int minPath, vertSum;
      for(int k = 1; k < DIM; k++) {
         for(int i = 0; i < DIM; i++) {
            minPath = 0;
            vertSum = 0;
            if(i == 0)
               minPath += numbers[0][k-1]; 
            else
               minPath += numbers[i-1][k] < numbers[i][k-1]? numbers[i-1][k] : numbers[i][k-1];
            int iPlus = 1;
            while(vertSum < minPath && (i + iPlus) < DIM) {
               vertSum += numbers[i + iPlus][k];
               if(numbers[i + iPlus][k-1] + vertSum < minPath)
                  minPath = numbers[i + iPlus][k-1] + vertSum;
               iPlus++;
            }
            numbers[i][k] += minPath;
         }
      }
   }
   
   void minPath() {
      int minPath = numbers[0][DIM-1];
      for(int i = 1; i < DIM; i++)
         if(numbers[i][DIM-1] < minPath)
            minPath = numbers[i][DIM-1];
      System.out.println("\nThe shortest path found is: " + minPath + "\n");
   }
   
   void printMatrix() {
      System.out.print("\nThe matrix: \n");
      for(int i = 0; i < DIM; i++) {
         for(int k = 0; k < DIM; k++) {
            System.out.print(numbers[i][k] + ", ");
         }
         System.out.println();
      }
   } 

   public static void main(String[] args) {
      try {
         Euler82 matrix = new Euler82("matrix.txt");
         matrix.printMatrix();
         matrix.findPath();
         matrix.printMatrix();
         matrix.minPath();
      }
      catch(Exception e) {
         System.out.println("Error: " + e.getMessage());
      }
   }
}
