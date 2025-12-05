# Step-by-Step: Test App on Phone (No Computer)

## 📱 Complete Walkthrough

---

### PHASE 1: Build the APK (5 minutes)

#### Step 1: Open GitHub on Your Phone
1. Open your phone's **web browser** (Chrome, Firefox, etc.)
2. Go to: `https://github.com`
3. **Log in** to your GitHub account
4. Navigate to your repository: `https://github.com/YOUR_USERNAME/interval-bell`

#### Step 2: Go to Actions Tab
1. At the top of the repository page, tap **"Actions"**
   - If you don't see it, tap the **☰ menu** (three lines) first
2. You should see a list of workflows

#### Step 3: Find and Run the Build Workflow
1. Look for **"Build Android APK"** in the workflow list
2. **Tap on "Build Android APK"**
3. On the right side, you'll see a button: **"Run workflow"**
4. **Tap "Run workflow"**
5. A popup appears - tap the green **"Run workflow"** button again
6. You'll see a **yellow circle** ⚪ - this means it's building

#### Step 4: Wait for Build to Complete
1. **Wait 2-5 minutes** (build is happening in the cloud)
2. **Pull down to refresh** the page every 30 seconds
3. Look for the status to change:
   - ⚪ Yellow = Still building
   - ✅ Green checkmark = **SUCCESS!**
   - ❌ Red X = Failed (see troubleshooting)

---

### PHASE 2: Download the APK (2 minutes)

#### Step 5: Open the Completed Build
1. When you see the **green checkmark** ✅, **tap on it**
2. This opens the workflow run details page

#### Step 6: Find the Artifacts Section
1. **Scroll down** on the workflow run page
2. Look for a section called **"Artifacts"**
3. You'll see: **"interval-bell-app"**
4. **Tap on "interval-bell-app"**

#### Step 7: Download Completes
1. Your browser will download the file
2. Check your **notification bar** - you should see a download notification
3. The file goes to your **Downloads folder**
4. Wait for download to finish (file is about 2-5 MB)

---

### PHASE 3: Install the APK (3 minutes)

#### Step 8: Enable Unknown Sources
1. Open your phone's **Settings** app
2. Go to: **Settings → Apps → Special Access → Install Unknown Apps**
   - (Path may vary: could be under Security or Privacy)
3. **Find your File Manager app** in the list
   - Common names: "Files", "My Files", "Samsung My Files"
4. **Tap on your File Manager**
5. **Toggle ON** "Allow from this source"
6. Press **Back** button

#### Step 9: Find the Downloaded APK
1. Open your **File Manager** app
2. Go to **Downloads** folder
3. Look for: `app-debug.apk` or `interval-bell-app.zip`
4. If it's a ZIP file, **tap it to extract**, then find `app-debug.apk`

#### Step 10: Install the App
1. **Tap on `app-debug.apk`**
2. You'll see an installation screen showing:
   - App name: Interval Bell
   - Package: com.intervalbell.app
   - Permissions needed
3. **Tap "Install"** at the bottom
4. Wait 10-30 seconds
5. When done, tap **"Open"** to launch the app

---

### PHASE 4: Test the App (10-15 minutes)

#### Step 11: First Launch
1. The app opens showing:
   - Interval input field
   - Bell sound dropdown
   - Start button
   - Stop button
   - Countdown display area

#### Step 12: Quick Test (30 seconds)
1. **Enter "10"** in the interval field (10 seconds)
2. **Select "Classic Bell"** from dropdown
3. **Tap "Start"**
4. **Watch the countdown** - should go: 10, 9, 8, 7...
5. **Wait for bell to ring** (after 10 seconds)
6. **Verify**:
   - ✅ Bell sound plays
   - ✅ Phone vibrates
   - ✅ Timer starts counting again
7. **Tap "Stop"**

#### Step 13: Test All Bell Sounds (2 minutes)
1. For each bell sound:
   - Select it from dropdown
   - Start a 15-second timer
   - Wait for bell to ring
   - Verify sound plays correctly
2. Test all 5 sounds:
   - Classic Bell
   - Church Bell
   - Alarm Bell
   - Chime Bell
   - Digital Bell

#### Step 14: Test Background Operation (2 minutes)
1. **Start a 20-second timer**
2. **Press Home button** (app goes to background)
3. **Open another app** (like Messages)
4. **Wait 20 seconds**
5. **Verify bell rings** even though app is in background
6. **Return to Interval Bell app**
7. **Verify timer is still running**

#### Step 15: Test Stop Function (30 seconds)
1. **Start a 30-second timer**
2. **Wait 10 seconds**
3. **Tap "Stop"**
4. **Verify**:
   - ✅ Timer stops immediately
   - ✅ No bell rings
   - ✅ You can start a new timer

---

## ✅ Testing Checklist

Mark each item as you test:

### Installation
- [ ] APK downloaded successfully
- [ ] APK installed without errors
- [ ] App appears in app drawer
- [ ] App launches without crashing

### Basic Functionality
- [ ] Can enter interval time
- [ ] Can select bell sound
- [ ] Start button works
- [ ] Stop button works
- [ ] Countdown displays correctly
- [ ] Timer counts down accurately

### Bell & Vibration
- [ ] Classic Bell works
- [ ] Church Bell works
- [ ] Alarm Bell works
- [ ] Chime Bell works
- [ ] Digital Bell works
- [ ] Phone vibrates when bell rings

### Background Operation
- [ ] Timer works when app is minimized
- [ ] Bell rings when app is in background
- [ ] Timer works when screen is off
- [ ] Bell rings when screen is off

---

## 🚨 Troubleshooting Quick Fixes

**Build fails?**
- Check Actions tab for error messages
- Make sure all files are in the repository
- Try running workflow again

**Can't download APK?**
- Make sure build completed (green checkmark)
- Try different browser
- Check internet connection

**Install blocked?**
- Enable "Install Unknown Apps" for your File Manager
- Settings → Apps → Special Access → Install Unknown Apps

**App crashes?**
- Check Android version (needs 7.0+)
- Uninstall and reinstall
- Clear app data and try again

**Bell doesn't ring?**
- Check phone volume
- Try different bell sound
- Check app permissions

**Timer doesn't work in background?**
- Disable battery optimization for the app
- Settings → Apps → Interval Bell → Battery → Unrestricted

---

## ⏱️ Time Breakdown

- **Build APK**: 2-5 minutes
- **Download**: 30 seconds - 2 minutes
- **Install**: 1-2 minutes
- **Basic Testing**: 5-10 minutes
- **Complete Testing**: 15-20 minutes

**Total**: ~30-45 minutes for everything

---

## 🎯 Success!

You've successfully:
- ✅ Built the app in the cloud
- ✅ Downloaded it to your phone
- ✅ Installed it without a computer
- ✅ Tested all functionality

**Enjoy your Interval Bell app!** 🔔
