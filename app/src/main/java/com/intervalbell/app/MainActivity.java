package com.intervalbell.app;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText intervalInput;
    private Spinner soundSpinner;
    private MaterialButton startButton;
    private MaterialButton stopButton;
    private TextView timerText;
    private TextView timerLabel;
    private TextView statusText;
    private View timerGlowRing;
    private ImageView bellIcon;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private ToneGenerator toneGen;
    
    private ObjectAnimator pulseAnimator;
    private ObjectAnimator glowAnimator;
    private AnimatorSet bellAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        intervalInput = findViewById(R.id.intervalInput);
        soundSpinner = findViewById(R.id.soundSpinner);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timerText = findViewById(R.id.timerText);
        timerLabel = findViewById(R.id.timerLabel);
        statusText = findViewById(R.id.statusText);
        timerGlowRing = findViewById(R.id.timerGlowRing);
        bellIcon = findViewById(R.id.bellIcon);

        // Setup spinner with beautiful dropdown
        setupSoundSpinner();

        // Create a tone generator for the "alarm" stream at 100% volume
        toneGen = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

        // Setup click listeners
        startButton.setOnClickListener(v -> startInterval());
        stopButton.setOnClickListener(v -> stopInterval());
        
        // Start subtle glow animation on timer ring
        startIdleAnimation();
    }
    
    private void setupSoundSpinner() {
        String[] sounds = {
            "Classic Bell",
            "Digital Tone",
            "Soft Chime",
            "Alert Beep",
            "Meditation Bell"
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            sounds
        ) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view;
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.text_primary));
                text.setTextSize(14);
                return view;
            }
            
            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.text_primary));
                text.setPadding(32, 24, 32, 24);
                text.setTextSize(14);
                return view;
            }
        };
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapter);
    }
    
    private void startIdleAnimation() {
        // Subtle pulsing glow effect on the timer ring
        glowAnimator = ObjectAnimator.ofFloat(timerGlowRing, "alpha", 0.3f, 0.7f);
        glowAnimator.setDuration(2000);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        glowAnimator.start();
    }
    
    private void startRunningAnimation() {
        // More prominent pulsing when timer is running
        if (glowAnimator != null) {
            glowAnimator.cancel();
        }
        
        glowAnimator = ObjectAnimator.ofFloat(timerGlowRing, "alpha", 0.4f, 1.0f);
        glowAnimator.setDuration(1000);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setInterpolator(new LinearInterpolator());
        glowAnimator.start();
    }
    
    private void animateBellRinging() {
        // Cancel any existing animation
        if (bellAnimator != null) {
            bellAnimator.cancel();
        }
        
        // Create a bell ringing animation (rotate back and forth)
        ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(bellIcon, "rotation", 0f, -20f);
        rotateLeft.setDuration(100);
        
        ObjectAnimator rotateRight = ObjectAnimator.ofFloat(bellIcon, "rotation", -20f, 20f);
        rotateRight.setDuration(200);
        rotateRight.setRepeatCount(4);
        rotateRight.setRepeatMode(ValueAnimator.REVERSE);
        
        ObjectAnimator rotateBack = ObjectAnimator.ofFloat(bellIcon, "rotation", 20f, 0f);
        rotateBack.setDuration(100);
        
        // Scale animation for emphasis
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(bellIcon, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(bellIcon, "scaleY", 1f, 1.2f);
        scaleUpX.setDuration(200);
        scaleUpY.setDuration(200);
        
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(bellIcon, "scaleX", 1.2f, 1f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(bellIcon, "scaleY", 1.2f, 1f);
        scaleDownX.setDuration(200);
        scaleDownY.setDuration(200);
        
        // Combine animations
        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.playTogether(scaleUpX, scaleUpY);
        
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.playTogether(scaleDownX, scaleDownY);
        
        AnimatorSet rotateSet = new AnimatorSet();
        rotateSet.playSequentially(rotateLeft, rotateRight, rotateBack);
        
        bellAnimator = new AnimatorSet();
        bellAnimator.playSequentially(scaleUp, rotateSet, scaleDown);
        bellAnimator.start();
        
        // Pulse the status text
        pulseStatusText();
    }
    
    private void pulseStatusText() {
        ObjectAnimator pulseAlpha = ObjectAnimator.ofFloat(statusText, "alpha", 0f, 1f);
        pulseAlpha.setDuration(300);
        pulseAlpha.setRepeatCount(3);
        pulseAlpha.setRepeatMode(ValueAnimator.REVERSE);
        pulseAlpha.start();
    }

    private void startInterval() {
        if (isRunning) return;

        String input = intervalInput.getText().toString();
        if (input.isEmpty()) {
            showToast("Please enter an interval");
            shakeView(intervalInput.getRootView().findViewById(R.id.settingsCard));
            return;
        }

        long intervalSeconds;
        try {
            intervalSeconds = Long.parseLong(input);
        } catch (NumberFormatException e) {
            showToast("Please enter a valid number");
            return;
        }
        
        if (intervalSeconds <= 0) {
            showToast("Interval must be greater than 0");
            shakeView(intervalInput.getRootView().findViewById(R.id.settingsCard));
            return;
        }

        isRunning = true;
        updateButtonStates(true);
        intervalInput.setEnabled(false);
        soundSpinner.setEnabled(false);
        
        // Start running animation
        startRunningAnimation();

        startTimer(intervalSeconds);
    }
    
    private void shakeView(View view) {
        ObjectAnimator shake = ObjectAnimator.ofFloat(view, "translationX", 0, 10, -10, 10, -10, 5, -5, 0);
        shake.setDuration(400);
        shake.start();
    }
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    private void updateButtonStates(boolean running) {
        if (running) {
            startButton.setEnabled(false);
            startButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_disabled));
            stopButton.setEnabled(true);
            stopButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_stop));
        } else {
            startButton.setEnabled(true);
            startButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_start));
            stopButton.setEnabled(false);
            stopButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_disabled));
        }
    }

    private void startTimer(final long intervalSeconds) {
        countDownTimer = new CountDownTimer(intervalSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerText.setText(String.valueOf(seconds));
                
                // Update label based on singular/plural
                timerLabel.setText(seconds == 1 ? "second" : "seconds");
                
                // Hide the bell status after first tick of new interval
                if (statusText.getVisibility() == View.VISIBLE) {
                    statusText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                playSound();
                // Restart the timer automatically for the next interval
                if (isRunning) {
                    startTimer(intervalSeconds);
                }
            }
        }.start();
    }

    private void stopInterval() {
        isRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerText.setText("0");
        timerLabel.setText("seconds");
        statusText.setVisibility(View.INVISIBLE);
        updateButtonStates(false);
        intervalInput.setEnabled(true);
        soundSpinner.setEnabled(true);
        
        // Return to idle animation
        startIdleAnimation();
    }

    private void playSound() {
        try {
            // Show bell ringing status with animation
            statusText.setVisibility(View.VISIBLE);
            statusText.setAlpha(1f);
            
            // Animate the bell
            animateBellRinging();
            
            // Play sound based on spinner selection
            int soundType = getSoundType();
            toneGen.startTone(soundType, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int getSoundType() {
        int position = soundSpinner.getSelectedItemPosition();
        switch (position) {
            case 0: // Classic Bell
                return ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD;
            case 1: // Digital Tone
                return ToneGenerator.TONE_PROP_BEEP;
            case 2: // Soft Chime
                return ToneGenerator.TONE_CDMA_CALLDROP_LITE;
            case 3: // Alert Beep
                return ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE;
            case 4: // Meditation Bell
                return ToneGenerator.TONE_CDMA_ANSWER;
            default:
                return ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD;
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toneGen != null) {
            toneGen.release();
        }
        if (glowAnimator != null) {
            glowAnimator.cancel();
        }
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
        if (bellAnimator != null) {
            bellAnimator.cancel();
        }
    }
}
