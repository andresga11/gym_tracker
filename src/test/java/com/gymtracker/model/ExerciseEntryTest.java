package com.gymtracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ExerciseEntryTest {

    private final Exercise bench = new Exercise("Bench Press", "Chest");

    @Test
    void totalVolumeAddsUpAllSets() {
        ExerciseEntry entry = new ExerciseEntry(bench);
        entry.addSet(new WorkoutSet(60, 10)); // 600
        entry.addSet(new WorkoutSet(70, 8));  // 560
        assertEquals(1160.0, entry.totalVolume());
    }

    @Test
    void bestSetIsHeaviestThenMostReps() {
        ExerciseEntry entry = new ExerciseEntry(bench);
        entry.addSet(new WorkoutSet(70, 6));
        entry.addSet(new WorkoutSet(75, 4));
        entry.addSet(new WorkoutSet(75, 5));
        assertEquals(new WorkoutSet(75, 5), entry.bestSet().orElseThrow());
    }

    @Test
    void bestSetIsEmptyWithNoSets() {
        assertTrue(new ExerciseEntry(bench).bestSet().isEmpty());
    }

    @Test
    void setsCannotBeModifiedFromOutside() {
        ExerciseEntry entry = new ExerciseEntry(bench);
        assertThrows(UnsupportedOperationException.class,
                () -> entry.getSets().add(new WorkoutSet(60, 10)));
    }
}
