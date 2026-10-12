#include <climits>
#include <iostream>

#include "lib/lists.h"

// - First elem is head->next.
// - Needs >= 2 real elements. Duplicates count: {5,5,3} -> 5.
int findSecondLargest(const SNode* head) {
  int max1 = INT_MIN;
  int max2 = INT_MIN;

  for (const SNode* c = head->next; c != nullptr; c = c->next) {
    if (c->num > max1) {
      max2 = max1;
      max1 = c->num;
    } else if (c->num > max2) {
      max2 = c->num;
    }
  }
  return max2;
}

int main() {
  SNode* h = makeSList(randomInts(10));
  printSList(h);

  std::cout << "second largest: " << findSecondLargest(h) << '\n';

  freeSList(h);
}
