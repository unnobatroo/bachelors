#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define STUD_CNT 10

/*
struct Student {
    short age;     // 2
    int id;        // 4
    double grade;  // 8
};
typedef struct Student Student;
*/

typedef struct Student {
    short age;
    int id;
    double grade; // 1.0 - 5.0 incremented by .1 (1.0, 1.1, ..., 4.9, 5.0)
} Student;

#include <stdlib.h>
#include <stdio.h>

typedef enum Type { BSc, MSc, PhD } Type;

typedef union StudentExtraData {
  int bsc_courses;
  double msc_avg;
  struct {
    double impact_factor;
    int erdos_number;
  } phd_info;
} StudentExtraData;

typedef struct AdvStudent {
  short age;
  int id;
  double grade;
  Type type;
  StudentExtraData extra_data;
} AdvStudent;

typedef int foo; // type alias

Student* student_init() {
    Student *student = malloc(sizeof(Student));
    // age: 18 - 25
    if (student == NULL)
        return NULL;

    // (*student).age = 18 + rand() % 8;
    student->age = 18 + rand() % 8;
    // [1.0 .. 5.0] -> [10..50]
    student->grade = (10 + rand() % 41) / 10.0;
    student->id = rand();
    return student;
}

AdvStudent* adv_student_init() {
    Student *new_student = malloc(sizeof(AdvStudent));
    if (new_student == NULL)
        return NULL;

    new_student->age = 18 + rand() % 8;
    // [1.0 .. 5.0] -> [10..50]
    new_student->grade = (10 + rand() % 41) / 10.0;
    new_student->id = rand();
    return new_student;
}

void print_student(Student *student) {
    printf("ID: %d\tage: %d\tgrade: %f\n", student->id, student->age, student->grade);
}

int main() {
    srand(time(NULL));

    printf("%zu\n", sizeof(Student));

    Student *s = student_init();
    print_student(s);
    free(s);

    Student s2 = { .age = 20, .id = 42, .grade = 5.0 };
    // foo something = 42;
}

union NumberOrString {
    int num;
    char *str;
};

enum NumOrStringType {
    NUM,
    STR,
};