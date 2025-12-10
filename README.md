# DietTracker-FastFit

A simple, private-first Android app for NicoPez to track meals, calories, food quality, and mass gained/lost while practising intermittent and extended fasting. The app will be designed for minimal daily friction so it fits with work, family, and financial constraints.

## Quick Start

See [README_BUILD.md](README_BUILD.md) for detailed build instructions and feature documentation.

## Features

✅ **Meal Tracking** - Log meals with calories and quality rating (1-5)  
✅ **Fasting Tracking** - Start/stop fasting with multi-day support  
✅ **Weight Tracking** - Record weight changes over time  
✅ **Trends & Statistics** - View aggregated data and insights  
✅ **CSV Export** - Export all data for backup or analysis  
✅ **100% Local & Private** - No internet, no tracking, no data collection

## Tech Stack

- Kotlin + Jetpack Compose (Material 3)
- Room Database (local SQLite)
- MVVM Architecture with Repository pattern
- Min SDK 26 (Android 8.0) / Target SDK 34 (Android 14)

## Development

Built with Android Studio and Gradle. The app follows Android best practices with:
- Clean architecture (data/ui separation)
- StateFlow for reactive data
- Coroutines for async operations
- Material Design 3 theming
