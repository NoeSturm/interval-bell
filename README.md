# Interval Bell Android App

An Android application that allows users to set a time interval and receive bell notifications at regular intervals. Perfect for meditation, workouts, study sessions, or any activity that requires periodic reminders.

## Features

- **Customizable Interval**: Set any time interval in seconds
- **Multiple Bell Sounds**: Choose from 5 different bell tones:
  - Classic Bell
  - Church Bell
  - Alarm Bell
  - Chime Bell
  - Digital Bell
- **Visual Countdown**: See the time remaining until the next bell
- **Vibration Feedback**: Haptic feedback when the bell rings
- **Background Operation**: Timer continues running even when the app is in the background

## Requirements

- Android 7.0 (API level 24) or higher
- Android Studio or compatible IDE
- Gradle 8.2.0 or higher

## Building the App

1. Open the project in Android Studio
2. Sync Gradle files
3. Build the project (Build > Make Project)
4. Run on an emulator or physical device

## Usage

1. Enter the desired interval in seconds (e.g., 60 for 1 minute, 300 for 5 minutes)
2. Select a bell sound from the dropdown menu
3. Tap "Start" to begin the interval timer
4. The bell will ring at each interval
5. Tap "Stop" to stop the timer at any time

## Project Structure

```
IntervalBell/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/intervalbell/app/
│   │       │   └── MainActivity.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   └── values/
│   │       │       ├── strings.xml
│   │       │       ├── colors.xml
│   │       │       └── themes.xml
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Permissions

The app requires the following permissions:
- `WAKE_LOCK`: To keep the device awake during timer operation
- `VIBRATE`: To provide haptic feedback when the bell rings

## Technical Details

- **Language**: Java
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **UI Framework**: Material Design Components
- **Timer Implementation**: CountDownTimer
- **Audio Playback**: MediaPlayer with system notification sounds

## Future Enhancements

Potential improvements for future versions:
- Custom sound file support
- Multiple interval presets
- Statistics and history tracking
- Widget support for quick access
- Dark mode optimization

## License

This project is open source and available for modification and distribution.
