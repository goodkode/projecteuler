#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
    printf("Euler 97 program started...\n");
    printf("sizeof(long) = %d\n", sizeof(long));
    long x = 2L;
    int i;
    for (i = 2; i <= 7830457; i++) {
        //printf("%d\n", x);
        x = x * 2L;
        x = x % 10000000000L;
        if (i < 100)
            printf("%d\n", x);
        if (i > 7830400)
            printf("%d\n", x);
    }
    printf("2^7830457 = %ld\n", x);
    x = x * 28433L;
    x = (x + 1L) % 10000000000L;
    printf("The result is: %ld\n", x);
    return (EXIT_SUCCESS);
}

