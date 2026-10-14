#include <algorithm>
#include <chrono>
#include <climits>
#include <iostream>
#include <vector>

#include "lib/lists.h"

// - Kadane's algorithm.
// - Empty input returns 0; otherwise the best segment may be all-negative.
long long maxSubArray(const std::vector<int>& nums) {
  long long best = LLONG_MIN;
  long long curr = 0;

  for (int v : nums) {
    long long value = v;
    curr = std::max(value, curr + value);  // extend or restart the segment
    best = std::max(best, curr);
  }

  return nums.empty() ? 0 : best;
}

int main() {
  const int n = 1'000'000;
  auto data = randomInts(n);

  auto t0 = std::chrono::steady_clock::now();
  long long ans = maxSubArray(data);
  auto t1 = std::chrono::steady_clock::now();

  auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(t1 - t0).count();
  std::cout << n << " elements: " << ms << " ms -> " << ans << '\n';
}
