# Roadmap

The phases and steps for building the Gym Tracker, and where the project
currently stands.

| Status | Meaning |
|---|---|
| ✅ | Done |
| 🔵 | In progress |
| ⬜ | Not started |

```
 PHASE 0: PLANNING                          ✅ done
 PHASE 1: BUILD VERSION 1                   🔵 in progress (step 1 of 4 done)
 PHASE 2: USE IT FOR REAL                   ⬜ not started
 PHASE 3: VERSION 2 FEATURES                ⬜ not started
```

## Phase 0: Planning ✅

Decide what to build before writing code.

| Task | Status | Where |
|---|---|---|
| Features, data, classes, layers and build order | ✅ | This roadmap |
| Tools: Java 21, Maven, command line, JUnit 5 | ✅ | `pom.xml` |
| ER diagram (what data exists) | ✅ | Not saved to a file yet |
| Data flow diagram (where data moves) | ✅ | [data-flow-diagram.md](data-flow-diagram.md) |
| Flowchart for "Log workout" (order of steps) | ✅ | [log-workout-flowchart.md](log-workout-flowchart.md) |

## Phase 1: Build Version 1 🔵

Built from the inside out, so each layer is tested before the next one
depends on it.

| Step | What | Package | Status |
|---|---|---|---|
| 1 | Model classes: `Exercise`, `WorkoutSet`, `ExerciseEntry`, `Workout`, plus tests | `model` | ✅ |
| 2 | `WorkoutStorage`: save and load workouts as JSON (Gson) | `storage` | ⬜ next |
| 3 | `Tracker`: exercise list, logging sets, history | `service` | ⬜ |
| 4 | `Menu` and `Main`: the command-line interface, following the flowchart | `cli` | ⬜ |

When step 4 is done, the app works from start to finish.

### Step 2 in detail

- Add the Gson library to `pom.xml`
- Write `WorkoutStorage` with `save()` and `load()`
- Teach Gson to read and write `LocalDate`
- Test that a saved workout loads back identical

## Phase 2: Use It for Real ⬜

Use the app at the gym for about a week and note what is annoying or missing.
That feedback decides what goes into Phase 3.

## Phase 3: Version 2 Features ⬜

Candidates, in suggested order:

1. Personal records (best set per exercise)
2. Progress over time for one exercise
3. Workout templates (e.g. "Push Day")
4. Stats: volume per week, workouts per week
5. A web interface (only the `cli` layer would need replacing)

## Known Improvements

Ideas noted along the way, to revisit later:

- Treat exercise names case-insensitively ("bench press" = "Bench Press"); planned for step 3
- Give workouts an ID so two sessions with the same name and date can be told apart; needed for step 2
- Save after every set instead of only at the end of a workout
