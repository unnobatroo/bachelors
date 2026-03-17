import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("election.ElectionTest")
       .that(hasUsualModifiers());
}

@Test
public void defaultConstructor() {
    it.hasDefaultConstructor();
}

@Test
public void methodNoVotes() {
    it.hasMethod("noVotes", withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodSingleCandidate() {
    it.hasMethod("singleCandidate", withParams("candidate: election.candidate.Candidate", "voteCount: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodTwoCandidatesSameVoteCount() {
    it.hasMethod("twoCandidatesSameVoteCount", withParams("c1: election.candidate.Candidate", "c2: election.candidate.Candidate", "voteCount: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodCandidateVoteCount() {
    it.hasMethod("candidateVoteCount", withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodCandidateVoteCountAdvanced() {
    it.hasMethod("candidateVoteCountAdvanced", withParams("voteCount: int", "expectedCandidateNamesTxt: String"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodMakeCandidateNames() {
    it.hasMethod("makeCandidateNames", withParams("expectedCandidateNamesTxt: String"))
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturns("array of election.candidate.Candidate");
}

void main() {}


