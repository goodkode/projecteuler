#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char** argv) {
   int a, b, c;
   int count = 0;
   int M = 1000;
   for(a = 1; a <= M; a++)
      for(b = 1; b <= a; b++)
         for(c = 1; c <= b; c++)
            count += check_cuboid(a, b, c);
   printf("Solutions found up to %d: %d\n", M, count);
   while(count <= 1000000) {
      a = ++M;
      for(b = 1; b <= a; b++)
         for(c = 1; c <= b; c++)
            count += check_cuboid(a, b, c);
   }
   printf("Solutions found for %d: %d\n", M, count);

   return (EXIT_SUCCESS);
}

int check_cuboid(int a, int b, int c) {
   int d, a2bc2;
   a2bc2 = a * a + (b+c) * (b+c);
   d = (int)(sqrt(a2bc2) + 0.1);
   if(d * d == a2bc2) {
      //printf("Diagonal: %d  (%d, %d, %d)\n", d, a, b, c);
      return 1;
   }
   return 0;
}

