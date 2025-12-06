package com.intervalbell.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.ToneGenerator;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText intervalInput;
    private Spinner soundSpinner;
    private Button startButton;
    private Button stopButton;
    private TextView timerText;
    private TextView statusText;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private ToneGenerator toneGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intervalInput = findViewById(R.id.intervalInput);
        soundSpinner = findViewById(R.id.soundSpinner);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);

        // create a tone generator for the "alarm" stream at 100% volume
        toneGen = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

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
    }

    private void startInterval() {
        if (isRunning) return;

        String input = intervalInput.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter an interval", Toast.LENGTH_SHORT).show();
            return;
        }

        long intervalSeconds = Long.parseLong(input);
        if (intervalSeconds <= 0) {
            Toast.makeText(this, "Interval must be > 0", Toast.LENGTH_SHORT).show();
            return;
        }

        isRunning = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        intervalInput.setEnabled(false);
        soundSpinner.setEnabled(false);

        startTimer(intervalSeconds);
    }

    private void startTimer(final long intervalSeconds) {
        countDownTimer = new CountDownTimer(intervalSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished / 1000) + "s");
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
        timerText.setText("0s");
        statusText.setVisibility(View.INVISIBLE);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        intervalInput.setEnabled(true);
        soundSpinner.setEnabled(true);
    }

    private void playSound() {
        try {
            // Show bell ringing status
            statusText.setVisibility(View.VISIBLE);
            // Beep for 500ms for a more noticeable bell sound
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toneGen != null) {
            toneGen.release();
        }
    }
}
