# WaitingLine

A Java waiting-line component following the OSU component discipline.
Supports FIFO service, cancellation, position lookup, priority changes,
and merging disjoint lines. Entries must be non-null and unique.

## Project structure

- `src/components/waitingline/`: interfaces, secondary class, and implementation.
- `src/HelpDeskDemo.java`: customer service example.
- `src/RoundRobinDemo.java`: players taking turns in a game.
- `test/components/waitingline/`: 30 JUnit tests.

## Setup

Use JDK 25 and place these dependencies in `lib/` (JAR files are ignored by Git):

- [components.jar](https://cse22x1.engineering.osu.edu/common/components.jar)
- [junit-4.13.2.jar](https://repo.maven.apache.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar)
- [hamcrest-core-1.3.jar](https://repo.maven.apache.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar)

## Documentation

- [Component design and test plan](doc/WAITING_LINE_DESIGN.md)
- [Generated JavaDoc](doc/javadoc/index.html) — download and open locally.
- [Assignment and reflection](doc/DIRECTIONS.md)

