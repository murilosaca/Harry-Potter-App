# Harry-Potter-App
An Android app (Kotlin) that consumes the Harry Potter API to browse characters, spells, Hogwarts houses, and staff.

Features


List and view details of characters
Browse spells and their descriptions
View students grouped by Hogwarts house
View Hogwarts staff list
Dashboard-style navigation between sections


Tech Stack


Kotlin
Android Views (Activities + XML layouts)
Retrofit (networking)
Coroutines (async calls)
MVVM-ish structure: data/model, data/remote, data/repository, ui/*


How to Run


Open the project in Android Studio.
Let Gradle sync and download dependencies.
Run on an emulator or physical device (min SDK as configured in app/build.gradle.kts).


Project Structure

app/src/main/java/com/example/harrypotterapp/
├── data/model        # Character, Spell data classes
├── data/remote        # Retrofit API service + client
├── data/repository     # Repository layer
└── ui/                # Activities per screen (dashboard, characters, spells, staff)
