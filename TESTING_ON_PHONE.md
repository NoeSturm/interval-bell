# Testing Your App on Phone from Repository (No Computer Needed)

This guide explains how to set up automated testing and deployment to your phone directly from your GitHub repository, without needing to use your computer.

## Overview

There are several approaches depending on your app type:

## Method 1: GitHub Actions + Firebase App Distribution (Recommended for Android/iOS)

This method automatically builds your app and sends it to your phone whenever you push code.

### Setup Steps:

1. **Create a Firebase project** (if you don't have one):
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project
   - Enable "App Distribution" in the Firebase console

2. **Get Firebase credentials**:
   - Go to Project Settings → Service Accounts
   - Generate a new private key (JSON file)
   - Add this JSON content as a GitHub Secret named `FIREBASE_SERVICE_ACCOUNT`

3. **Get your device token**:
   - Install Firebase App Distribution app on your phone
   - Register your device to get a token
   - Add token as GitHub Secret: `FIREBASE_DEVICE_TOKEN`

4. **Update GitHub Actions workflow** (`.github/workflows/deploy.yml`):

```yaml
name: Build and Deploy to Phone

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
      
      - name: Build APK
        run: |
          # Add your build commands here
          # Example for Android:
          # ./gradlew assembleDebug
      
      - name: Deploy to Firebase App Distribution
        uses: wzieba/Firebase-Distribution-Github-Action@v1
        with:
          appId: ${{ secrets.FIREBASE_APP_ID }}
          token: ${{ secrets.FIREBASE_SERVICE_ACCOUNT }}
          groups: testers
          file: app/build/outputs/apk/debug/app-debug.apk
```

## Method 2: GitHub Actions + Expo (For React Native/Expo Apps)

If you're using Expo, this is the easiest method:

1. **Install Expo Go** on your phone from App Store/Play Store

2. **Update workflow**:

```yaml
name: Deploy to Expo

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - uses: actions/setup-node@v4
        with:
          node-version: '18'
      
      - name: Install dependencies
        run: npm install
      
      - name: Publish to Expo
        uses: expo/expo-github-action@v8
        with:
          expo-version: latest
          token: ${{ secrets.EXPO_TOKEN }}
      
      - name: Build and share
        run: |
          npx expo build:android --type apk
          # Or for iOS: npx expo build:ios
```

3. **Get Expo token**:
   - Run `npx expo login` locally once to get token
   - Add as GitHub Secret: `EXPO_TOKEN`

4. **Access on phone**:
   - Open Expo Go app
   - Scan QR code from GitHub Actions output
   - Or use the published URL

## Method 3: GitHub Actions + TestFlight (iOS Only)

For iOS apps, you can automatically deploy to TestFlight:

```yaml
name: Deploy to TestFlight

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Build and upload to TestFlight
        uses: apple-actions/upload-testflight-build@v1
        with:
          app-path: 'YourApp.ipa'
          issuer-id: ${{ secrets.APPSTORE_ISSUER_ID }}
          api-key-id: ${{ secrets.APPSTORE_API_KEY_ID }}
          api-private-key: ${{ secrets.APPSTORE_API_PRIVATE_KEY }}
```

## Method 4: GitHub Actions + Direct APK Download

Build APK and make it downloadable:

```yaml
name: Build APK

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Build APK
        run: |
          # Your build commands
      
      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: app-release
          path: app/build/outputs/apk/release/app-release.apk
      
      - name: Create Release
        uses: softprops/action-gh-release@v1
        with:
          files: app/build/outputs/apk/release/app-release.apk
          draft: false
          prerelease: true
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

Then download the APK from GitHub Releases on your phone.

## Method 5: GitHub Actions + BrowserStack/Appium (Automated Testing)

For automated testing on real devices:

```yaml
name: Test on Real Devices

on:
  push:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Run tests on BrowserStack
        uses: browserstack/github-actions@master
        with:
          username: ${{ secrets.BROWSERSTACK_USERNAME }}
          access-key: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
          # Your test configuration
```

## Quick Start Recommendations

**For Android apps:**
- Use **Method 1 (Firebase App Distribution)** - easiest setup, automatic notifications

**For React Native/Expo apps:**
- Use **Method 2 (Expo)** - zero configuration needed

**For iOS apps:**
- Use **Method 3 (TestFlight)** - official Apple solution

**For quick manual testing:**
- Use **Method 4 (Direct Download)** - build APK and download from GitHub Releases

## Next Steps

1. Choose the method that fits your app type
2. Add the necessary secrets to your GitHub repository (Settings → Secrets and variables → Actions)
3. Update the workflow file with your specific build commands
4. Push code to trigger the workflow
5. Receive the app on your phone automatically!

## Notes

- All methods require initial setup (one-time configuration)
- After setup, everything happens automatically on push
- You'll receive notifications on your phone when new builds are ready
- No computer needed after initial GitHub Actions setup
