#define TOP 2000000

int main() {
   int a, b;       //a = horizontal dim, b = vertical dim
   a = b = 2;
   int best, area, SI, SI_temp;
   int best_temp, area_temp;
   
   SI = squares_inside(a, b);
   while(SI < TOP)
      SI = squares_inside(++a, b);
   SI_temp = squares_inside(a-1, b);
   if((TOP - SI_temp) < (SI - TOP)) {
      best = TOP - SI_temp;
      area = --a * b;
   }
   else {
      best = SI - TOP;
      area = 2 * (a + b);
   }
   printf("So the first is: %d, %d (%d)\n", a, b, best);
   
   while((++b) <= a) {
      SI = squares_inside(a, b);
      while(SI > TOP) {
         SI = squares_inside(--a, b);
      }
      SI_temp = squares_inside(a+1, b);
      if((SI_temp - TOP) < (TOP - SI)) {
         best_temp = SI_temp - TOP;
         area_temp = ++a * b;
      }
      else {
         best_temp = TOP - SI;
         area_temp = a * b;
      }
      printf("Then it is: %d, %d (%d), area: %d\n", a, b, best_temp, area_temp);
      if(best_temp < best) {
         best = best_temp;
         area = area_temp;
      }
   }  
   
   printf("Final result:\narea: %d (%d)\n", area, best);
   
   return (0);    
}

int squares_inside(int a, int b) {
    int SI = 0;
    int j, k;
    for(j = 0; j < a; j++)
        for(k = 0; k < b; k++)
            SI += (a - j) * (b - k);
    printf("%d, %d (%d)\n", a, b, SI);
    return SI;
}

