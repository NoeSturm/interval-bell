package com.intervalbell.app;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Enum representing different bell tones available in the app.
 * Each tone has a display name resource ID and a method to play its sound.
 */
public enum BellTone {
    // Classic/Traditional Tones
    CLASSIC_BELL(R.string.tone_classic_bell, "🔔") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
        }
    },
    CHURCH_BELL(R.string.tone_church_bell, "⛪") {
        @Override
        public void play(ToneGenerator toneGen) {
            // Deep resonant tone
            toneGen.startTone(ToneGenerator.TONE_CDMA_LOW_L, 800);
        }
    },
    TEMPLE_BELL(R.string.tone_temple_bell, "🛕") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_MED_L, 600);
        }
    },
    CHIME(R.string.tone_chime, "🎐") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_HIGH_L, 400);
        }
    },
    GRANDFATHER_CLOCK(R.string.tone_grandfather_clock, "🕰️") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_LOW_PBX_L, 1000);
        }
    },

    // Digital/Modern Tones
    DIGITAL_BEEP(R.string.tone_digital_beep, "📟") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 300);
        }
    },
    NOTIFICATION(R.string.tone_notification, "📱") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 200);
        }
    },
    ALARM(R.string.tone_alarm, "⏰") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 500);
        }
    },
    SCI_FI_ALERT(R.string.tone_sci_fi_alert, "🚀") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 400);
        }
    },
    RADAR_PING(R.string.tone_radar_ping, "📡") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_NETWORK_USA_RINGBACK, 300);
        }
    },

    // Musical Tones
    PIANO_CHORD(R.string.tone_piano_chord, "🎹") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 400);
        }
    },
    XYLOPHONE(R.string.tone_xylophone, "🎵") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_HIGH_PBX_L, 350);
        }
    },
    HARP(R.string.tone_harp, "🎶") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_MED_PBX_L, 500);
        }
    },
    MUSIC_BOX(R.string.tone_music_box, "🎁") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_HIGH_SS, 400);
        }
    },

    // Nature Tones
    BIRD_CHIRP(R.string.tone_bird_chirp, "🐦") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_AUTOREDIAL_LITE, 300);
        }
    },
    WATER_DROP(R.string.tone_water_drop, "💧") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_PROP_PROMPT, 200);
        }
    },

    // Funny/Fun Tones
    DUCK_QUACK(R.string.tone_duck_quack, "🦆") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 250);
        }
    },
    ROBOT_BEEP(R.string.tone_robot_beep, "🤖") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_CALLDROP_LITE, 400);
        }
    },
    DOORBELL(R.string.tone_doorbell, "🚪") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_NORMAL, 600);
        }
    },
    GAME_OVER(R.string.tone_game_over, "🎮") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE, 500);
        }
    },
    CELEBRATION(R.string.tone_celebration, "🎉") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_INCALL_LITE, 400);
        }
    },
    SPACESHIP(R.string.tone_spaceship, "🛸") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 350);
        }
    },
    SUBMARINE(R.string.tone_submarine, "🚢") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_SUP_ERROR, 500);
        }
    },
    MAGIC_WAND(R.string.tone_magic_wand, "✨") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_NETWORK_BUSY_ONE_SHOT, 300);
        }
    },
    COW_BELL(R.string.tone_cow_bell, "🐄") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
        }
    },
    BOXING_BELL(R.string.tone_boxing_bell, "🥊") {
        @Override
        public void play(ToneGenerator toneGen) {
            toneGen.startTone(ToneGenerator.TONE_CDMA_ABBR_INTERCEPT, 500);
        }
    };

    private final int nameResId;
    private final String emoji;

    BellTone(int nameResId, String emoji) {
        this.nameResId = nameResId;
        this.emoji = emoji;
    }

    public int getNameResId() {
        return nameResId;
    }

    public String getEmoji() {
        return emoji;
    }

    /**
     * Play the bell tone sound.
     * @param toneGen The ToneGenerator instance to use for playback
     */
    public abstract void play(ToneGenerator toneGen);

    /**
     * Get the display name for this tone including the emoji.
     * @param context Android context to resolve string resource
     * @return Display string with emoji and name
     */
    public String getDisplayName(android.content.Context context) {
        return emoji + " " + context.getString(nameResId);
    }
}
