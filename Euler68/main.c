#include <stdio.h>
#include <stdlib.h>
#define MAX 10

void clear_solution(int s[], int *spoke) {
   int i;
   for(i = 1; i < MAX; i++)
      s[i] = 0;
   for(i = 0; i < 5; i++)
      spoke[i] = 0;
}

int duplicate(int s[], int b) {
   int i = 0;
   while(s[i] != 0) {
      if(s[i] == b)
         return 1;
      i++;
   }
   return 0;
}

void prints(int *s) {
   printf("%d,%d,%d; %d,%d,%d; %d,%d,%d; %d,%d,%d; %d,%d,%d\n", s[0],s[1],s[2], s[1],s[3],s[4], s[3],s[5],s[6], s[5],s[7],s[8], s[7],s[0],s[9]);
}

int main() {
   int num[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
   int solution[MAX];
   int spoke[5];
   int a[MAX];
   
   //first spoke
   for(a[0] = 1; a[0] < MAX; a[0]++) {
      clear_solution(solution, spoke);
      solution[0] = a[0];
      for(a[1] = 1; a[1] < MAX; a[1]++) {
         if(duplicate(solution, a[1]))
            continue;
         solution[1] = a[1];
         for(a[2] = 1; a[2] < MAX+1; a[2]++) {
            if(duplicate(solution, a[2]))
              continue;
            solution[2] = a[2];
            spoke[0] = a[0] + a[1] + a[2];
            if(spoke[0] > 19)
               break;
           
            if(spoke[0] >= 14) {
               //start second spoke
               for(a[3] = 1; a[3] < MAX; a[3]++) {
                  if(duplicate(solution, a[3]))
                     continue;
                  solution[3] = a[3];
                  for(a[4] = 1; a[4] < MAX+1; a[4]++) {
                     if(duplicate(solution, a[4]))
                        continue;
                     solution[4] = a[4];
                     spoke[1] = a[1] + a[3] + a[4];
                     
                     
                     if(spoke[1] == spoke[0]) {
                        //start third spoke
                        for(a[5] = 1; a[5] < MAX; a[5]++) {
                           if(duplicate(solution, a[5]))
                              continue;
                           solution[5] = a[5];
                           for(a[6] = 1; a[6] < MAX+1; a[6]++) {
                              if(duplicate(solution, a[6]))
                                 continue;
                              solution[6] = a[6];
                              spoke[2] = a[3] + a[5] + a[6];
                              
                              if(spoke[2] == spoke[0]) {
                                 //start fourth spoke
                                 for(a[7] = 1; a[7] < MAX; a[7]++) {
                                    if(duplicate(solution, a[7]))
                                       continue;
                                    solution[7] = a[7];
                                    for(a[8] = 1; a[8] < MAX+1; a[8]++) {
                                       if(duplicate(solution, a[8]))
                                          continue;
                                       solution[8] = a[8];
                                       spoke[3] = a[5] + a[7] + a[8];

                                       if(spoke[3] == spoke[0]) {
                                          //start fifth (last) spoke
                                          for(a[9] = 1; a[9] < MAX+1; a[9]++) {
                                             if(duplicate(solution, a[9]))
                                                continue;
                                             solution[9] = a[9];
                                             spoke[4] = a[7] + a[0] + a[9];
                                             
                                             if(spoke[4] == spoke[0])
                                                //all spokes are equal
                                                prints(solution);
                                   
                                          }}}}}}}}}}}}}}
   
   return (EXIT_SUCCESS);
}

