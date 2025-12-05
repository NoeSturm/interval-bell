# Detailed Guide: Test Interval Bell App on Your Phone (No Computer Required)

This guide provides **exact step-by-step instructions** to build, download, install, and test the app entirely from your phone.

---

## Prerequisites Checklist

Before you start, make sure you have:

- [ ] **Android phone** running Android 7.0 or higher (check in Settings → About Phone → Android Version)
- [ ] **Internet connection** on your phone (WiFi or mobile data)
- [ ] **Web browser** on your phone (Chrome, Firefox, Safari, etc.)
- [ ] **GitHub account** with access to this repository
- [ ] **File Manager app** on your phone (most Android phones have one built-in)
- [ ] **At least 50 MB** of free storage space

---

## PART 1: Set Up the Automated Build (One-Time Setup)

### Step 1: Commit and Push the Build Workflow

**If you haven't already pushed the workflow file to GitHub:**

1. **Open your phone's browser** (Chrome, Firefox, etc.)
2. **Navigate to GitHub.com** and log in to your account
3. **Go to your repository**: `https://github.com/YOUR_USERNAME/interval-bell`
   - Replace `YOUR_USERNAME` with your actual GitHub username
4. **Verify the workflow exists**:
   - Tap the **"Code"** tab (if you see it)
   - Navigate to `.github/workflows/build-apk.yml`
   - If the file doesn't exist, you'll need to add it (see note below)

**Note:** If the workflow file isn't in your repository yet, you'll need to either:
- Push it from a computer once, OR
- Use GitHub's web interface to create it (Settings → Actions → New workflow)

---

## PART 2: Build the APK (From Your Phone)

### Step 2: Navigate to GitHub Actions

1. **Open your phone's web browser**
2. **Go to**: `https://github.com/YOUR_USERNAME/interval-bell`
   - Replace `YOUR_USERNAME` with your GitHub username
3. **Tap the "Actions" tab** at the top of the page
   - If you don't see it, tap the **☰ menu icon** (three horizontal lines) first
4. **Look for "Build Android APK"** in the left sidebar or workflow list
   - If this is your first time, you might see a message about enabling workflows

### Step 3: Enable Workflows (If Needed)

**If you see a message about enabling workflows:**

1. **Tap "I understand my workflows, go ahead and enable them"** or similar button
2. This is a one-time setup

### Step 4: Trigger the Build

1. **Find the "Build Android APK" workflow** in the list
2. **Tap on it** to open the workflow page
3. **Look for the "Run workflow" button** on the right side of the page
   - It's usually a blue or green button
   - If you don't see it, scroll down or look for a dropdown menu
4. **Tap "Run workflow"**
5. **A dialog will appear** - tap the green **"Run workflow"** button again to confirm
6. **You'll see a yellow circle** ⚪ indicating the workflow is running

### Step 5: Wait for the Build to Complete

1. **The build will take 2-5 minutes**
2. **Refresh the page** periodically by pulling down or tapping the refresh button
3. **Watch for the status to change**:
   - ⚪ **Yellow circle** = Running
   - ❌ **Red X** = Failed (see troubleshooting below)
   - ✅ **Green checkmark** = Success!

4. **When you see a green checkmark**, tap on the workflow run to open it

---

## PART 3: Download the APK (From Your Phone)

### Step 6: Access the Built APK

1. **After the build completes** (green checkmark), **tap on the workflow run**
   - It will show something like "Build Android APK #1" or similar
2. **Scroll down** on the workflow run page
3. **Look for a section called "Artifacts"** near the bottom
   - It might be collapsed, so tap to expand it
4. **You'll see "interval-bell-app"** listed under Artifacts

### Step 7: Download the APK File

1. **Tap on "interval-bell-app"**
2. **Your browser will start downloading** the file
   - On Android, this usually goes to your **Downloads** folder
   - You might see a download notification
3. **Wait for the download to complete**
   - The file size should be around 2-5 MB
   - Check your notification bar for download progress

### Step 8: Locate the Downloaded File

**The file might be in one of these formats:**
- `interval-bell-app.zip` (needs to be extracted)
- `app-debug.apk` (ready to install)

**To find it:**

1. **Open your phone's File Manager app**
   - Common names: "Files", "My Files", "File Manager", "Downloads"
   - Usually found in your app drawer
2. **Navigate to "Downloads" folder**
   - Often in: Internal Storage → Downloads
   - Or: SD Card → Downloads (if you have one)
3. **Look for files named**:
   - `interval-bell-app`
   - `app-debug.apk`
   - Or any file downloaded in the last few minutes

**If you downloaded a ZIP file:**

1. **Tap on the ZIP file**
2. **Your phone should extract it automatically** or ask which app to use
3. **Choose "Files" or "Archive" app** if prompted
4. **After extraction, look for `app-debug.apk`** in the same folder

---

## PART 4: Install the APK (On Your Phone)

### Step 9: Enable Installation from Unknown Sources

**This is required because the app isn't from Google Play Store:**

1. **Open your phone's Settings app**
2. **Navigate to one of these paths** (varies by phone):
   - **Option A**: Settings → **Apps** → **Special Access** → **Install Unknown Apps**
   - **Option B**: Settings → **Security** → **Unknown Sources** (older Android versions)
   - **Option C**: Settings → **Privacy** → **Install Unknown Apps**
   - **Option D**: Settings → **Apps & Notifications** → **Special App Access** → **Install Unknown Apps**

3. **Find your File Manager app** in the list
   - This is the app you used to find the APK file
   - Common names: "Files", "My Files", "Samsung My Files", etc.

4. **Tap on your File Manager app**
5. **Toggle ON "Allow from this source"** or "Allow installing apps"
6. **Press the back button** to return

### Step 10: Install the APK

1. **Go back to your File Manager**
2. **Navigate to the Downloads folder** (where the APK is)
3. **Tap on `app-debug.apk`** (or the APK file you found)
4. **You'll see an installation screen** with app information:
   - App name: "Interval Bell"
   - Package: com.intervalbell.app
   - Permissions it will request

5. **Review the permissions** (the app needs):
   - Wake Lock (to keep timer running)
   - Vibration (for haptic feedback)

6. **Tap "Install"** button at the bottom
7. **Wait for installation** (usually 10-30 seconds)
8. **When complete, you'll see**:
   - "App installed" message, OR
   - "Open" button

9. **Tap "Open"** to launch the app immediately, OR
   - Tap "Done" and find the app in your app drawer later

---

## PART 5: Test the App (On Your Phone)

### Step 11: First Launch and Basic Setup

1. **Open the Interval Bell app**
   - Find it in your app drawer (all apps menu)
   - Or tap "Open" after installation
   - Icon should show a bell or timer

2. **On first launch, you might see**:
   - Permission requests (tap "Allow" if asked)
   - The main screen with interval input and bell selection

3. **Familiarize yourself with the interface**:
   - **Interval input field** (where you enter seconds)
   - **Bell sound dropdown** (to select bell type)
   - **Start button** (to begin timer)
   - **Stop button** (to stop timer)
   - **Countdown display** (shows time remaining)

### Step 12: Basic Functionality Test

**Test 1: Short Interval Test (Quick Verification)**

1. **Enter "10" in the interval field** (10 seconds for quick testing)
2. **Select any bell sound** from the dropdown (e.g., "Classic Bell")
3. **Tap the "Start" button**
4. **Observe**:
   - ✅ Countdown timer should start showing decreasing numbers
   - ✅ Timer should count down from 10 to 0
5. **Wait for the bell to ring** (after 10 seconds)
6. **Verify**:
   - ✅ Bell sound plays
   - ✅ Phone vibrates (if vibration is enabled)
   - ✅ Timer resets and starts counting again
7. **Tap "Stop"** to end the test

**Test 2: Different Bell Sounds**

1. **Start the timer** with a 15-second interval
2. **Let it ring once**
3. **Tap "Stop"**
4. **Change the bell sound** to a different option (e.g., "Church Bell")
5. **Start again** with 15 seconds
6. **Verify the new bell sound plays** when timer reaches 0
7. **Repeat for all 5 bell sounds**:
   - Classic Bell
   - Church Bell
   - Alarm Bell
   - Chime Bell
   - Digital Bell

**Test 3: Longer Interval Test**

1. **Enter "60" in the interval field** (1 minute)
2. **Select a bell sound**
3. **Tap "Start"**
4. **Verify the countdown shows minutes and seconds** (e.g., "0:59", "0:58", etc.)
5. **Wait for the full minute** (or at least verify it's counting correctly)
6. **Verify the bell rings after 60 seconds**
7. **Tap "Stop"**

**Test 4: Stop Functionality**

1. **Start a timer** with 30 seconds
2. **Wait 10 seconds** (let it count down a bit)
3. **Tap "Stop"**
4. **Verify**:
   - ✅ Timer stops immediately
   - ✅ Countdown resets or clears
   - ✅ No bell rings
   - ✅ You can start a new timer

### Step 13: Background Operation Test

**This is important - the app should work when you're not looking at it:**

1. **Start a timer** with a 20-second interval
2. **Press the Home button** (or swipe up to go home)
   - The app should minimize but keep running
3. **Open another app** (like Messages or Chrome)
4. **Wait for 20 seconds**
5. **Verify**:
   - ✅ Bell still rings even though app is in background
   - ✅ You might see a notification or hear the sound
   - ✅ Phone vibrates (if enabled)
6. **Return to the Interval Bell app**
7. **Verify the timer is still running** and counting correctly

**Test Background with Screen Off:**

1. **Start a timer** with 15 seconds
2. **Press the power button** to turn off the screen
3. **Wait 15 seconds**
4. **Verify the bell rings** even with screen off
5. **Turn screen back on** and check the app is still running

### Step 14: Edge Cases and Error Handling

**Test 1: Invalid Input**

1. **Try entering "0"** in the interval field
2. **Try entering a negative number** (if possible)
3. **Try entering text** instead of numbers
4. **Verify the app handles these gracefully** (shows error, prevents start, or uses default)

**Test 2: Very Long Interval**

1. **Enter "3600"** (1 hour) in the interval field
2. **Start the timer**
3. **Verify the countdown displays correctly** (shows hours:minutes:seconds)
4. **You don't need to wait the full hour** - just verify it's counting

**Test 3: Rapid Start/Stop**

1. **Start a timer**
2. **Immediately tap Stop**
3. **Start again**
4. **Stop again**
5. **Repeat several times**
6. **Verify the app doesn't crash** or behave strangely

**Test 4: App Switching**

1. **Start a timer** with 30 seconds
2. **Switch to another app**
3. **Switch back to Interval Bell**
4. **Verify the timer is still accurate** and running

### Step 15: Vibration Test

1. **Ensure your phone's vibration is enabled** (Settings → Sound → Vibration)
2. **Start a timer** with 10 seconds
3. **When the bell rings, verify**:
   - ✅ Phone vibrates
   - ✅ Vibration is noticeable but not too strong
4. **Test with different bell sounds** to ensure vibration works for all

### Step 16: Audio Test

1. **Ensure your phone's volume is turned up**
2. **Start a timer** with 10 seconds
3. **When the bell rings, verify**:
   - ✅ Sound plays clearly
   - ✅ Volume is appropriate
   - ✅ Sound doesn't cut off early
4. **Test each bell sound** to ensure they all play correctly
5. **Test with headphones** (if available) to verify audio quality

---

## PART 6: Complete Testing Checklist

Use this checklist to ensure you've tested everything:

### Installation & Setup
- [ ] APK downloaded successfully
- [ ] APK installed without errors
- [ ] App appears in app drawer
- [ ] App launches without crashing
- [ ] No permission errors on first launch

### Basic Functionality
- [ ] Can enter interval time
- [ ] Can select bell sound from dropdown
- [ ] Start button works
- [ ] Stop button works
- [ ] Countdown timer displays correctly
- [ ] Timer counts down accurately

### Bell Functionality
- [ ] Classic Bell sound works
- [ ] Church Bell sound works
- [ ] Alarm Bell sound works
- [ ] Chime Bell sound works
- [ ] Digital Bell sound works
- [ ] All sounds play at correct volume
- [ ] Sounds don't overlap or cut off

### Vibration
- [ ] Phone vibrates when bell rings
- [ ] Vibration intensity is appropriate
- [ ] Vibration works for all bell sounds

### Background Operation
- [ ] Timer continues when app is minimized
- [ ] Bell rings when app is in background
- [ ] Timer continues when screen is off
- [ ] Bell rings when screen is off
- [ ] Timer is accurate after returning to app

### User Interface
- [ ] All buttons are visible and accessible
- [ ] Text is readable
- [ ] Layout looks correct on your phone screen
- [ ] No UI elements are cut off
- [ ] App works in both portrait and landscape (if applicable)

### Error Handling
- [ ] App handles invalid input gracefully
- [ ] App doesn't crash on rapid start/stop
- [ ] App handles app switching correctly
- [ ] No memory leaks (app doesn't slow down over time)

---

## Troubleshooting Common Issues

### Issue: "Can't find the workflow in GitHub Actions"

**Solutions:**
- Make sure you're logged into GitHub
- Check that you're viewing the correct repository
- The workflow file (`.github/workflows/build-apk.yml`) must be in the repository
- Try refreshing the page
- If workflow doesn't exist, you may need to push it from a computer once

### Issue: "Build fails with errors"

**Solutions:**
- Check the workflow logs by tapping on the failed run
- Look for specific error messages
- Common issues:
  - Missing files in repository
  - Gradle version mismatch
  - Android SDK issues
- Try running the workflow again
- Make sure all project files are committed to the repository

### Issue: "Can't download the APK artifact"

**Solutions:**
- Make sure the build completed successfully (green checkmark)
- Try a different browser
- Check your internet connection
- Clear browser cache and try again
- The artifact is available for 30 days - make sure you're downloading within that time

### Issue: "Install blocked" or "Can't install APK"

**Solutions:**
- Enable "Install Unknown Apps" for your File Manager (see Step 9)
- Make sure you enabled it for the correct app (the one you're using to open the APK)
- Some phones require enabling this in multiple places
- Try a different file manager app
- Check that the APK file isn't corrupted (try downloading again)

### Issue: "App crashes on launch"

**Solutions:**
- Check that your phone is Android 7.0 or higher
- Uninstall any previous version of the app
- Clear app data: Settings → Apps → Interval Bell → Storage → Clear Data
- Reinstall the app
- Check the build logs for any errors

### Issue: "Bell doesn't ring" or "No sound"

**Solutions:**
- Check phone volume (not on silent/vibrate)
- Test with headphones to verify audio output
- Try a different bell sound
- Check app permissions (Settings → Apps → Interval Bell → Permissions)
- Restart the app
- Restart your phone

### Issue: "Timer doesn't work in background"

**Solutions:**
- Disable battery optimization for the app:
  - Settings → Apps → Interval Bell → Battery → Unrestricted
- Some phones have aggressive battery saver modes that kill background apps
- Check that "Wake Lock" permission is granted
- Try disabling battery saver mode temporarily

### Issue: "Vibration doesn't work"

**Solutions:**
- Check phone vibration settings (Settings → Sound → Vibration)
- Ensure phone is not in silent mode
- Check app permissions for vibration
- Test vibration with other apps to verify phone hardware works

### Issue: "Countdown is inaccurate"

**Solutions:**
- Test with short intervals first (10 seconds) to verify accuracy
- Some delay is normal, but should be within 1-2 seconds
- Check if other apps are slowing down your phone
- Close other apps and test again

---

## What to Report If Something Doesn't Work

If you encounter issues, note down:

1. **What you were trying to do** (e.g., "Start a 30-second timer")
2. **What actually happened** (e.g., "App crashed", "Bell didn't ring")
3. **Your phone model** (Settings → About Phone)
4. **Android version** (Settings → About Phone → Android Version)
5. **When it happened** (e.g., "After 2 minutes of running")
6. **Steps to reproduce** (exact steps that cause the issue)

---

## Success Criteria

Your testing is successful if:

✅ App installs without errors  
✅ App launches and shows the main screen  
✅ You can set an interval and start the timer  
✅ Countdown displays and counts down accurately  
✅ Bell rings at the correct interval  
✅ Phone vibrates when bell rings  
✅ Timer works when app is in background  
✅ All 5 bell sounds work  
✅ Stop button works correctly  
✅ App doesn't crash during normal use  

---

## Next Steps After Testing

Once you've completed testing:

1. **Note any bugs or issues** you found
2. **Test on different intervals** to ensure reliability
3. **Use the app for its intended purpose** (meditation, workouts, etc.)
4. **Provide feedback** on what works well and what could be improved

---

## Quick Reference: File Locations

- **APK Download Location**: Internal Storage → Downloads → `app-debug.apk`
- **App Installation Location**: System Apps (managed by Android)
- **Workflow File**: `.github/workflows/build-apk.yml` (in GitHub repository)
- **Build Artifacts**: GitHub Actions → Workflow Run → Artifacts section

---

## Time Estimates

- **Build APK**: 2-5 minutes
- **Download APK**: 30 seconds - 2 minutes (depending on internet speed)
- **Install APK**: 1-2 minutes
- **Basic Testing**: 5-10 minutes
- **Complete Testing**: 20-30 minutes

**Total Time**: Approximately 30-45 minutes for complete setup and testing

---

## Need More Help?

If you get stuck:

1. **Check the troubleshooting section** above
2. **Review the error messages** carefully
3. **Try the steps again** from the beginning
4. **Check GitHub Actions logs** for build errors
5. **Verify your phone meets requirements** (Android 7.0+)

---

**You're all set!** Follow these steps and you'll have the app running on your phone without ever needing a computer. 🎉
