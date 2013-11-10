package euler84;


public class Euler84 {

   static final int DICETYPE = 6;
   static final int ROUNDS = 1000000;

   public static void main(String args[]) {
      Monopoly monopoly = new Monopoly(DICETYPE);
      monopoly.go(ROUNDS);
      monopoly.displayStats();
   }
}

class Monopoly {
   int DICETYPE;
   int rounds;
   int[] diceRoll;  // roll value, doubleCount
   int[] squares;
   int[] CC;
   int[] CH;
   int CCcounter, CHcounter;

   public Monopoly(int DICETYPE) {
      this.DICETYPE = DICETYPE;
      diceRoll = new int[] {0, 0};
      squares = new int[40];
      CCcounter = CHcounter = 0;
      CC = new int[] {0, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
      CH = new int[] {0, 10, 11, 24, 39, 5, -99, -99, -98, -3, -1, -1, -1, -1, -1, -1};
   }

   public int[] rollDice() {
      int d1, d2;
      d1 = (int)(Math.random() * DICETYPE) + 1;
      d2 = (int)(Math.random() * DICETYPE) + 1;
      if(d1 == d2) {
         diceRoll[1]++;
         if(diceRoll[1] >= 3)
            return new int[] {0, 0};
      else
         return new int[] {d1+d2, diceRoll[1]};
         }
         else
      return new int[] {d1+d2, 0};
   }   

   public void go(int ROUNDS) {
      this.rounds = ROUNDS;
      int position = 0;
      for(int i = 0; i < rounds; i++) {
         diceRoll = rollDice();
         //System.out.println("  -> Value rolled: " + diceRoll[0] + " (doubles: " + diceRoll[1] + ")");
         position = (position + diceRoll[0]) % 40;
         if(diceRoll[0] == 0 || position == 30)
            position = 10;
         else if(position == 2 || position == 17 || position == 33)
            position = hitPositionCC(position);
         else if(position == 7 || position == 22 || position == 36)
            position = hitPositionCH(position);
         squares[position]++;
      }
   }

   public int hitPositionCC(int position) {
      CCcounter++;
      CCcounter %= 16;
      if(CC[CCcounter] != -1)
         return CC[CCcounter];             // go to Go or JAIL
      return position;                     // stay in place
   }

   public int hitPositionCH(int position) {
      CHcounter++;
      CHcounter %= 16;
      if(CH[CHcounter] != -1) {
         if(CH[CHcounter] >= 0)
            return CH[CHcounter];           // go to Go, JAIL, or given square
         else if(CH[CHcounter] == -3)
            return position - 3;            // go 3 squares back
         else if(CH[CHcounter] == -99) {    // go to next RAILWAY
            if(position == 7)
               return 15;
            else if(position == 22)
               return 25;
            else
               return 5;
         }
         else {   // CH[CHcounter] == -98   // go to next UTILITY
            if(position == 22)
               return 28;
            else
               return 12;
         }
      }
      else
         return position;                     // stay in place
   }

   public void displayStats() {
      int hits, max, maxPos;
      for(int i = 0; i < 40; i++) {
         hits = squares[i];
      System.out.println("Square " + i + ": " + squares[i] + " hits - " + (hits * 100.0 / rounds) + "%");
      }
      System.out.print("\nTop hit squares: ");
      max = 0;
      maxPos = 0;
      for(int i = 0; i < 3; i++) {
         for(int k = 0; k < 40; k++) {
            if(squares[k] >= max) {
               max = squares[k];
               maxPos = k;
            }
         }
         System.out.print(maxPos + " ");
         squares[maxPos] = 0;
         max = 0;
      }
      System.out.println("\n");
   }
}