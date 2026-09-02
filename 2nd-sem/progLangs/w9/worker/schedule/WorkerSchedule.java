package worker.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class WorkerSchedule {
    private final HashMap<Integer, HashSet<String>> weekToWorkers;

    public WorkerSchedule(HashMap<Integer, HashSet<String>> weekToWorkers) {
        this.weekToWorkers = weekToWorkers;
    }

    public void add(int week, HashSet<String> workers) {
        int w = week - 1;

        if (!weekToWorkers.containsKey(w)) {
            weekToWorkers.put(w, new HashSet<>());
        }

        weekToWorkers.get(w).addAll(workers);
    }

    public void add(HashSet<Integer> weeks, ArrayList<String> workers) {
        HashSet<String> workerSet = new HashSet<>(workers);

        for (int week : weeks) {
            this.add(week, workerSet);
        }
    }

    public boolean isWorkingOnWeek(String worker, int week) {
        int w = week - 1;
        if (!weekToWorkers.containsKey(w)) {
            return false;
        }
        return weekToWorkers.get(w).contains(worker);
    }

    public HashSet<Integer> getWorkWeeks(String worker) {
        HashSet<Integer> workWeeks = new HashSet<>();
        for (Map.Entry<Integer, HashSet<String>> entry : weekToWorkers.entrySet()) {
            if (entry.getValue().contains(worker)) {
                workWeeks.add(entry.getKey() + 1);
            }
        }
        return workWeeks;
    }
}
