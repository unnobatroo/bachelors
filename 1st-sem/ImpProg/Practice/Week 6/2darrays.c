#include <stdio.h>

int main(){
    int array[5][5] = {{0}};
    int* p = &array[0][0];

    for(int i = 1; i <= 5; i++){
        for(int j = 1; j <= 5; j++){
            *p = i * j;
            p++;
        }
    }

    for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
            printf("%2d ", array[i][j]);
        }
        printf("\n");
    }
}