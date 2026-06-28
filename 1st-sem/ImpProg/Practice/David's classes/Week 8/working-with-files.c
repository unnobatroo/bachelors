#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>

void switch_case(char *str);

int main(void)
{
    const char *file_name = "hello.txt";
    // FILE* file1 = fopen(file_name, "w+");
    // if (file1 == NULL)
    // {
    //     perror("fopen");
    //     return 1;
    // }
    // fputs("Hello World!\n", file1);
    // fclose(file1);

    FILE *file2 = stdin;
    if (file2 == NULL)
    {
        perror("fopen");
        return 1;
    }
    char buf[8];
    while (fgets(buf, 8, file2) != NULL)
    {
        switch_case(buf);
        printf("%s", buf);
    }
    return 0;
}

void switch_case(char *str)
{
    while (*str != '\0')
    {
        if (islower(*str))
        {
            *str = toupper(*str);
        }
        else if (isupper(*str))
        {
            *str = tolower(*str);
        }
        str++;
    }
}