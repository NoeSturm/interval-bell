package com.intervalbell.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Handles audio recording for custom bell sounds.
 * Records audio using MediaRecorder and saves to app-specific storage.
 */
public class AudioRecorder {
    private static final String TAG = "AudioRecorder";
    private static final String RECORDING_FILENAME = "custom_bell.3gp";

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String recordingFilePath;
    private boolean isRecording = false;
    private long recordingStartTime = 0;
    private long recordingDurationMs = 0;
    private Context context;

    public interface RecordingCallback {
        void onRecordingStarted();
        void onRecordingStopped(long durationMs);
        void onRecordingError(String error);
        void onPlaybackStarted();
        void onPlaybackCompleted();
        void onPlaybackError(String error);
    }

    public AudioRecorder(Context context) {
        this.context = context;
        // Use app-specific storage (no permission needed for Android 10+)
        File recordingsDir = context.getFilesDir();
        this.recordingFilePath = new File(recordingsDir, RECORDING_FILENAME).getAbsolutePath();
    }

    /**
     * Starts recording audio.
     * @param callback Callback for recording events
     */
    public void startRecording(RecordingCallback callback) {
        if (isRecording) {
            if (callback != null) {
                callback.onRecordingError("Already recording");
            }
            return;
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                mediaRecorder = new MediaRecorder(context);
            } else {
                mediaRecorder = new MediaRecorder();
            }

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(recordingFilePath);

            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            recordingStartTime = System.currentTimeMillis();

            if (callback != null) {
                callback.onRecordingStarted();
            }

            Log.d(TAG, "Recording started: " + recordingFilePath);

        } catch (IOException | IllegalStateException e) {
            Log.e(TAG, "Error starting recording", e);
            releaseRecorder();
            if (callback != null) {
                callback.onRecordingError("Failed to start recording: " + e.getMessage());
            }
        }
    }

    /**
     * Stops the current recording.
     * @param callback Callback for recording events
     */
    public void stopRecording(RecordingCallback callback) {
        if (!isRecording || mediaRecorder == null) {
            if (callback != null) {
                callback.onRecordingError("Not currently recording");
            }
            return;
        }

        try {
            mediaRecorder.stop();
            recordingDurationMs = System.currentTimeMillis() - recordingStartTime;
            isRecording = false;
            releaseRecorder();

            if (callback != null) {
                callback.onRecordingStopped(recordingDurationMs);
            }

            Log.d(TAG, "Recording stopped. Duration: " + recordingDurationMs + "ms");

        } catch (RuntimeException e) {
            Log.e(TAG, "Error stopping recording", e);
            releaseRecorder();
            isRecording = false;
            if (callback != null) {
                callback.onRecordingError("Failed to stop recording: " + e.getMessage());
            }
        }
    }

    /**
     * Plays the recorded audio.
     * @param callback Callback for playback events
     */
    public void playRecording(RecordingCallback callback) {
        if (!hasRecording()) {
            if (callback != null) {
                callback.onPlaybackError("No recording available");
            }
            return;
        }

        releasePlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(recordingFilePath);
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(mp -> {
                releasePlayer();
                if (callback != null) {
                    callback.onPlaybackCompleted();
                }
            });

            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                releasePlayer();
                if (callback != null) {
                    callback.onPlaybackError("Playback error: " + what);
                }
                return true;
            });

            mediaPlayer.start();
            if (callback != null) {
                callback.onPlaybackStarted();
            }

            Log.d(TAG, "Playback started");

        } catch (IOException e) {
            Log.e(TAG, "Error playing recording", e);
            releasePlayer();
            if (callback != null) {
                callback.onPlaybackError("Failed to play recording: " + e.getMessage());
            }
        }
    }

    /**
     * Plays the recorded audio without callback (for bell playback during interval).
     */
    public void playRecordingForBell() {
        if (!hasRecording()) {
            Log.w(TAG, "No recording available for bell playback");
            return;
        }

        releasePlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(recordingFilePath);
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(mp -> releasePlayer());
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                releasePlayer();
                return true;
            });

            mediaPlayer.start();
            Log.d(TAG, "Bell playback started");

        } catch (IOException e) {
            Log.e(TAG, "Error playing bell recording", e);
            releasePlayer();
        }
    }

    /**
     * Checks if a recording exists.
     * @return true if a recording file exists
     */
    public boolean hasRecording() {
        File file = new File(recordingFilePath);
        return file.exists() && file.length() > 0;
    }

    /**
     * Gets the duration of the last recording in milliseconds.
     * @return Duration in milliseconds, or 0 if no recording
     */
    public long getRecordingDurationMs() {
        return recordingDurationMs;
    }

    /**
     * Gets the duration of the saved recording by reading the file.
     * @return Duration in seconds, or 0 if no recording
     */
    public long getRecordingDurationSeconds() {
        if (!hasRecording()) {
            return 0;
        }

        MediaPlayer tempPlayer = null;
        try {
            tempPlayer = new MediaPlayer();
            tempPlayer.setDataSource(recordingFilePath);
            tempPlayer.prepare();
            long durationMs = tempPlayer.getDuration();
            tempPlayer.release();
            // Round up to ensure we have enough time
            return (durationMs + 999) / 1000;
        } catch (IOException e) {
            Log.e(TAG, "Error getting recording duration", e);
            if (tempPlayer != null) {
                tempPlayer.release();
            }
            return 0;
        }
    }

    /**
     * Deletes the current recording.
     */
    public void deleteRecording() {
        File file = new File(recordingFilePath);
        if (file.exists()) {
            file.delete();
            recordingDurationMs = 0;
            Log.d(TAG, "Recording deleted");
        }
    }

    /**
     * Checks if currently recording.
     * @return true if recording is in progress
     */
    public boolean isRecording() {
        return isRecording;
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing recorder", e);
            }
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing player", e);
            }
            mediaPlayer = null;
        }
    }

    /**
     * Releases all resources. Call when done using the recorder.
     */
    public void release() {
        if (isRecording) {
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                Log.e(TAG, "Error stopping recording on release", e);
            }
        }
        releaseRecorder();
        releasePlayer();
        isRecording = false;
    }
}
