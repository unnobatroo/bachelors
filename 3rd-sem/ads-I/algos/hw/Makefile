CXX = clang++
CXXFLAGS = -Wall -Wextra -std=c++17
# Optimized build: make CXXFLAGS="-Wall -Wextra -std=c++17 -O2" <name>

# Your exercises — add new names here and they get a build rule for free.
EXERCISES = findSecondLargest maxSubSegSum

.PHONY: all clean help

all: $(EXERCISES)

# Static pattern rule: gives every name in EXERCISES a real target,
# which is also what makes shell autocomplete work.
$(EXERCISES): %: %.cpp lib/lists.cpp
	$(CXX) $(CXXFLAGS) $^ -o $@

# `make run-findSecondLargest` builds (if needed) then runs it.
run-%: %
	./$<

clean:
	rm -f $(EXERCISES)

help:
	@echo "make all          build all exercises"
	@echo "make <name>       build one exercise"
	@echo "make run-<name>   build and run it"
	@echo "make clean        delete built binaries"
