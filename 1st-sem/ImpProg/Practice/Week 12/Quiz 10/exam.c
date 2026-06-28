#include "exam.h"
#include <stdlib.h>
#include <string.h>

/* Write a function create_exam that allocates a new Exam instance on the heap
   using malloc() and initializes its fields with the given values. */

struct Exam *create_exam(const char *subject, int duration, ExamKind type) {
  struct Exam *new_exam = (struct Exam *)malloc(sizeof(struct Exam));

  if (new_exam == NULL) {
    return NULL;
  }

  strncpy(new_exam->subject, subject, 20);
  new_exam->subject[20] = '\0';
  new_exam->duration = duration;
  new_exam->type = type;

  return new_exam;
}