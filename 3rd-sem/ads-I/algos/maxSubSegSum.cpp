/*
Problem: Find the largest sum of a non-empty contiguous segment of integers.
Contiguous means adjacent elements: you cannot skip numbers inside a segment.
If all numbers are negative, return the largest one. Empty input returns 0 here.

Kadane's algorithm: one pass, O(n) time and O(1) extra space.
*/


#include <iostream>
#include <vector>

long long maxSubArray(const std::vector<int>& nums) {
    if (nums.empty()) {
        return 0;
    }

    long long curr_sum = nums[0];
    long long max_sum = nums[0];

    for (std::size_t i = 1; i < nums.size(); ++i) {
        const long long value = nums[i];
        curr_sum = std::max(value, curr_sum + value);
        max_sum = std::max(max_sum, curr_sum);
    }

    return max_sum;
}

int main() {
    const std::vector<int> arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    std::cout << "Max subarray sum: " << maxSubArray(arr); // 6.
    return 0;
}
