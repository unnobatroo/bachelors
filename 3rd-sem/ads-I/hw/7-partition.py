import random


class Node:
    def __init__(self, value, next_node=None):
        self.value = value
        self.next_node = next_node

    def __iter__(self):
        curr = self
        while curr:
            yield curr.value
            curr = curr.next_node


def build_linked_list(numbers):
    head = None
    for num in reversed(numbers):
        head = Node(num, head)

    return head


def partition(head, pivot):
    l_dummy = l = Node(0)
    r_dummy = r = Node(0)

    while head:
        if head.value < pivot:
            l.next_node = l = head
        else:
            r.next_node = r = head
        head = head.next_node

    l.next_node = r_dummy.next_node
    r.next_node = None
    return l_dummy.next_node


if __name__ == '__main__':
    random.seed(42)

    numbers = [random.randint(1, 20) for _ in range(10)]
    first_node = build_linked_list(numbers)

    print("before:", list(first_node))
    print("after: ", list(partition(first_node, int(input("What should be the pivot element? ")))))
