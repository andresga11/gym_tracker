package com.gymtracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExerciseTest {

    @Test
    void trimsWhitespace() {
        Exercise exercise = new Exercise("  Bench Press ", " Chest ");
        assertEquals("Bench Press", exercise.name());
        assertEquals("Chest", exercise.category());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("  ", "Chest"));
    }

    @Test
    void rejectsBlankCategory() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("Squat", ""));
    }
}
