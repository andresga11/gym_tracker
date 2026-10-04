package com.gymtracker.model;

/**
 * One set: a weight lifted for a number of reps, e.g. 60 kg x 10.
 * A weight of 0 means a bodyweight exercise (push-ups, pull-ups).
 */
public record WorkoutSet(double weight, int reps) {

    public WorkoutSet {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative: " + weight);
        }
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be at least 1: " + reps);
        }
    }

    /** Total weight moved in this set (weight x reps), a common measure of training work. */
    public double volume() {
        return weight * reps;
    }
}
