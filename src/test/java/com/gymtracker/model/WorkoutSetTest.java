package com.gymtracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class WorkoutSetTest {

    @Test
    void volumeIsWeightTimesReps() {
        assertEquals(600.0, new WorkoutSet(60, 10).volume());
    }

    @Test
    void bodyweightSetHasZeroWeight() {
        assertEquals(0.0, new WorkoutSet(0, 15).volume());
    }

    @Test
    void rejectsNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> new WorkoutSet(-5, 10));
    }

    @Test
    void rejectsZeroReps() {
        assertThrows(IllegalArgumentException.class, () -> new WorkoutSet(60, 0));
    }
}
