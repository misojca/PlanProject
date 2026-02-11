# Daily Plan Manager - Jetpack Compose

A modern Android application for managing daily plans, developed as a final project. The app focuses on state management, navigation, and seamless UI transitions using Jetpack Compose.

## Features

* **Plan Overview:** A comprehensive list of daily tasks displaying icons, titles, and current statuses.
* **Dynamic Filtering:** Filter plans by categories: `WORK`, `PERSONAL`, and `HEALTH`.
* **Detailed View:** Specific screen for each plan showing descriptions and action triggers.
* **State Management:** Real-time UI updates for task status (e.g., moving from *Planned* to *Active* or *Completed*).
* **Permission Handling:** Integrated runtime permission request for location services (Show Location action).
* **Animations:** Smooth UI transitions and an animated loading indicator for simulated data fetching.

## Details

* **Jetpack Compose:** Declarative UI toolkit.
* **MVVM Architecture:** Separation of concerns using `ViewModel` to handle business logic.
* **Compose Navigation:** Typed routing between the List and Details screens.
* **Kotlin Coroutines:** Used for simulating data loading delays.
* **State & Flow:** For reactive UI updates.
