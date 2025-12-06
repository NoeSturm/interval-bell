package com.intervalbell.app;

/**
 * Enum representing different bell tones available in the app.
 * Each tone has a display name resource ID and uses high-quality synthesized audio.
 */
public enum BellTone {
    // Custom Recording (must be first for easy access)
    CUSTOM_RECORDING(R.string.tone_custom_recording, "🎙️", null) {
        @Override
        public void play(ToneSynthesizer synth) {
            // Custom recording playback is handled separately via AudioRecorder
            // This method is not used for custom recordings
        }

        @Override
        public boolean isCustomRecording() {
            return true;
        }
    },

    // Meditation & Wellness
    ZEN_BOWL(R.string.tone_zen_bowl, "🧘", ToneSynthesizer.ToneType.ZEN_BOWL),
    CRYSTAL_CHIME(R.string.tone_crystal_chime, "💎", ToneSynthesizer.ToneType.CRYSTAL_CHIME),
    TIBETAN_BOWL(R.string.tone_tibetan_bowl, "🔔", ToneSynthesizer.ToneType.TIBETAN_BOWL),
    TEMPLE_GONG(R.string.tone_temple_gong, "🛕", ToneSynthesizer.ToneType.TEMPLE_GONG),
    MINDFUL_BELL(R.string.tone_mindful_bell, "🪷", ToneSynthesizer.ToneType.MINDFUL_BELL),

    // Modern & Digital
    SOFT_PULSE(R.string.tone_soft_pulse, "💫", ToneSynthesizer.ToneType.SOFT_PULSE),
    AMBIENT_WAVE(R.string.tone_ambient_wave, "🌊", ToneSynthesizer.ToneType.AMBIENT_WAVE),
    DIGITAL_CHIME(R.string.tone_digital_chime, "✨", ToneSynthesizer.ToneType.DIGITAL_CHIME),
    AURORA(R.string.tone_aurora, "🌌", ToneSynthesizer.ToneType.AURORA),

    // Musical
    MARIMBA(R.string.tone_marimba, "🎵", ToneSynthesizer.ToneType.MARIMBA),
    VIBRAPHONE(R.string.tone_vibraphone, "🎶", ToneSynthesizer.ToneType.VIBRAPHONE),
    KALIMBA(R.string.tone_kalimba, "🎹", ToneSynthesizer.ToneType.KALIMBA),
    WIND_CHIMES(R.string.tone_wind_chimes, "🎐", ToneSynthesizer.ToneType.WIND_CHIMES),

    // Nature-Inspired
    WATER_DROPLET(R.string.tone_water_droplet, "💧", ToneSynthesizer.ToneType.WATER_DROPLET),
    BAMBOO_KNOCK(R.string.tone_bamboo_knock, "🎋", ToneSynthesizer.ToneType.BAMBOO_KNOCK),
    RAIN_STICK(R.string.tone_rain_stick, "🌧️", ToneSynthesizer.ToneType.RAIN_STICK),

    // Notification
    GENTLE_ALERT(R.string.tone_gentle_alert, "🔉", ToneSynthesizer.ToneType.GENTLE_ALERT),
    SUCCESS_TONE(R.string.tone_success, "✅", ToneSynthesizer.ToneType.SUCCESS_TONE),
    SOFT_GONG(R.string.tone_soft_gong, "🥁", ToneSynthesizer.ToneType.SOFT_GONG);

    private final int nameResId;
    private final String emoji;
    private final ToneSynthesizer.ToneType toneType;

    BellTone(int nameResId, String emoji, ToneSynthesizer.ToneType toneType) {
        this.nameResId = nameResId;
        this.emoji = emoji;
        this.toneType = toneType;
    }

    public int getNameResId() {
        return nameResId;
    }

    public String getEmoji() {
        return emoji;
    }

    public ToneSynthesizer.ToneType getToneType() {
        return toneType;
    }

    /**
     * Play the bell tone sound using the synthesizer.
     * @param synth The ToneSynthesizer instance to use for playback
     */
    public void play(ToneSynthesizer synth) {
        if (toneType != null && synth != null) {
            synth.playTone(toneType);
        }
    }

    /**
     * Check if this is the custom recording option.
     * @return true if this is a custom recording
     */
    public boolean isCustomRecording() {
        return false;
    }

    /**
     * Get the display name for this tone including the emoji.
     * @param context Android context to resolve string resource
     * @return Display string with emoji and name
     */
    public String getDisplayName(android.content.Context context) {
        return emoji + " " + context.getString(nameResId);
    }
}
