package com.intervalbell.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView hoursDisplay;
    private TextView minutesDisplay;
    private TextView secondsDisplay;
    private LinearLayout timeDisplayLayout;
    private GridLayout numericKeypad;
    private LinearLayout soundSelectionLayout;
    private Spinner soundSpinner;
    private Button previewButton;
    private Button startButton;
    private Button stopButton;
    private TextView timerText;
    private TextView statusText;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private ToneSynthesizer toneSynth;
    
    // Recording UI elements
    private LinearLayout recordingLayout;
    private View recordingCard;
    private TextView recordingStatusText;
    private Button recordButton;
    private Button deleteRecordingButton;
    
    // Timer card
    private View timerCard;
    
    // Audio recorder for custom recordings
    private AudioRecorder audioRecorder;
    
    // Permission request launcher
    private ActivityResultLauncher<String> requestPermissionLauncher;
    
    // Selected bell tone
    private BellTone selectedTone = BellTone.ZEN_BOWL;

    // Store the entered digits (max 6 digits for HH:MM:SS)
    private StringBuilder enteredDigits = new StringBuilder();
    private static final int MAX_DIGITS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        hoursDisplay = findViewById(R.id.hoursDisplay);
        minutesDisplay = findViewById(R.id.minutesDisplay);
        secondsDisplay = findViewById(R.id.secondsDisplay);
        timeDisplayLayout = findViewById(R.id.timeDisplayLayout);
        numericKeypad = findViewById(R.id.numericKeypad);
        soundSelectionLayout = findViewById(R.id.soundSelectionLayout);
        soundSpinner = findViewById(R.id.soundSpinner);
        previewButton = findViewById(R.id.previewButton);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);
        
        // Initialize recording views
        recordingLayout = findViewById(R.id.recordingLayout);
        recordingCard = findViewById(R.id.recordingCard);
        recordingStatusText = findViewById(R.id.recordingStatusText);
        recordButton = findViewById(R.id.recordButton);
        deleteRecordingButton = findViewById(R.id.deleteRecordingButton);
        
        // Initialize timer card
        timerCard = findViewById(R.id.timerCard);

        // Create synthesizer for high-quality tone playback
        toneSynth = new ToneSynthesizer();
        
        // Initialize audio recorder
        audioRecorder = new AudioRecorder(this);
        
        // Setup permission request launcher
        setupPermissionLauncher();

        // Setup numeric keypad buttons
        setupNumericKeypad();
        
        // Setup sound spinner with bell tones
        setupSoundSpinner();
        
        // Setup recording buttons
        setupRecordingButtons();

        // Preview button click listener
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSelectedTone();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInterval();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopInterval();
            }
        });

        // Initialize display
        updateTimeDisplay();
        
        // Update recording status on startup
        updateRecordingStatus();
    }

    private void setupSoundSpinner() {
        // Create list of display names for the spinner
        final BellTone[] tones = BellTone.values();
        List<String> toneNames = new ArrayList<>();
        for (BellTone tone : tones) {
            toneNames.add(tone.getDisplayName(this));
        }

        // Create custom adapter with larger text
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                toneNames
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextSize(16);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextSize(16);
                textView.setPadding(32, 24, 32, 24);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapter);
        
        // Default to first non-custom tone (ZEN_BOWL at index 1)
        soundSpinner.setSelection(1);

        // Handle selection changes
        soundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTone = tones[position];
                // Show/hide recording layout based on selection
                if (selectedTone.isCustomRecording()) {
                    recordingCard.setVisibility(View.VISIBLE);
                    updateRecordingStatus();
                } else {
                    recordingCard.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTone = BellTone.ZEN_BOWL;
                recordingCard.setVisibility(View.GONE);
            }
        });
    }

    private void setupNumericKeypad() {
        // Number buttons 1-9
        int[] buttonIds = {
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int i = 0; i < buttonIds.length; i++) {
            final int digit = i + 1;
            findViewById(buttonIds[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDigitPressed(String.valueOf(digit));
                }
            });
        }

        // Button 0
        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDigitPressed("0");
            }
        });

        // Button 00
        findViewById(R.id.btn00).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDigitPressed("0");
                onDigitPressed("0");
            }
        });

        // Delete button
        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeletePressed();
            }
        });

        // Long press delete to clear all
        findViewById(R.id.btnDelete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                enteredDigits.setLength(0);
                updateTimeDisplay();
                return true;
            }
        });
    }

    private void onDigitPressed(String digit) {
        if (enteredDigits.length() < MAX_DIGITS) {
            // Don't allow leading zeros that would exceed display
            String potentialValue = enteredDigits.toString() + digit;
            // Remove leading zeros for validation
            while (potentialValue.length() > 0 && potentialValue.charAt(0) == '0') {
                potentialValue = potentialValue.substring(1);
            }
            if (potentialValue.length() <= MAX_DIGITS) {
                enteredDigits.append(digit);
                updateTimeDisplay();
            }
        }
    }

    private void onDeletePressed() {
        if (enteredDigits.length() > 0) {
            enteredDigits.deleteCharAt(enteredDigits.length() - 1);
            updateTimeDisplay();
        }
    }

    private void updateTimeDisplay() {
        // Pad with zeros to get 6 digits
        String paddedDigits = String.format("%6s", enteredDigits.toString()).replace(' ', '0');
        
        // Extract hours, minutes, seconds
        String hours = paddedDigits.substring(0, 2);
        String minutes = paddedDigits.substring(2, 4);
        String seconds = paddedDigits.substring(4, 6);

        hoursDisplay.setText(hours);
        minutesDisplay.setText(minutes);
        secondsDisplay.setText(seconds);

        // Update colors based on whether there's a value
        int activeColor = getResources().getColor(R.color.time_display_active, getTheme());
        int inactiveColor = getResources().getColor(R.color.time_display_inactive, getTheme());

        hoursDisplay.setTextColor(hours.equals("00") ? inactiveColor : activeColor);
        minutesDisplay.setTextColor(minutes.equals("00") ? inactiveColor : activeColor);
        secondsDisplay.setTextColor(seconds.equals("00") ? inactiveColor : activeColor);
    }

    private long getIntervalInSeconds() {
        if (enteredDigits.length() == 0) {
            return 0;
        }

        // Pad with zeros to get 6 digits
        String paddedDigits = String.format("%6s", enteredDigits.toString()).replace(' ', '0');
        
        int hours = Integer.parseInt(paddedDigits.substring(0, 2));
        int minutes = Integer.parseInt(paddedDigits.substring(2, 4));
        int seconds = Integer.parseInt(paddedDigits.substring(4, 6));

        return hours * 3600L + minutes * 60L + seconds;
    }

    private void startInterval() {
        if (isRunning) return;

        long intervalSeconds = getIntervalInSeconds();
        if (intervalSeconds <= 0) {
            Toast.makeText(this, "Please enter a time interval", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Check if using custom recording and validate minimum interval
        long minimumInterval = getMinimumIntervalSeconds();
        if (minimumInterval > 0 && intervalSeconds < minimumInterval) {
            Toast.makeText(this, 
                getString(R.string.interval_too_short, (int) minimumInterval), 
                Toast.LENGTH_LONG).show();
            return;
        }
        
        // If custom recording is selected but no recording exists, show error
        if (selectedTone.isCustomRecording() && !audioRecorder.hasRecording()) {
            Toast.makeText(this, R.string.no_recording, Toast.LENGTH_SHORT).show();
            return;
        }

        isRunning = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        
        // Hide keypad and time display, show timer
        findViewById(R.id.timeDisplayCard).setVisibility(View.GONE);
        numericKeypad.setVisibility(View.GONE);
        findViewById(R.id.soundCard).setVisibility(View.GONE);
        recordingCard.setVisibility(View.GONE);
        timerCard.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);

        startTimer(intervalSeconds);
    }

    private void startTimer(final long intervalSeconds) {
        countDownTimer = new CountDownTimer(intervalSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long totalSeconds = millisUntilFinished / 1000;
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;
                
                timerText.setText(String.format("%02dh %02dm %02ds", hours, minutes, seconds));
                
                // Hide the bell status after first tick of new interval
                if (statusText.getVisibility() == View.VISIBLE) {
                    statusText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                playSelectedTone();
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
        timerText.setText("00h 00m 00s");
        statusText.setVisibility(View.INVISIBLE);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        
        // Show keypad and time display again
        findViewById(R.id.timeDisplayCard).setVisibility(View.VISIBLE);
        numericKeypad.setVisibility(View.VISIBLE);
        findViewById(R.id.soundCard).setVisibility(View.VISIBLE);
        timerCard.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
        
        // Show recording layout if custom recording is selected
        if (selectedTone.isCustomRecording()) {
            recordingCard.setVisibility(View.VISIBLE);
        }
    }

    private void playSelectedTone() {
        try {
            // Show bell ringing status
            statusText.setVisibility(View.VISIBLE);
            
            // Handle custom recording playback
            if (selectedTone.isCustomRecording()) {
                if (audioRecorder.hasRecording()) {
                    audioRecorder.playRecordingForBell();
                }
            } else {
                // Play the selected synthesized tone
                selectedTone.play(toneSynth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    // Permission granted, start recording
                    startRecording();
                } else {
                    // Permission denied
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
                }
            }
        );
    }
    
    private void setupRecordingButtons() {
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioRecorder.isRecording()) {
                    stopRecording();
                } else {
                    checkPermissionAndRecord();
                }
            }
        });
        
        deleteRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecording();
            }
        });
    }
    
    private void checkPermissionAndRecord() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            startRecording();
        } else {
            // Request permission
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }
    
    private void startRecording() {
        audioRecorder.startRecording(new AudioRecorder.RecordingCallback() {
            @Override
            public void onRecordingStarted() {
                runOnUiThread(() -> {
                    recordButton.setText(R.string.stop_recording_button);
                    recordButton.setBackgroundTintList(
                        ContextCompat.getColorStateList(MainActivity.this, R.color.recording_active));
                    recordingStatusText.setText(R.string.recording_in_progress);
                    recordingStatusText.setTextColor(
                        ContextCompat.getColor(MainActivity.this, R.color.recording_active));
                    deleteRecordingButton.setVisibility(View.GONE);
                });
            }

            @Override
            public void onRecordingStopped(long durationMs) {
                // Not used here, handled in stopRecording
            }

            @Override
            public void onRecordingError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, 
                        getString(R.string.recording_error, error), Toast.LENGTH_SHORT).show();
                    updateRecordingStatus();
                });
            }

            @Override
            public void onPlaybackStarted() {}

            @Override
            public void onPlaybackCompleted() {}

            @Override
            public void onPlaybackError(String error) {}
        });
    }
    
    private void stopRecording() {
        audioRecorder.stopRecording(new AudioRecorder.RecordingCallback() {
            @Override
            public void onRecordingStarted() {}

            @Override
            public void onRecordingStopped(long durationMs) {
                runOnUiThread(() -> {
                    updateRecordingStatus();
                    long durationSec = (durationMs + 999) / 1000;
                    Toast.makeText(MainActivity.this,
                        getString(R.string.recording_saved, (int) durationSec), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onRecordingError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                        getString(R.string.recording_error, error), Toast.LENGTH_SHORT).show();
                    updateRecordingStatus();
                });
            }

            @Override
            public void onPlaybackStarted() {}

            @Override
            public void onPlaybackCompleted() {}

            @Override
            public void onPlaybackError(String error) {}
        });
    }
    
    private void deleteRecording() {
        audioRecorder.deleteRecording();
        updateRecordingStatus();
        Toast.makeText(this, R.string.recording_deleted, Toast.LENGTH_SHORT).show();
    }
    
    private void updateRecordingStatus() {
        recordButton.setText(R.string.record_button);
        recordButton.setBackgroundTintList(
            ContextCompat.getColorStateList(this, R.color.accent));
        recordingStatusText.setTextColor(
            ContextCompat.getColor(this, R.color.time_label));
        
        if (audioRecorder.hasRecording()) {
            long durationSec = audioRecorder.getRecordingDurationSeconds();
            recordingStatusText.setText(getString(R.string.recording_saved, (int) durationSec));
            deleteRecordingButton.setVisibility(View.VISIBLE);
        } else {
            recordingStatusText.setText(R.string.no_recording);
            deleteRecordingButton.setVisibility(View.GONE);
        }
    }
    
    /**
     * Gets the minimum interval required based on the recording length.
     * @return Minimum interval in seconds, or 0 if no recording or not using custom recording
     */
    private long getMinimumIntervalSeconds() {
        if (selectedTone.isCustomRecording() && audioRecorder.hasRecording()) {
            return audioRecorder.getRecordingDurationSeconds();
        }
        return 0;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toneSynth != null) {
            toneSynth.release();
        }
        if (audioRecorder != null) {
            audioRecorder.release();
        }
    }
}
