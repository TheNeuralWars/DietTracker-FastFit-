# DietTracker-FastFit

A simple, private-first Android app for NicoPez to track meals, calories, food quality, and mass gained/lost while practising intermittent and extended fasting.

## Features

- **Meal Tracking**: Log meals with name, calories, and quality rating (1-5)
- **Fasting Tracking**: Start/stop fasting sessions with support for multi-day fasts
- **Weight Tracking**: Record and monitor weight changes over time
- **Statistics & Trends**: View aggregated data including:
  - Total and average calories
  - Average meal quality
  - Fasting session history and averages
  - Weight change trends
- **CSV Export**: Export all data (meals, fasting sessions, weight entries) to CSV
- **Local-First**: All data stored locally using Room database - no internet required

## Technology Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Database**: Room (SQLite)
- **Architecture**: MVVM with Repository pattern
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Building the App

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 8 or higher
- Android SDK with API level 34

### Build Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/TheNeuralWars/DietTracker-FastFit-.git
   cd DietTracker-FastFit-
   ```

2. Build the app using Gradle wrapper:
   ```bash
   ./gradlew build
   ```

3. Install on connected device or emulator:
   ```bash
   ./gradlew installDebug
   ```

### Building with Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the cloned repository folder
4. Wait for Gradle sync to complete
5. Run the app using the green play button or press Shift+F10

## Project Structure

```
app/
├── src/main/java/com/nicopez/diettracker/
│   ├── data/
│   │   ├── entity/         # Room entities (Meal, FastingSession, WeightEntry)
│   │   ├── dao/            # Data Access Objects
│   │   ├── repository/     # Repository layer
│   │   └── DietTrackerDatabase.kt
│   ├── ui/
│   │   ├── screens/        # Compose UI screens
│   │   ├── viewmodel/      # ViewModels for each feature
│   │   ├── theme/          # Material 3 theme
│   │   └── utils/          # Utility classes (DateUtils, CsvExporter)
│   ├── DietTrackerApplication.kt
│   └── MainActivity.kt
└── src/main/res/           # Resources (strings, themes, etc.)
```

## Usage

### Adding a Meal
1. Navigate to the "Meals" tab
2. Tap the floating action button (+)
3. Enter meal name, calories, and quality rating (1-5)
4. Tap "Save"

### Starting a Fast
1. Navigate to the "Fasting" tab
2. Tap "Start Fast" button
3. The timer will start counting and update in real-time
4. Tap "Stop Fast" when you're done

### Recording Weight
1. Navigate to the "Weight" tab
2. Tap the floating action button (+)
3. Enter your weight in kilograms
4. Tap "Save"

### Viewing Trends
1. Navigate to the "Trends" tab
2. View aggregated statistics about meals, fasting, and weight
3. Tap "Export All Data to CSV" to share or backup your data

## Privacy

This app is completely private and local-first:
- All data is stored locally on your device using SQLite
- No internet connection required
- No data is sent to any servers
- No analytics or tracking
- Data export is only available via manual CSV export

## License

This project is private and intended for personal use by NicoPez.
