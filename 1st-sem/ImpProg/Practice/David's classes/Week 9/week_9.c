#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// int main(int argc,  char *argv[])
// {
//     printf("%d\n", argc);
//     for (int i = 0; i < argc; i++)
//     {
//         printf("%s ", argv[i]);
//     }
//     printf("\n");
//     return 0;

//     //echo $? ---> can show the number of erros in code
//     // *.c
//     //

// }

// int main(int argc, char * argv[])
// {
//     if (argc ==1)
//     {
//         //FILE *fp
//         //stdin, stdout, stderr
//         fprintf(stderr, "usage %s <one argument>", argv[0]);
//         return 1;
//     }
//     if (strcmp(argv[1], "hello") == 0)
//     // strcmp --> string compare
//     // pear > apple -> positive
//     // apple < pear -> negative
//     // apple == apple -> 0;
//     {
//         fprintf(stdout, "how do you do?\n");
//     } else {
//         fprintf(stdout, "hello %s\n", argv[1]);
//     }
//     return 0;
// }

// int main(int argc, char *argv[]) {
//   if (argc == 1) {
//     fprintf(stderr, "usage %s <file>\n", argv[0]);
//     return 1;
//   }

//   FILE *fp = fopen(argv[1], "r");
//   if (fp == NULL) {
//     perror("unable to open file");
//     return 1;
//   }

//   // char buf[1024];
//   // while (fgets(buf, sizeof(buf), fp) != NULL)
//   // {
//   //     fprintf(stdout, "%s\n", buf);
//   //     fprintf(stdout, "--------------\n");
//   // }

//   // int c = fgetc(fp);
//   int c;
//   while ((c = fgetc(fp)) != EOF) {
//     // fprintf(stdout, "%c", c);
//     if (c == 'u') {
//       c = '*';
//     }
//     // if (c >= '0' && c <= '9')
//     // {
//     //     c = '?';
//     // }
//     if (isdigit(c)) {
//       c = '?';
//     }
//     fprintf(stdout, "%c", c);
//   }

//   fclose(fp);
//   return 0;

//   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!learn this for the
//   //!exam!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (or use cppreference)
// }

int main(int argc, char *argv[]) {
  FILE *fp;
  if (fp == NULL) {
    return 1;
  }

  char shape[1024];
  double a, b, area;

  while ((fscanf(fp, "%1023s", shape)) == 1) {
    if (fscanf(fp, "%lf %lf", &a, &b) == 2) {
      if ((strcmp(shape, "rectangle")) == 0) {
        area = a * b;
      }
    }

    else if (fscanf(fp, "%lf %lf", &a, &b) == 1) {
      if ((strcmp(shape, "circle")) == 0) {
        area = M_PI * pow(a, 2);
      } else if ((strcmp(shape, "square")) == 0) {
        area = pow(a, 2);
      }
    }
  }