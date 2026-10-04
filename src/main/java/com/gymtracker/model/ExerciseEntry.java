package com.gymtracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An exercise as performed in one workout: which exercise, plus the sets done.
 */
public class ExerciseEntry {

    private final Exercise exercise;
    private final List<WorkoutSet> sets = new ArrayList<>();

    public ExerciseEntry(Exercise exercise) {
        this.exercise = Objects.requireNonNull(exercise, "exercise must not be null");
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void addSet(WorkoutSet set) {
        sets.add(Objects.requireNonNull(set, "set must not be null"));
    }

    /** Read-only view, so callers must go through addSet() to change the list. */
    public List<WorkoutSet> getSets() {
        return Collections.unmodifiableList(sets);
    }

    public double totalVolume() {
        return sets.stream().mapToDouble(WorkoutSet::volume).sum();
    }

    /**
     * The heaviest set; if two sets share the top weight, the one with more reps.
     * Empty if no sets have been logged yet.
     */
    public Optional<WorkoutSet> bestSet() {
        return sets.stream()
                .max(Comparator.comparingDouble(WorkoutSet::weight)
                        .thenComparingInt(WorkoutSet::reps));
    }
}
