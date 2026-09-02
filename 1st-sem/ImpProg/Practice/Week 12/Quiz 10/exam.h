#ifndef EXAM_H
#define EXAM_H

typedef enum { Written, Oral } ExamKind;

struct Exam {
  char subject[21];
  int duration;
  ExamKind type;
};

struct Exam *create_exam(const char *subject, int duration, ExamKind type);

#endif // EXAM_H