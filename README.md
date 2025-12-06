# Interval Bell Android App

An Android application that allows users to set a time interval and receive bell notifications at regular intervals. Perfect for meditation, workouts, study sessions, or any activity that requires periodic reminders.

## Features

- **Google Timer-Style Time Input**: Enter time intervals using an intuitive numeric keypad
  - Digits shift from right to left as you type (just like Google Timer)
  - Display shows hours, minutes, and seconds (HH:MM:SS format)
  - "00" button for quick double-zero entry
  - Backspace button to delete last digit (long press to clear all)
- **Customizable Interval**: Set any time interval up to 99h 59m 59s
- **Multiple Bell Sounds**: Choose from 5 different bell tones:
  - Classic Bell
  - Church Bell
  - Alarm Bell
  - Chime Bell
  - Digital Bell
- **Visual Countdown**: See the time remaining until the next bell (in HH:MM:SS format)
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

1. Use the numeric keypad to enter your desired time interval:
   - Tap digits 0-9 to enter time (digits shift left like a calculator)
   - Use "00" for quick double-zero entry
   - Use "⌫" to delete the last digit (long press to clear all)
   - Time displays as **HHh MMm SSs** (e.g., type "130" for 1 minute 30 seconds)
2. Select a bell sound from the dropdown menu
3. Tap "Start" to begin the interval timer
4. The countdown displays remaining time in HH:MM:SS format
5. The bell will ring at each interval and restart automatically
6. Tap "Stop" to stop the timer at any time

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
