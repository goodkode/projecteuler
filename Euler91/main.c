#include <stdio.h>
#include <stdlib.h>
#define LIMIT 50

int main(int argc, char** argv) {
    int solutions = 0;
    int x1, y1, x2, y2;
    // point "P" = [x1, y1]
    for(x1 = 0; x1 <= LIMIT; x1++)
        for(y1 = 0; y1 <= LIMIT; y1++)
            // point "Q" = [x2, y2]
            for(x2 = 0; x2 <= LIMIT; x2++)
                for(y2 = 0; y2 <= LIMIT; y2++)
                    is_right_triangle(x1, y1, x2, y2, &solutions);
    printf("\nNumber is right triangles is: %d\n", solutions / 2);

    return (EXIT_SUCCESS);
}

void is_right_triangle(int x1, int y1, int x2, int y2, int* solutions) {
    // if the points are the same OR they one is on the origin, that's bad
    if(x1 == x2 && y1 == y2)
        return;
    if((x1 == 0 && y1 == 0) || (x2 == 0 && y2 == 0))
        return;
    // if the points are on the lines, it will be good
    int s1, s2, s;
    // if the points are on the lines, it will be good for sure
    if(x1 == 0 && y2 == 0 || x2 == 0 && y1 == 0) {
        //printf(" [%d, %d], [%d, %d]\n", x1, y1, x2, y2);
        (*solutions)++;
        return;
    }
    int side_a2 = x1 * x1 + y1 * y1;
    int side_b2 = x2 * x2 + y2 * y2;
    int side_c2 = (x2-x1) * (x2-x1) + (y2-y1) * (y2-y1);
    if(side_a2 + side_b2 == side_c2 || side_a2 + side_c2 == side_b2 || side_b2 + side_c2 == side_a2) {
        //printf("[%d, %d], [%d, %d]\n", x1, y1, x2, y2);
        (*solutions)++;
        return;
    }
}

