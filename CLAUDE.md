# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android card game scoring application (Belote/Deberc) written in Kotlin. The app allows 2-4 players to track scores across multiple game sessions with persistent storage using Room database. It includes features like score history, customizable themes (Cyanea), ads (Google Play Services), and in-app review prompts.

## Build and Development Commands

### Building the app
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Clean build
./gradlew clean
```

### Running tests
```bash
# Run all tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Installing on device
```bash
# Install debug build
./gradlew installDebug

# Install release build
./gradlew installRelease
```

### Linting
```bash
# Run lint checks
./gradlew lint
```

## Architecture

### Core Structure
- **MainActivity**: Entry point that manages ViewPager with tab navigation between different game modes (2, 3, 4 player games). Uses coroutines for async operations (font setup, database initialization). Extends `CyaneaAppCompatActivity` for theming support.
- **Game Fragments**: Each game mode (2x2, 2, 3, 4 player) has its own fragment (Fragment2x2.kt, Fragment2.kt, etc.) with corresponding score tracking UI. Fragments handle player score input, score calculation, and bolt/penalty tracking.
- **Database Layer**: Room-based persistence with singleton pattern accessed via `DatabaseHelper.instance`

### Database Architecture
The app uses Room with the following key components:
- **Entities**: `GameEntity` (game metadata) and `GamerEntity` (player data) with foreign key relationship
- **Relation**: `GameWithGamers` embeds game with its players using `@Relation` annotation
- **DatabaseHelper**: Singleton pattern with `WeakReference` to context. Database name is "belote" with destructive migration enabled
- **Active/Inactive Games**: Games are marked as active (isActive = true) during play. When a new game starts, the previous game is marked inactive and added to history. Only one active game exists per game type at a time.
- **History Management**: The `addGameToInactive` function maintains a maximum of 10 inactive games in history, automatically deleting oldest games when limit is exceeded

### Key Design Patterns
- **Singleton**: DatabaseHelper provides single instance of Room database
- **Repository Pattern**: Dao interface provides data access methods with suspend functions for coroutine support
- **Fragment-Activity Communication**: Fragments access MainActivity's `gameWithGamers` property directly and call `saveGame()` to persist changes
- **Lifecycle-Aware Persistence**: Fragments save game state in `onPause()` and `onDestroy()`, loading in `onResume()`

### Configuration and Preferences
- **SharedPreferences**: Used for user settings (fonts, language, game type, bolt settings)
- **Onboarding**: First-time user experience managed via OnBoardingActivity with completion flag in preferences
- **Font Management**: Custom font support via AppFontManager reading from preferences
- **Language**: ContextWrapper handles locale changes based on language preference

### Key Dependencies
- Room (2.4.2): Database ORM with Kotlin coroutines and RxJava2 support
- Kotlin Coroutines: Async operations using lifecycleScope in activities/fragments
- Cyanea: Dynamic theming library
- Google Play Services Ads (24.4.0): Ad integration
- Firebase Core: Analytics and services
- Custom UI libraries: NumberPicker, DataTable, CalcDialog, CounterFab

## Development Notes

### Database Operations
- All database operations are suspend functions and must be called from a coroutine context (typically `lifecycleScope.launch(Dispatchers.IO)`)
- Use `dao.upsertByReplacementGame(gameWithGamers)` to save complete game state (handles both game and gamers)
- The `activeGameExists()` function is a blocking function (not suspend) used during initialization
- When creating a new game, old active games must be explicitly marked inactive via `addGameToInactive()`

### Game State Management
- Each fragment maintains references to MainActivity's `gameWithGamers` property
- Score updates modify the in-memory `gameWithGamers` object, then persist to database
- `gameScore` is a mutable list storing individual round scores as strings
- Game types are identified by string values: "1" (2x2), "2" (2-player), "3" (3-player), "4" (4-player)

### UI and Animations
- Custom animation utilities in utils package for text changes and view transitions
- Drag-and-drop support for score fields (OnDragListener)
- NumberPicker for game point selection (162-382 range)
- TextFieldBoxes for styled input fields
- CounterFab for bolt/penalty tracking (configurable in preferences)

### Configuration Details
- minSdkVersion: 23, targetSdkVersion: 36, compileSdk: 34
- Kotlin version: 1.6.21
- Android Gradle Plugin: 8.1.4
- Gradle version: 8.2 (required for AGP 8.1.4 compatibility - do not upgrade to Gradle 9.x)
- Java compatibility: VERSION_17
- Kotlin Android Extensions enabled (kotlinx synthetic properties)
- ProGuard disabled in release builds
- Supported languages: en, UA, es, fr, it, de, bg, pl, ru

**Important**: AGP 8.1.4 requires Gradle 8.0-8.2. Using Gradle 9.x will cause `NoSuchMethodError` due to removed APIs.

## Important Context

### Multi-Module Structure
The project includes a `:nativetemplates` module (referenced in settings.gradle) for native ad templates.

### Theme System
The app uses Cyanea for dynamic theming. Activities extend `CyaneaAppCompatActivity` and fragments extend `CyaneaFragment`. Theme changes persist across sessions.

### Coroutine Scope Best Practices
- Use `lifecycleScope.launch` for UI-related database operations
- Wrap database calls in `withContext(Dispatchers.IO)` when called from UI context
- Fragments should check `isAdded` and `view != null` before accessing views in coroutine callbacks