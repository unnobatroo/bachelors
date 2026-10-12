#include "lists.h"

#include <iostream>
#include <random>

SNode* makeSList(const std::vector<int>& vals) {
  SNode* head = new SNode{0, nullptr};
  SNode* tail = head;
  for (int v : vals) {
    tail->next = new SNode{v, nullptr};
    tail = tail->next;
  }
  return head;
}

DNode* makeDList(const std::vector<int>& vals) {
  DNode* head = new DNode{0, nullptr, nullptr};
  DNode* tail = head;
  for (int v : vals) {
    tail->next = new DNode{v, nullptr, tail};
    tail = tail->next;
  }
  return head;
}

SNode* makeCSList(const std::vector<int>& vals) {
  SNode* head = new SNode{0, nullptr};
  SNode* tail = head;
  for (int v : vals) {
    tail->next = new SNode{v, nullptr};
    tail = tail->next;
  }
  tail->next = head;  // close the loop
  return head;
}

DNode* makeCDList(const std::vector<int>& vals) {
  DNode* head = new DNode{0, nullptr, nullptr};
  DNode* tail = head;
  for (int v : vals) {
    tail->next = new DNode{v, nullptr, tail};
    tail = tail->next;
  }
  tail->next = head;  // close the loop both ways
  head->prev = tail;
  return head;
}

void freeSList(SNode* head) {
  while (head) {
    SNode* next = head->next;
    delete head;
    head = next;
  }
}

void freeDList(DNode* head) {
  while (head) {
    DNode* next = head->next;
    delete head;
    head = next;
  }
}

// Circular frees: delete every node after the header, then the header.

void freeCSList(SNode* head) {
  SNode* c = head->next;
  while (c != head) {
    SNode* next = c->next;
    delete c;
    c = next;
  }
  delete head;
}

void freeCDList(DNode* head) {
  DNode* c = head->next;
  while (c != head) {
    DNode* next = c->next;
    delete c;
    c = next;
  }
  delete head;
}

void printSList(const SNode* head) {
  for (const SNode* c = head->next; c && c != head; c = c->next) {
    std::cout << c->num << ' ';
  }
  std::cout << '\n';
}

void printDList(const DNode* head) {
  for (const DNode* c = head->next; c && c != head; c = c->next) {
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
