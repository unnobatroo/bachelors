import module org.junit.jupiter;
import check.*;

import static check.Use.*;

@BeforeAll
public static void init() {
    Use.theClass("worker.schedule.WorkerSchedule")
            .that(hasUsualModifiers());
}

@Test
public void fieldWeekToWorkers() {
    it.hasField("weekToWorkers: HashMap of Integer to HashSet of String")
            .that(hasUsualModifiers())
            .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields())
            .that(hasUsualModifiers());
}

@Test
public void methodAdd01() {
    it.hasMethod("add", withParams("week: int", "workers: HashSet of String"))
            .that(hasUsualModifiers())
            .thatReturnsNothing();
}

@Test
public void methodAdd02() {
    it.hasMethod("add", withParams("weeks: HashSet of Integer", "workers: ArrayList of String"))
            .that(hasUsualModifiers())
            .thatReturnsNothing();
}

// returns whether a given person works on a given week
@Test
public void methodIsWorkingOnWeek() {
    it.hasMethod("isWorkingOnWeek", withParams("worker: String", "week: int"))
            .that(hasUsualModifiers())
            .thatReturns("boolean");
}

// returns a HashSet of all the weeks that the person’s working on
@Test
public void methodGetWorkWeeks() {
    // use the entrySet() method of HashMap to iterate through its elements
    // during the iteration, you’ll access Entry<key type, value type> values (java.util.Map.Entry).
    it.hasMethod("getWorkWeeks", withParams("worker: String"))
            .that(hasUsualModifiers())
            .thatReturns("HashSet of Integer");
}

void main() {
}


