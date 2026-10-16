#include <chrono>
#include <climits>
#include <cstdlib>
#include <iostream>

#include "lib/lists.h"

/// Second largest element of an H1L.
/// `head` is the dummy node; first element is `head->next`.
/// One pass, O(n)/O(1). Needs >= 2 elements. Duplicates count: {5,5,3} -> 5.
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

// Usage: ./findSecondLargest <n> — builds a random list of n ints.
int main(int argc, char* argv[]) {
  if (argc != 2) {
    return 1;
  }

  int n = std::atoi(argv[1]);
  SList list(randomInts(n));

  auto t0 = std::chrono::steady_clock::now();
  int ans = findSecondLargest(list.header());
  auto t1 = std::chrono::steady_clock::now();

  auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(t1 - t0).count();
  std::cout << n << " elements: " << ms << " ms -> " << ans << '\n';
}
