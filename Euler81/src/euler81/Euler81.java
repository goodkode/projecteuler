package euler81;

import java.io.*;

public class Euler81 {
   private static final int N = 80;

   public static void main(String[] args) {
      int[][] matrix = new int[80][80];
      try{
         DataInputStream in = new DataInputStream(new FileInputStream("matrix.txt"));
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String[] lineItems;
         int k;
         for(int i = 0; i < 80; i++) {
            lineItems = br.readLine().split(",");
            k = 0;
            for(String st: lineItems) {
               matrix[i][k] = Integer.parseInt(st);
               //System.out.print(matrix[i][k] + ", ");
               k++;
            }
            //System.out.println();
         }
         passThrough(matrix);
         printMatrix(matrix);
         in.close();
      }
      catch(Exception e) {
         System.out.println("Error: " + e.getMessage());
      }
   }
   
   static void passThrough(int[][] matrix) {
      int i, k, z;
      for(z = 1; z < N; z++) {
         i = z;
         for(k = 0; k <= z; k++) {
                  if(k == 0)
                     matrix[i][k] += matrix[i-1][k];
                  else if(i == 0)
                     matrix[i][k] += matrix[i][k-1];
                  else
                     matrix[i][k] += (matrix[i-1][k] < matrix[i][k-1]) ? matrix[i-1][k] : matrix[i][k-1];
                  i--;
         }
      }
      for(z = N - 1; z >= 1; z--) {
         k = N - z;
         for(i = N - 1; i >= N - z; i--) {
                  matrix[i][k] += (matrix[i-1][k] < matrix[i][k-1]) ? matrix[i-1][k] : matrix[i][k-1];
                  k++;
         }
      }
   }
 
   static void printMatrix(int[][] matrix) {
      for(int i = 0; i < matrix.length; i++) {
         for(int k = 0; k < matrix[i].length; k++)
                  System.out.print(matrix[i][k] + " ");
         System.out.println();
      }
   }
}
