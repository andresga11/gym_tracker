# Log Workout Flowchart

This flowchart shows the **order of steps** the command-line menu follows when
you log a workout: which questions it asks, which decisions it makes, and where
it loops back. It is the plan for the "Log workout" option in `Menu` (step 4).

Unlike the [data flow diagram](data-flow-diagram.md), which shows *where data
goes*, a flowchart shows *what happens in which order*.

## Notation

| Symbol | Shape name | Meaning |
|---|---|---|
| `( START )` | Terminator (oval) | Where the flow begins or ends |
| `/ Ask: ... /` | Input/output (parallelogram) | The program asks the user something or shows them something |
| `[ ... ]` | Process (rectangle) | The program does some work |
| `< ... ? >` | Decision (diamond) | A yes/no question; the flow splits into two paths |
| `( A )` | Connector (small circle) | "Continue at the matching letter", so one long chart can be split into parts |
| `│ ▼ ──▶` | Flow line | The direction to follow |

The chart is split into four parts linked by connectors **A**, **B** and **C**.

## Part 1: Start the Workout

```
                ( START )
                    │
                    ▼
        / Ask: "Workout name?" / ◀──────────────────────┐
                    │                                   │
                    ▼                                   │
         < Is the name blank? > ──Yes──▶ / Show: "Name can't be empty" /
                    │
                    │ No
                    ▼
 [ Create Workout: name + today's date ]
                    │
                    ▼
                  ( A )
```

The date is filled in automatically with today's date, so there is one less
question to answer at the gym.

## Part 2: Choose an Exercise (A)

```
                  ( A )
                    │
                    ▼
     / Show exercises (0 = finish) / ◀────────────┐
                    │                             │
                    ▼                             │
       / Ask: "Exercise number?" /                │
                    │                             │
                    ▼                             │
              < Is it 0? > ──Yes──▶ ( C )         │
                    │                             │
                    │ No                          │
                    ▼                             │
            < On the list? > ──No──▶ / Show: "Not on the list" /
                    │
                    │ Yes
                    ▼
      [ Remember chosen exercise ]
                    │
                    ▼
                  ( B )
```

Exercises are picked by **number** from the saved list instead of typed by
name, which avoids typos like "Bench press" vs "bench Press". Typing text that
is not a number (e.g. `abc`) counts as "not on the list".

## Part 3: Log Sets (B)

```
                  ( B )
                    │
                    ▼
    / Ask: "Weight? (blank = done)" / ◀─────────────────────┐
                    │                                       │
                    ▼                                       │
            < Weight blank? > ──Yes──▶ ( A )                │
                    │                                       │
                    │ No                                    │
                    ▼                                       │
            / Ask: "Reps?" /                                │
                    │                                       │
                    ▼                                       │
   [ Create WorkoutSet(weight, reps) ]                      │
                    │                                       │
                    ▼                                       │
             < Valid set? > ──No──▶ / Show the error / ─────┤
                    │                                       │
                    │ Yes                                   │
                    ▼                                       │
         [ Add set to workout ]                             │
                    │                                       │
                    ▼                                       │
   / Show: "Set logged: 60 kg x 10" /                       │
                    │                                       │
                    └───────────────────────────────────────┘
```

- Pressing Enter on an empty weight means "done with this exercise" and goes
  back to the exercise list (A), so you can pick the next one.
- **Valid set?** covers two kinds of mistakes:
  - text that is not a number, e.g. `ten` (caught when converting the input), and
  - numbers that break the rules, e.g. `-5` kg or `0` reps (caught by the
    `WorkoutSet` constructor, which throws `IllegalArgumentException`).
- **Add set to workout** is `workout.addExercise(exercise).addSet(set)`.
  Because `addExercise` returns the existing entry if the exercise is already
  in the workout, calling it for every set is safe, and an exercise only gets
  an entry once its **first valid set** is logged. Picking an exercise and then
  logging nothing leaves no empty entry behind.

## Part 4: Finish (C)

```
                  ( C )
                    │
                    ▼
          < Any sets logged? > ──No──▶ / Show: "Nothing logged, not saved" / ──▶ ( END )
                    │
                    │ Yes
                    ▼
        [ Save workout to file ]
                    │
                    ▼
/ Show summary: exercises, sets, total volume /
                    │
                    ▼
                 ( END )
```

- An empty workout is not saved, so the history never fills up with blank
  sessions. Because entries are only created with a valid set (Part 3),
  "any sets logged?" is simply "does the workout have any entries?".
- The workout is saved **once, at the end**. This is simple, but if the program
  is closed mid-workout, the unsaved sets are lost. Saving after every set is a
  possible improvement later.

## Mapping to the Code

| In the flowchart | In the code |
|---|---|
| Asking and showing (parallelograms) | `Menu`, using `Scanner` and `System.out` |
| Creating the workout, adding sets, saving (rectangles) | `Tracker` methods, which use the model classes and `WorkoutStorage` |
| "Valid set?" | Number conversion in `Menu` + validation in the `WorkoutSet` constructor |
| Loops back to an earlier step | `while` loops in `Menu` |
