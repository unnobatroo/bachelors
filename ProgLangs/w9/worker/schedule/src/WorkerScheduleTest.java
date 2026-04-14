package worker.schedule.src;

import org.junit.jupiter.api.Test;
import worker.schedule.WorkerSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkerScheduleTest {
    @Test
    public void emptySchedule() {
        WorkerSchedule schedule = new WorkerSchedule(new HashMap<>());
        assertFalse(schedule.isWorkingOnWeek("Jalol", -1));
        assertTrue(schedule.getWorkWeeks("Jalol").isEmpty());
    }

    @Test
    public void schedule() {
        WorkerSchedule schedule = new WorkerSchedule(new HashMap<>());

        HashSet<String> teamA = new HashSet<>(List.of("Jalol", "Ismail"));
        schedule.add(1, teamA);

        HashSet<Integer> weeks = new HashSet<>(List.of(2, 3));
        ArrayList<String> teamB = new ArrayList<>(List.of("Jalol", "Ismail"));
        schedule.add(weeks, teamB);

        assertTrue(schedule.isWorkingOnWeek("Jalol", 1));
        assertTrue(schedule.isWorkingOnWeek("Jalol", 2));
        assertTrue(schedule.isWorkingOnWeek("Ismail", 3));
        assertFalse(schedule.isWorkingOnWeek("Javohir", 2));

        HashSet<Integer> aliceWeeks = schedule.getWorkWeeks("Jalol");
        assertEquals(new HashSet<>(List.of(1, 2, 3)), aliceWeeks);
    }
}
