#pragma once

#include <vector>

/// Singly linked list node.
struct SNode {
  int num;
  SNode* next;
};

/// Doubly linked list node.
struct DNode {
  int num;
  DNode* next;
  DNode* prev;
};

/// Singly linked list with a dummy header node.
/// First element: `header()->next`. Destructor frees all nodes.
///
///     SList list({4, 1, 7});   // linear
///     SList circ(vals, true);  // circular: tail links back to header
class SList {
public:
  /// Build from `vals` in order; `circular` links tail back to header.
  SList(const std::vector<int>& vals, bool circular = false);

  /// Free all nodes.
  ~SList();

  // Owning lists can't be copied (would double-free).
  SList(const SList&) = delete;
  SList& operator=(const SList&) = delete;

  /// The dummy header node.
  SNode* header() const { return head; }

  /// First real element, or nullptr if empty.
  SNode* first() const { return head->next; }

  /// Print elements space-separated.
  void print() const;

private:
  SNode* head;
};

/// Doubly linked list with a dummy header node.
/// Same API as SList; in circular mode `header()->prev` is the tail.
class DList {
public:
  /// Build from `vals` in order; `circular` links tail back to header.
  DList(const std::vector<int>& vals, bool circular = false);

  /// Free all nodes.
  ~DList();

  DList(const DList&) = delete;
  DList& operator=(const DList&) = delete;

  /// The dummy header node.
  DNode* header() const { return head; }

  /// First real element, or nullptr if empty.
  DNode* first() const { return head->next; }

  /// Print elements space-separated.
  void print() const;

private:
  DNode* head;
};

/// `n` random ints in `[lo, hi]`: `SList list(randomInts(10))`.
std::vector<int> randomInts(int n, int lo = -100, int hi = 100);
