#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define LIMIT 1500000

void intializePerimeters(int* perimeters) {
   int i;
   for(i = 1; i <= LIMIT; i++)
      perimeters[i] = 0;
}

int main(int argc, char** argv) {
   int perimeters[LIMIT + 1];
   intializePerimeters(perimeters);
   printf("Perimeter array initialized...\n");
   long long a, b, c, c_sqr;
   for(a = 1; a < LIMIT/3; a++) {      
      for(b = a + 1; b < LIMIT/2 - a; b++) {
         c_sqr =  a * a + b * b;
         c = (long long) sqrt((double) c_sqr);
         if(c_sqr == c * c) {
            //printf("%d %d %d\n", a, b, c);
            perimeters[a + b + c]++;
         }      
      }
   }
   int i, count = 0;
   for(i = 1; i <= LIMIT; i++)
      if(perimeters[i] == 1)
         count++;
   printf("Triangles: %d", count);
   return (EXIT_SUCCESS);
}



