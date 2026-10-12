CXX = clang++
CXXFLAGS = -Wall -Wextra -std=c++17

# `make findSecondLargest` builds ./findSecondLargest from its .cpp + lib/.
# New exercises get this for free as long as the .cpp name matches.
%: %.cpp lib/lists.cpp
	$(CXX) $(CXXFLAGS) $^ -o $@

# Add new exercise names here as you create them.
clean:
	rm -f findSecondLargest maxSubSegSum
