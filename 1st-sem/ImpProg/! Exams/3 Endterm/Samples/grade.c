#include <stdbool.h>
#include <stdio.h>

#define MIN_LIMIT 10

/**
 * This function returns an integer in the range of [0, 20] based on the quiz
 * results.
 */
int pointsFromQuizzes() { return 17; }

/**
 * This function returns an integer in the range of [0, 20] based on the
 * student's activity during the semester.
 */
int pointsFromTeacher() { return 16; }

/**
 * This function returns a number in the range of [0, 60] based on the exam's
 * result.
 */
int pointsFromExam() { return 60; }

/**
 * This function returns true if the assignment is approved by the teacher.
 */
bool assignmentAccpeted() { return true; }

/**
 * This function returns true if the student who reads this program understands
 * its code.
 */
bool understandsThisCode(char *student) { return true; }

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("Usage: %s <student_name>\n", argv[0]);
    return 1;
  }

  char *student = argv[1];

  int quiz = pointsFromQuizzes();
  int teacher_points = pointsFromTeacher();
  int exam = pointsFromExam();
  bool assignment = assignmentAccpeted();

  int grade;

  if (quiz < MIN_LIMIT || teacher_points < MIN_LIMIT || exam < MIN_LIMIT ||
      !assignment) {
    grade = 1;
  } else {
    int sum = quiz + teacher_points + exam;

    if (sum < 51)
      grade = 1;
    else if (sum < 61)
      grade = 2;
    else if (sum < 71)
      grade = 3;
    else if (sum < 86)
      grade = 4;
    else
      grade = 5;
  }

  if (!understandsThisCode(student))
    grade = 1;

  printf("Final grade of %s is %d\n", student, grade);
}