import random

random.seed(42)


class Node:
    def __init__(self, val, next_node=None):
        self.val = val
        self.next = next_node


    @staticmethod
    def create_ls(elems: list):
        if not elems: return None

        cls = Node
        head = curr = cls(elems[0])

        for x in elems[1:]:
            new_node = cls(x)
            curr.next = new_node
            curr = new_node

        return head


    def __str__(self):
        curr = self
        elements = []

        while curr:
            elements.append(str(curr.val))
            curr = curr.next

        return " -> ".join(elements) + " -> None"

def findSecondMax(head_ptr):
    if not head_ptr or not head_ptr.next: return None

    m1 = head_ptr.val
    m2 = head_ptr.next.val

    if m2 > m1: m1, m2 = m2, m1

    curr = head_ptr.next.next

    while curr is not None:
        val = curr.val

        if val > m1:
            m2 = m1
            m1 = val

        elif val > m2 and val != m1: m2 = val

        curr = curr.next

    return m2

ls = Node.create_ls(random.sample(range(1, 100), 10))

print(ls)

print(findSecondMax(ls))
