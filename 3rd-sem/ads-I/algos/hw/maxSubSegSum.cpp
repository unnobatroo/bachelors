#include <algorithm>
#include <chrono>
#include <climits>
#include <cstdlib>
#include <iostream>
#include <vector>

#include "lib/lists.h"

/// Largest sum of a non-empty contiguous segment (Kadane's).
/// One pass, O(n)/O(1). Empty -> 0; all-negative -> max element.
long long maxSubArray(const std::vector<int>& nums) {
  long long best = LLONG_MIN;
  long long curr = 0;

  for (int v : nums) {
    long long value = v;
    curr = std::max(value, curr + value);
    best = std::max(best, curr);
  }

  return nums.empty() ? 0 : best;
}

// Usage: ./maxSubSegSum <n> — runs on n random ints and prints the time.
int main(int argc, char* argv[]) {
  if (argc != 2) {
    return 1;
  }

  int n = std::atoi(argv[1]);
  auto data = randomInts(n);

  auto t0 = std::chrono::steady_clock::now();
  long long ans = maxSubArray(data);
  auto t1 = std::chrono::steady_clock::now();

  auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(t1 - t0).count();
  std::cout << n << " elements: " << ms << " ms -> " << ans << '\n';
}
