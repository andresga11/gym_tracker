package com.gymtracker.model;

/**
 * The definition of an exercise, e.g. "Bench Press" in category "Chest".
 * This says nothing about when or how it was performed; that is ExerciseEntry's job.
 */
public record Exercise(String name, String category) {

    // A "compact constructor": runs before the fields are set, so we can
    // reject bad input and clean up whitespace.
    public Exercise {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Exercise name must not be empty");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Exercise category must not be empty");
        }
        name = name.trim();
        category = category.trim();
    }
}
