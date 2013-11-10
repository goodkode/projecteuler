#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void analyze(int x, int y, double *sig, int *exp, int *line);

int main() {
    FILE *input_file = fopen("base_exp.txt", "r");
    if (input_file == NULL)
        perror("Can't open the file");
    double sig, max_sig;
    int exp, max_exp;
    int x, y, line, max_line;
    max_sig = max_exp = 0;
    x = y = 0;
    line = max_line = 1;
    char c;
    do {
        c = fgetc(input_file);
        //putchar(c);
        if (c >= '0' && c <= '9')
            y = y * 10 + (c - '0');
        else if (c == ',') {
            x = y;
            y = 0;
        }
        else {
            printf("%d: %d^%d\n", line, x, y);
            analyze(x, y, &sig, &exp, &line);
            if (exp > max_exp || (exp == max_exp && sig > max_sig)) {
                max_line = line;
                max_exp = exp;
                max_sig = sig;
                printf("*** new max: %f * 10^%d\n", sig, exp);
            }
            x = y = 0;
            line++;
        }
    }
    while (c != EOF);
    printf("The results are in!! Max is in line: %d\n", max_line);
    fclose(input_file);
    return (EXIT_SUCCESS);
}

void analyze(int x, int y, double *sig, int *exp, int *line) {
    double s;
    s = x;
    int e = 0;
    int i;
    while (s >= 10) {
        s /= 10;
        e ++; }
    printf(" -> %f * 10^%d\n", s, e);
    e = e * y;
    *sig = s;
    for (i = 2; i <= y; i++) {
        s =  s * (*sig);
        if (s >= 10) {
            s /= 10;
            e++; }
    }
    printf("--> %f * 10^%d\n", s, e);
    *sig = s;
    *exp = e;
}

