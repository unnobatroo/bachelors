#pragma once

#include <vector>

/// Singly linked list node.
struct SNode { int num; SNode* next; };

/// Doubly linked list node.
struct DNode { int num; DNode* next; DNode* prev; };

/**
 * Build a singly linked list with a dummy header node (H1L).
 * @param vals  element values, in list order
 * @return pointer to the header node. `head->num` is unused;
 *         the first real element is `head->next`.
 *         Caller owns the list — release with freeSList.
 */
SNode* makeSList(const std::vector<int>& vals);

/**
 * Build a doubly linked list with a dummy header node.
 * Same ownership and header conventions as makeSList.
 * Release with freeDList.
 */
DNode* makeDList(const std::vector<int>& vals);

/**
 * Build a circular singly linked list: the last node's `next`
 * points back to the header (so `head` is also the loop's end marker).
 * Release with freeCSList.
 */
SNode* makeCSList(const std::vector<int>& vals);

/**
 * Build a circular doubly linked list: `tail->next == head`
 * and `head->prev == tail`. Release with freeCDList.
 */
DNode* makeCDList(const std::vector<int>& vals);

/// Free a linear singly linked list, header included.
void freeSList(SNode* head);

/// Free a linear doubly linked list, header included.
void freeDList(DNode* head);

/// Free a circular singly linked list built by makeCSList.
void freeCSList(SNode* head);

/// Free a circular doubly linked list built by makeCDList.
void freeCDList(DNode* head);

/**
 * Print the elements after the header, space-separated.
 * Stops at nullptr or back at the header, so it works for
 * both linear and circular singly linked lists.
 */
void printSList(const SNode* head);

/// Like printSList, for doubly linked lists.
void printDList(const DNode* head);

/**
 * Generate n random ints in [lo, hi].
 * Feed the result straight into any make* function:
 * `makeSList(randomInts(10))`.
 */
std::vector<int> randomInts(int n, int lo = -100, int hi = 100);
