#include <iostream>
#include <string>
#include <vector>

namespace progtech {

class Student {
private:
    std::string name;
    std::string nationality;
    double gpa;

public:
    // Constructors
    Student() : name(""), nationality(""), gpa(0.0) {}

    Student(const std::string& name, const std::string& nationality, double gpa)
        : name(name), nationality(nationality), gpa(gpa) {}

    // Getters
    std::string getName() const {
        return name;
    }

    std::string getNationality() const {
        return nationality;
    }

    double getGPA() const {
        return gpa;
    }

    // Setters
    void setName(const std::string& newName) {
        name = newName;
    }

    void setNationality(const std::string& newNationality) {
        nationality = newNationality;
    }

    void setGPA(double newGPA) {
        gpa = newGPA;
    }

    // Business Logic
    bool isScholar(double minGPA = 4.0) const {
        return gpa >= minGPA;
    }

    // Display
    void print() const {
        std::cout << "Name: " << name
                  << ", Nationality: " << nationality
                  << ", GPA: " << gpa << std::endl;
    }
};

class StudentGroup {
private:
    std::vector<Student> students;

public:
    // Add student to the group
    void addStudent(const Student& student) {
        students.push_back(student);
    }

    bool isEmpty() const {
        return students.empty();
    }

    const Student* getBestStudent() const {
        if (students.empty()) return nullptr;

        const Student* best = &students[0];
        for (size_t i = 1; i < students.size(); ++i) {
            if (students[i].getGPA() > best->getGPA()) {
                best = &students[i];
            }
        }
        return best;
    }

    const Student* getWorstStudent() const {
        if (students.empty()) return nullptr;

        const Student* worst = &students[0];
        for (size_t i = 1; i < students.size(); ++i) {
            if (students[i].getGPA() < worst->getGPA()) {
                worst = &students[i];
            }
        }
        return worst;
    }

    std::vector<Student> getScholars(double minGPA = 4.0) const {
        std::vector<Student> scholars;
        for (const Student& student : students) {
            if (student.isScholar(minGPA)) {
                scholars.push_back(student);
            }
        }
        return scholars;
    }

    void printBestAndWorst() const {
        if (isEmpty()) {
            std::cout << "The student list is empty." << std::endl;
            return;
        }

        const Student* best = getBestStudent();
        const Student* worst = getWorstStudent();

        std::cout << "--- Best Student ---" << std::endl;
        best->print();

        std::cout << "\n--- Worst Student ---" << std::endl;
        worst->print();
    }

    void printScholars(double minGPA = 4.0) const {
        std::cout << "\n--- Scholars (GPA >= " << minGPA << ") ---" << std::endl;

        std::vector<Student> scholars = getScholars(minGPA);
        if (scholars.empty()) {
            std::cout << "No scholars found." << std::endl;
            return;
        }

        for (const Student& scholar : scholars) {
            scholar.print();
        }
    }
};

} // namespace progtech

int main() {
    progtech::StudentGroup group;

    group.addStudent(progtech::Student("Alice", "Uzbek", 3.9));
    group.addStudent(progtech::Student("Bob", "German", 2.4));
    group.addStudent(progtech::Student("Charlie", "American", 3.2));
    group.addStudent(progtech::Student("Diana", "Korean", 4.0));
    group.addStudent(progtech::Student("Charlie", "American", 4.3));

    group.printBestAndWorst();
    group.printScholars();

    return 0;
}
