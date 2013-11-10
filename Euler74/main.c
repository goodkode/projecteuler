#include <stdio.h>
#include <stdlib.h>

int main() {
   int i, x, count = 0;
   int factorials[10];
   int criticals[8][2] = {{145, 1}, {169, 3}, {871, 2}, {872, 2}, {1454, 3}, {45361, 2}, {45362, 2}, {363601, 3}};
   fillFactorials(factorials);
   for(x = 10; x < 1000000; x++)
      if(nonRepeating(x, factorials, criticals) == 60)
         count++;
   printf("Test\n");
   printf("%d -> %d\n", 145, nonRepeating(145, factorials, criticals));
   printf("%d -> %d\n", 69, nonRepeating(69, factorials, criticals));
   printf("%d -> %d\n", 78, nonRepeating(78, factorials, criticals));
   printf("Numbers with 60 non-repating terms: %d", count);
   return (EXIT_SUCCESS);
}

int fillFactorials(int* factorials) {
   int i = 0;
   factorials[0] = 1;
   for(i = 1; i < 10; i++) 
      factorials[i] = factorials[i - 1] * i;
}

int nextNumber(int n, int* factorials) {
   int x = 0;
   do {
      x += factorials[n % 10];
      n /= 10;
   }
   while(n > 0);
   return x;
}

int nonRepeating(int x, int* factorials, int criticals[][2]) {
   int i, count = 0;
   while(count < 60) {
      for(i = 0; i < 8; i++)
         if(x == criticals[i][0])
            return count + criticals[i][1];
      x = nextNumber(x, factorials);
      count++;
   }
   return 0;
}



