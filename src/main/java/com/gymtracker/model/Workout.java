package com.gymtracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * One gym session: a date, a name (e.g. "Push Day"), and the exercises performed.
 */
public class Workout {

    private final LocalDate date;
    private final String name;
    private final List<ExerciseEntry> entries = new ArrayList<>();

    public Workout(LocalDate date, String name) {
        this.date = Objects.requireNonNull(date, "date must not be null");
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Workout name must not be empty");
        }
        this.name = name.trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    /**
     * Adds an exercise to this workout and returns its entry, ready for sets.
     * If the exercise is already in the workout, the existing entry is returned
     * instead, so all sets of one exercise stay together.
     */
    public ExerciseEntry addExercise(Exercise exercise) {
        for (ExerciseEntry entry : entries) {
            if (entry.getExercise().equals(exercise)) {
                return entry;
            }
        }
        ExerciseEntry entry = new ExerciseEntry(exercise);
        entries.add(entry);
        return entry;
    }

    public List<ExerciseEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public double totalVolume() {
        return entries.stream().mapToDouble(ExerciseEntry::totalVolume).sum();
    }
}
