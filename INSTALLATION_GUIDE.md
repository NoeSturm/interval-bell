# Installation and Testing Guide for Interval Bell Android App

This guide will walk you through installing and testing the Interval Bell app on your Android phone.

## Prerequisites

### Option 1: Using Android Studio (Recommended for Development)
- **Android Studio** (latest version recommended)
- **Java Development Kit (JDK)** 8 or higher
- **Android SDK** (installed via Android Studio)
- **USB cable** to connect your phone to your computer
- **Android phone** running Android 7.0 (API 24) or higher

### Option 2: Direct APK Installation (Simpler)
- **Android phone** running Android 7.0 (API 24) or higher
- **File transfer method** (USB, email, cloud storage, etc.)

---

## Method 1: Install via Android Studio (For Development/Testing)

### Step 1: Set Up Your Phone for USB Debugging

1. **Enable Developer Options** on your phone:
   - Go to **Settings** → **About Phone**
   - Tap **Build Number** 7 times until you see "You are now a developer!"

2. **Enable USB Debugging**:
   - Go to **Settings** → **Developer Options**
   - Toggle on **USB Debugging**
   - (Optional) Enable **Install via USB** if available

3. **Connect Your Phone**:
   - Connect your phone to your computer via USB cable
   - On your phone, when prompted, tap **Allow USB Debugging** and check "Always allow from this computer"

### Step 2: Build and Install the App

1. **Open the Project**:
   ```bash
   # If you haven't already, checkout the project files
   git checkout origin/cursor/create-interval-bell-android-app-gemini-3-pro-preview-1cac
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Click **File** → **Open**
   - Navigate to the project directory and select it
   - Wait for Gradle sync to complete

3. **Verify Your Phone is Detected**:
   - In Android Studio, click the device dropdown (top toolbar)
   - Your phone should appear in the list
   - If not, check USB connection and ensure USB debugging is enabled

4. **Build and Run**:
   - Click the **Run** button (green play icon) or press `Shift + F10`
   - Android Studio will:
     - Build the APK
     - Install it on your phone
     - Launch the app automatically

### Step 3: Test the App

Once installed, you can test the app:

1. **Basic Functionality**:
   - Open the app on your phone
   - Enter an interval (e.g., 10 seconds for quick testing)
   - Select a bell sound
   - Tap **Start** and wait for the bell to ring
   - Verify the countdown timer is working
   - Tap **Stop** to stop the timer

2. **Background Operation**:
   - Start the timer
   - Press the home button or switch to another app
   - Wait for the interval to complete
   - Verify the bell still rings when the app is in the background

3. **Vibration**:
   - Start the timer
   - When the bell rings, verify your phone vibrates

4. **Different Bell Sounds**:
   - Test each of the 5 bell sounds to ensure they all work

---

## Method 2: Build APK and Install Manually

### Step 1: Build the APK

**Using Android Studio:**
1. Open the project in Android Studio
2. Go to **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
3. Wait for the build to complete
4. Click **locate** in the notification to find the APK file
5. The APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

**Using Command Line:**
```bash
# Navigate to project directory
cd /workspace

# Checkout the project files if needed
git checkout origin/cursor/create-interval-bell-android-app-gemini-3-pro-preview-1cac

# Make gradlew executable (if needed)
chmod +x gradlew

# Build the APK
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Step 2: Transfer APK to Your Phone

Choose one of these methods:

**Option A: USB Transfer**
1. Connect your phone to your computer via USB
2. Enable **File Transfer** mode on your phone (when USB connection notification appears)
3. Copy `app-debug.apk` to your phone's Downloads folder

**Option B: Email/Cloud Storage**
1. Upload the APK to Google Drive, Dropbox, or email it to yourself
2. Open the file on your phone and download it

**Option C: ADB Install (if USB debugging is enabled)**
```bash
# Connect phone via USB and run:
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Step 3: Install the APK on Your Phone

1. **Enable Unknown Sources** (if needed):
   - Go to **Settings** → **Security** (or **Apps** → **Special Access**)
   - Enable **Install Unknown Apps** or **Unknown Sources**
   - Select the app you'll use to install (File Manager, Chrome, etc.)

2. **Install the APK**:
   - Open your phone's **File Manager**
   - Navigate to where you saved the APK (Downloads folder, etc.)
   - Tap the APK file
   - Tap **Install**
   - Tap **Open** when installation completes

### Step 4: Test the App

Follow the same testing steps as described in Method 1, Step 3 above.

---

## Method 3: Using ADB Command Line (Advanced)

If you have ADB installed and your phone is connected:

```bash
# Build the APK
./gradlew assembleDebug

# Install directly to phone
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch the app
adb shell am start -n com.intervalbell.app/.MainActivity

# View logs (useful for debugging)
adb logcat | grep -i intervalbell
```

---

## Troubleshooting

### Phone Not Detected by Android Studio
- Check USB cable connection
- Try a different USB port
- Enable USB debugging again
- Install/update USB drivers for your phone model
- On some phones, you may need to change USB connection mode to "File Transfer" or "MTP"

### Build Errors
- Ensure you have the correct Android SDK version installed
- Sync Gradle files: **File** → **Sync Project with Gradle Files**
- Clean and rebuild: **Build** → **Clean Project**, then **Build** → **Rebuild Project**

### App Crashes or Doesn't Work
- Check that your phone meets the minimum requirement (Android 7.0+)
- Check logcat for error messages: **View** → **Tool Windows** → **Logcat**
- Ensure all permissions are granted (WAKE_LOCK and VIBRATE are usually auto-granted)

### APK Installation Blocked
- Enable "Install Unknown Apps" for your file manager or browser
- Some phones require you to allow installation from specific apps

---

## Quick Test Checklist

- [ ] App installs successfully
- [ ] App launches without crashing
- [ ] Can enter interval time
- [ ] Can select bell sound
- [ ] Timer starts when "Start" is tapped
- [ ] Countdown displays correctly
- [ ] Bell sound plays at interval
- [ ] Phone vibrates when bell rings
- [ ] Timer continues in background
- [ ] "Stop" button works correctly
- [ ] All 5 bell sounds work

---

## Additional Notes

- **First Launch**: The app may request permissions. Grant them if prompted.
- **Battery Optimization**: Some phones may kill the app in the background. You may need to disable battery optimization for this app in your phone's settings.
- **Testing Different Intervals**: Start with short intervals (5-10 seconds) for quick testing, then test longer intervals (1-5 minutes) to verify functionality.

---

## Need Help?

If you encounter issues:
1. Check the Android Studio Logcat for error messages
2. Verify your phone's Android version meets the minimum requirement (7.0+)
3. Ensure all prerequisites are installed correctly
4. Try uninstalling and reinstalling the app
