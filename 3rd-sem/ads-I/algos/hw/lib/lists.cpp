#include "lists.h"

#include <iostream>
#include <random>

SList::SList(const std::vector<int>& vals, bool circular) {
  head = new SNode{0, nullptr};
  SNode* tail = head;
  for (int v : vals) {
    tail->next = new SNode{v, nullptr};
    tail = tail->next;
  }
  if (circular) {
    tail->next = head;  // close the loop
  }
}

// One loop covers both kinds: linear ends at nullptr, circular back at head.
SList::~SList() {
  SNode* c = head->next;
  while (c != nullptr && c != head) {
    SNode* next = c->next;
    delete c;
    c = next;
  }
  delete head;
}

void SList::print() const {
  for (const SNode* c = first(); c != nullptr && c != head; c = c->next) {
    std::cout << c->num << ' ';
  }
  std::cout << '\n';
}

DList::DList(const std::vector<int>& vals, bool circular) {
  head = new DNode{0, nullptr, nullptr};
  DNode* tail = head;
  for (int v : vals) {
    tail->next = new DNode{v, nullptr, tail};
    tail = tail->next;
  }
  if (circular) {
    tail->next = head;  // close the loop both ways
    head->prev = tail;
  }
}

DList::~DList() {
  DNode* c = head->next;
  while (c != nullptr && c != head) {
    DNode* next = c->next;
    delete c;
    c = next;
  }
  delete head;
}

void DList::print() const {
  for (const DNode* c = first(); c != nullptr && c != head; c = c->next) {
    std::cout << c->num << ' ';
  }
  std::cout << '\n';
}

std::vector<int> randomInts(int n, int lo, int hi) {
  static std::mt19937 rng{std::random_device{}()};
  std::uniform_int_distribution<int> dist(lo, hi);
  std::vector<int> v(n);
  for (int& x : v) {
    x = dist(rng);
  }
  return v;
}
