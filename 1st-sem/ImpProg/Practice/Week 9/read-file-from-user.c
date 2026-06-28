#include <ctype.h>
#include <stdio.h>

int main(void)
{
    char filename[100];
    scanf("%s", filename);

    FILE *fp = fopen(filename, "r");

    if (!fp)
    {
        perror("fopen");
        return 1;
    }

    int ch = 0;
    while (ch = fgetc(fp) != EOF)
    {
        putchar(toupper(ch));
    }
}