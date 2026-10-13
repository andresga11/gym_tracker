# Data Flow Diagram

This diagram shows how data moves through the Gym Tracker: where it comes from,
where it goes, and what changes it along the way. It describes **version 1 as
planned** (CLI menu, `Tracker`, JSON storage).

## Notation

| Shape | Name | Meaning |
|---|---|---|
| `┌──────┐` square box | **External entity** | Someone outside the system who sends or receives data |
| `╭──────╮` rounded box, numbered | **Process** | Takes data in, does work, sends data out (named with a verb) |
| `┌──┬─────` open-ended box | **Data store** | Where data rests until needed (named with a noun) |
| `───▶` labeled arrow | **Data flow** | Data moving from one place to another |

`USER*` means the same user drawn again, so arrows don't cross.

## Level 0: Context Diagram

The whole app as a single process: what goes in, what comes out.

```
                 exercises, workouts, sets, history requests
 ┌────────┐                                                 ╭──────────────────╮
 │  USER  │ ──────────────────────────────────────────────▶ │ 0                │
 │        │ ◀────────────────────────────────────────────── │ Gym Tracker      │
 └────────┘                                                 ╰──────────────────╯
                 exercise list, workout history, errors
```

## Level 1: Inside the Gym Tracker

```
 ┌────────┐   name + category    ╭──────────────────╮   new exercise     ┌────┬────────────────
 │  USER  │ ──────────────────▶  │ 1.0              │ ─────────────────▶ │ D1 │ Exercise list
 │        │ ◀──────────────────  │ Manage exercises │ ◀───────────────── └────┴────────────────
 └────────┘  exercise list,      ╰──────────────────╯   existing           │
              errors                                    exercises          │ exercise list
                                                                           │ (to pick from)
                                                                           │
 ┌────────┐  workout name,       ╭──────────────────╮                      │
 │  USER* │  exercise choice,    │ 2.0              │ ◀────────────────────┘
 │        │  weight + reps ───▶  │ Log workout      │   completed workout┌────┬────────────────
 │        │ ◀──────────────────  │                  │ ─────────────────▶ │ D2 │ Workout history
 └────────┘  confirmation,       ╰──────────────────╯                    └────┴────────────────
              errors                                                       │
                                                                           │
 ┌────────┐   history request    ╭──────────────────╮   past workouts      │
 │  USER* │ ──────────────────▶  │ 3.0              │ ◀────────────────────┘
 │        │ ◀──────────────────  │ View history     │
 └────────┘  workout summaries   ╰──────────────────╯
              + volumes
```

### Processes

- **1.0 Manage exercises**: receives a name and category, checks D1 for
  duplicates (ignoring capitalization), and saves new exercises to D1.
- **2.0 Log workout**: offers exercises from D1 to pick from, validates each
  set (no negative weight, at least 1 rep), and saves the completed workout to D2.
- **3.0 View history**: reads past workouts from D2 and calculates volumes and
  best sets for display. Volumes are derived, never stored.

## Rules This Design Follows

| DFD rule | What it means in the code |
|---|---|
| A user never connects directly to a data store | The menu never touches the JSON file; everything goes through the `Tracker` |
| A store never connects directly to another store | Data is only moved by code that runs (a process) |
| Every process has both input and output | Every `Tracker` method takes something in and returns or saves something |
| Stored data only changes through a process | Validation lives in one place, so bad data can't reach disk |

## Mapping to the Code

| In the diagram | In the code |
|---|---|
| Arrows between USER and processes | `Menu` (reads input with `Scanner`, prints results) |
| Processes 1.0, 2.0, 3.0 | `Tracker` methods |
| Data stores D1, D2 | `WorkoutStorage` and the JSON file |
| Data travelling on the arrows | Model classes: `Exercise`, `Workout`, `ExerciseEntry`, `WorkoutSet` |
