# Install Interval Bell App on Your Phone (No Computer Needed!)

This guide shows you how to get the app on your phone without using a computer.

## Method 1: Download from GitHub Actions (Recommended)

### Step 1: Trigger the Build

1. **Open your web browser** on your phone (or any device)
2. Go to your GitHub repository: `https://github.com/YOUR_USERNAME/interval-bell`
3. Click on the **Actions** tab at the top
4. Click **Build Android APK** in the left sidebar (or find it in the workflow list)
5. Click **Run workflow** button (on the right side)
6. Click the green **Run workflow** button to start the build
7. Wait 2-5 minutes for the build to complete

### Step 2: Download the APK

1. Once the build finishes (you'll see a green checkmark), click on the workflow run
2. Scroll down to the **Artifacts** section
3. Click on **interval-bell-app** to download the APK file
4. The APK will download to your phone's Downloads folder

### Step 3: Install the APK

1. **Open your phone's File Manager** (or Downloads app)
2. Find the downloaded file: `interval-bell-app.zip` or `app-debug.apk`
   - If it's a ZIP file, extract it first (most phones can do this by tapping it)
3. **Tap the APK file** to install

4. **If you see "Install blocked"**:
   - Go to **Settings** → **Apps** → **Special Access** (or **Security**)
   - Find **Install Unknown Apps** (or **Unknown Sources**)
   - Select your **File Manager** or **Browser** (whichever you used)
   - **Enable** "Allow from this source"
   - Go back and try installing again

5. **Tap Install** when prompted
6. **Tap Open** when installation completes

### Step 4: Test the App

1. Open the **Interval Bell** app
2. Enter an interval (try **10 seconds** for quick testing)
3. Select a bell sound
4. Tap **Start**
5. Wait for the bell to ring!
6. Test the **Stop** button
7. Try different bell sounds

---

## Method 2: Use an Online Build Service

If GitHub Actions isn't available, you can use these services:

### Option A: Appetize.io (for testing only)
- Upload your code to GitHub
- Use Appetize.io to run the app in a browser (not a real install)

### Option B: GitHub Releases (if set up)
1. Check if there's a **Releases** section in your GitHub repo
2. Download the APK from the latest release
3. Install following Step 3 above

---

## Troubleshooting

### "Can't open file" or "Parse error"
- Make sure you downloaded the complete file
- Try downloading again
- Check that your phone has Android 7.0 or higher

### "App not installed" error
- Uninstall any previous version first
- Make sure "Install Unknown Apps" is enabled for your file manager
- Check that you have enough storage space

### Build fails on GitHub Actions
- Check the Actions tab for error messages
- Make sure all project files are committed to the repository
- Try running the workflow again

### Can't find the APK after download
- Check your **Downloads** folder
- Use your phone's search function to find "interval-bell" or "app-debug"
- Some browsers save to a "Downloads" folder in internal storage

---

## Quick Checklist

- [ ] Build triggered on GitHub Actions
- [ ] Build completed successfully
- [ ] APK downloaded to phone
- [ ] "Install Unknown Apps" enabled
- [ ] APK installed successfully
- [ ] App opens without crashing
- [ ] Timer works correctly
- [ ] Bell sounds play
- [ ] Vibration works

---

## Security Note

Since you're installing an APK directly (not from Google Play), your phone will warn you about security. This is normal for apps you build yourself. Only install APKs from sources you trust.

---

## Need Help?

- **Build not working?** Check the GitHub Actions logs for errors
- **Installation blocked?** Make sure you enabled "Install Unknown Apps"
- **App crashes?** Check that your phone is Android 7.0 or newer
- **Can't find the file?** Check your Downloads folder or use a file manager app

---

## Alternative: Ask Someone to Build It

If the automated build doesn't work, you can:
1. Share your GitHub repository link with someone who has Android Studio
2. Ask them to build the APK and send it to you
3. They can email it, upload to Google Drive, or use any file-sharing method
4. Then follow **Step 3** above to install
