class C2LNode:

    def __init__(self, key=None):
        self.key = key
        self.prev = self
        self.next = self


class C2L:

    def __init__(self, iterable=None):
        self.head = C2LNode(None)
        if iterable:
            for val in iterable:
                self.append(val)

    def append(self, key):
        q = C2LNode(key)
        p = self.head.prev
        q.prev, q.next = p, self.head
        p.next = self.head.prev = q

    def __iter__(self):
        curr = self.head.next
        while curr != self.head:
            yield curr.key
            curr = curr.next

    def nodes(self):
        curr = self.head.next
        while curr != self.head:
            yield curr
            curr = curr.next


def c2l_intersection(L1: C2L, L2: C2L) -> C2L:
    result = C2L()

    gen1 = L1.nodes()
    gen2 = L2.nodes()

    node1 = next(gen1, None)
    node2 = next(gen2, None)

    while node1 is not None and node2 is not None:
        if node1.key == node2.key:
            result.append(node1.key)
            node1 = next(gen1, None)
            node2 = next(gen2, None)
        elif node1.key < node2.key:
            node1 = next(gen1, None)
        else:
            node2 = next(gen2, None)

    return result


if __name__ == "__main__":
    L1 = C2L(range(11, 18))
    L2 = C2L([12, 14, 15] + list(range(17, 22)))

    print("L1 init: ", list(L1))
    print("L2 init: ", list(L2))

    L_res = c2l_intersection(L1, L2)

    print("Delta C2L: ", list(L_res))
    print("L1 stayed: ", list(L1) == list(range(11, 18)))
    print(
        "L2 stayed: ",
        list(L2) == [12, 14, 15] + list(range(17, 22)),
    )
