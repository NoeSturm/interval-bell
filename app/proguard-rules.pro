# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep MainActivity
-keep class com.intervalbell.app.MainActivity { *; }

# Keep MediaPlayer
-keep class android.media.MediaPlayer { *; }

# Keep Ringtone
-keep class android.media.Ringtone { *; }
