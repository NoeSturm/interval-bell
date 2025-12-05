package com.intervalbell.app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText intervalInput;
    private Spinner soundSpinner;
    private MaterialButton startButton;
    private MaterialButton stopButton;
    private TextView timeRemainingText;
    private TextView statusText;

    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private boolean isRunning = false;
    private long intervalSeconds = 60;
    private long remainingSeconds = 0;

    // Sound resource IDs - using system notification sounds
    // These are standard Android notification sounds available on most devices
    private final int[] soundResources = {
        android.R.raw.notification_001,  // Classic Bell
        android.R.raw.notification_002,  // Church Bell
        android.R.raw.notification_003,  // Alarm Bell
        android.R.raw.notification_004,  // Chime Bell
        android.R.raw.notification_005   // Digital Bell
    };
    
    // Fallback: Use default notification URI if system sounds aren't available
    private final android.net.Uri defaultNotificationUri = 
        android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupSoundSpinner();
        setupButtons();
        
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void initializeViews() {
        intervalInput = findViewById(R.id.intervalInput);
        soundSpinner = findViewById(R.id.soundSpinner);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timeRemainingText = findViewById(R.id.timeRemainingText);
        statusText = findViewById(R.id.statusText);
    }

    private void setupSoundSpinner() {
        String[] soundNames = {
            getString(R.string.classic_bell),
            getString(R.string.church_bell),
            getString(R.string.alarm_bell),
            getString(R.string.chime_bell),
            getString(R.string.digital_bell)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            soundNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapter);
    }

    private void setupButtons() {
        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
    }

    private void startTimer() {
        String intervalStr = intervalInput.getText().toString().trim();
        
        if (intervalStr.isEmpty()) {
            Toast.makeText(this, "Please enter an interval", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            intervalSeconds = Long.parseLong(intervalStr);
            if (intervalSeconds <= 0) {
                Toast.makeText(this, "Interval must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isRunning) {
            return;
        }

        isRunning = true;
        remainingSeconds = intervalSeconds;
        
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        intervalInput.setEnabled(false);
        soundSpinner.setEnabled(false);
        
        timeRemainingText.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.VISIBLE);
        
        startCountDown();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(remainingSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingSeconds = millisUntilFinished / 1000;
                updateTimeDisplay();
            }

            @Override
            public void onFinish() {
                playBellSound();
                vibrate();
                remainingSeconds = intervalSeconds;
                statusText.setText(R.string.bell_ringing);
                statusText.postDelayed(() -> {
                    if (isRunning) {
                        statusText.setText("");
                        startCountDown();
                    }
                }, 2000);
            }
        };
        countDownTimer.start();
    }

    private void updateTimeDisplay() {
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeRemainingText.setText(getString(R.string.time_remaining, timeString));
    }

    private void playBellSound() {
        try {
            // Release previous media player if exists
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            int selectedSoundIndex = soundSpinner.getSelectedItemPosition();
            if (selectedSoundIndex >= 0 && selectedSoundIndex < soundResources.length) {
                int soundResource = soundResources[selectedSoundIndex];
                mediaPlayer = MediaPlayer.create(this, soundResource);
                
                if (mediaPlayer != null) {
                    mediaPlayer.setOnCompletionListener(mp -> {
                        if (mp != null) {
                            mp.release();
                        }
                        mediaPlayer = null;
                    });
                    mediaPlayer.start();
                    return;
                }
            }
        } catch (Exception e) {
            // Continue to fallback
        }
        
        // Fallback to system default notification sound using Ringtone
        try {
            android.media.Ringtone ringtone = android.media.RingtoneManager.getRingtone(
                getApplicationContext(),
                defaultNotificationUri
            );
            if (ringtone != null) {
                ringtone.play();
            }
        } catch (Exception e) {
            // If all else fails, just vibrate
            vibrate();
        }
    }

    private void vibrate() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(500);
        }
    }

    private void stopTimer() {
        isRunning = false;
        
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        intervalInput.setEnabled(true);
        soundSpinner.setEnabled(true);
        
        timeRemainingText.setVisibility(View.GONE);
        statusText.setVisibility(View.GONE);
        remainingSeconds = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Keep timer running in background
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRunning && remainingSeconds > 0) {
            updateTimeDisplay();
        }
    }
}
