package com.gymtracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class WorkoutTest {

    private final Exercise bench = new Exercise("Bench Press", "Chest");
    private final Exercise press = new Exercise("Overhead Press", "Shoulders");

    @Test
    void addingSameExerciseTwiceReusesEntry() {
        Workout workout = new Workout(LocalDate.of(2026, 10, 4), "Push Day");
        ExerciseEntry first = workout.addExercise(bench);
        ExerciseEntry second = workout.addExercise(bench);

        assertSame(first, second);
        assertEquals(1, workout.getEntries().size());
    }

    @Test
    void totalVolumeAddsUpAllExercises() {
        Workout workout = new Workout(LocalDate.of(2026, 10, 4), "Push Day");
        workout.addExercise(bench).addSet(new WorkoutSet(60, 10)); // 600
        workout.addExercise(press).addSet(new WorkoutSet(40, 10)); // 400
        assertEquals(1000.0, workout.totalVolume());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Workout(LocalDate.of(2026, 10, 4), " "));
    }
}
