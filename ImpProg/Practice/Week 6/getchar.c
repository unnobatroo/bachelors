#include <stdio.h>

int main(){
    int ch;
    // while((ch = getchar()) != EOF){
    //     if('A' <= ch && ch <= 'Z'){
    //         printf("This is a capital letter.");
    //     }
    // }

    while((ch = getchar()) != EOF){
        if(ch >= 'a' && ch <= 'z'){
            putchar(ch - 32);
        }
    }
}