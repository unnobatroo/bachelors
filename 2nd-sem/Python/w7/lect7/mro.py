class A:
    def greet(self):
        print("A")
        super().greet()


class B:
    def greet(self):
        print("B")
        super().greet()


class Root:
    def greet(self):
        print("Root")


class C(A, B, Root):
    pass


C().greet()
