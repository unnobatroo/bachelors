"""
Write a class called Student that has an __init__ method accepting a name and a courses list.
Fix the "mutable default trap" so that if no courses list is provided, the student gets
an empty list, but no two students accidentally share the same course list.
"""

class Student():
    def __init__(self, name, courses):
        self.name = name
        if courses is None:
            self.courses = []
        else:
            self.courses = courses

"""
Write a function increment_score() that adds 10 to a global variable score.
Make sure to use the correct keyword so Python doesn't throw an UnboundLocalError
or NameError when you try to modify it.
"""

score = 0

def increment_score():
    global score
    score += 10
    return score