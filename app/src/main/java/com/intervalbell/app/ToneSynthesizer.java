package com.intervalbell.app;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

/**
 * High-quality audio synthesizer for generating smooth, modern bell tones.
 * Uses 44100 Hz sample rate with proper envelope shaping for professional sound.
 */
public class ToneSynthesizer {
    
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    
    private AudioTrack audioTrack;
    private boolean isPlaying = false;
    
    /**
     * Represents different synthesized tone types with unique characteristics.
     */
    public enum ToneType {
        // Meditation & Wellness
        ZEN_BOWL,           // Deep singing bowl with overtones
        CRYSTAL_CHIME,      // Bright, sparkly crystal sound
        TIBETAN_BOWL,       // Rich resonant bowl
        TEMPLE_GONG,        // Warm, expansive gong
        
        // Modern & Digital
        SOFT_PULSE,         // Gentle electronic pulse
        AMBIENT_WAVE,       // Smooth synth pad
        DIGITAL_CHIME,      // Clean modern chime
        AURORA,             // Ethereal, shimmering
        
        // Musical
        MARIMBA,            // Warm wooden tone
        VIBRAPHONE,         // Metallic, sustained
        KALIMBA,            // Plucky thumb piano
        WIND_CHIMES,        // Airy, random harmonics
        
        // Nature-Inspired
        WATER_DROPLET,      // Pure, clear drop
        BAMBOO_KNOCK,       // Hollow, organic
        RAIN_STICK,         // Gentle cascading
        
        // Notification
        GENTLE_ALERT,       // Soft attention-getter
        MINDFUL_BELL,       // Meditation timer style
        SUCCESS_TONE,       // Pleasant confirmation
        SOFT_GONG           // Subtle gong hit
    }
    
    /**
     * Plays a synthesized tone of the specified type.
     * @param type The tone type to play
     */
    public void playTone(ToneType type) {
        new Thread(() -> {
            try {
                short[] samples = generateTone(type);
                playAudioSamples(samples);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Generate audio samples for the specified tone type.
     */
    private short[] generateTone(ToneType type) {
        switch (type) {
            case ZEN_BOWL:
                return generateZenBowl();
            case CRYSTAL_CHIME:
                return generateCrystalChime();
            case TIBETAN_BOWL:
                return generateTibetanBowl();
            case TEMPLE_GONG:
                return generateTempleGong();
            case SOFT_PULSE:
                return generateSoftPulse();
            case AMBIENT_WAVE:
                return generateAmbientWave();
            case DIGITAL_CHIME:
                return generateDigitalChime();
            case AURORA:
                return generateAurora();
            case MARIMBA:
                return generateMarimba();
            case VIBRAPHONE:
                return generateVibraphone();
            case KALIMBA:
                return generateKalimba();
            case WIND_CHIMES:
                return generateWindChimes();
            case WATER_DROPLET:
                return generateWaterDroplet();
            case BAMBOO_KNOCK:
                return generateBambooKnock();
            case RAIN_STICK:
                return generateRainStick();
            case GENTLE_ALERT:
                return generateGentleAlert();
            case MINDFUL_BELL:
                return generateMindfulBell();
            case SUCCESS_TONE:
                return generateSuccessTone();
            case SOFT_GONG:
                return generateSoftGong();
            default:
                return generateMindfulBell();
        }
    }
    
    /**
     * Deep singing bowl with rich overtones - perfect for meditation
     */
    private short[] generateZenBowl() {
        double duration = 2.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 220.0; // A3
        double[] harmonics = {1.0, 2.0, 3.0, 4.76, 6.28};
        double[] amplitudes = {1.0, 0.5, 0.25, 0.15, 0.08};
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getExponentialDecay(t, 0.01, 2.0);
            
            double sample = 0;
            for (int h = 0; h < harmonics.length; h++) {
                double freq = baseFreq * harmonics[h];
                // Add slight detuning for richness
                double detune = 1.0 + Math.sin(t * 0.5) * 0.002;
                sample += amplitudes[h] * Math.sin(2.0 * Math.PI * freq * detune * t);
            }
            
            // Add subtle amplitude modulation (vibrato-like shimmer)
            double shimmer = 1.0 + 0.03 * Math.sin(2.0 * Math.PI * 5.5 * t);
            sample *= envelope * shimmer;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Bright, sparkly crystal sound with high frequencies
     */
    private short[] generateCrystalChime() {
        double duration = 1.8;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 1047.0; // C6
        double[] harmonics = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] amplitudes = {1.0, 0.6, 0.4, 0.25, 0.12};
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getExponentialDecay(t, 0.002, 1.2);
            
            double sample = 0;
            for (int h = 0; h < harmonics.length; h++) {
                double freq = baseFreq * harmonics[h];
                // Higher harmonics decay faster
                double harmEnv = Math.pow(envelope, 1.0 + h * 0.3);
                sample += amplitudes[h] * harmEnv * Math.sin(2.0 * Math.PI * freq * t);
            }
            
            samples[i] = toShort(sample * 0.45);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Rich resonant Tibetan bowl with complex overtones
     */
    private short[] generateTibetanBowl() {
        double duration = 3.0;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 174.61; // F3
        // Tibetan bowls have non-harmonic partials
        double[] partials = {1.0, 2.71, 5.19, 8.44, 12.5};
        double[] amplitudes = {1.0, 0.7, 0.35, 0.18, 0.08};
        double[] decays = {2.5, 2.0, 1.5, 1.2, 0.9};
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            
            double sample = 0;
            for (int p = 0; p < partials.length; p++) {
                double freq = baseFreq * partials[p];
                double env = getExponentialDecay(t, 0.015, decays[p]);
                // Add beating effect between partials
                double beat = 1.0 + 0.02 * Math.sin(2.0 * Math.PI * (0.5 + p * 0.3) * t);
                sample += amplitudes[p] * env * beat * Math.sin(2.0 * Math.PI * freq * t);
            }
            
            samples[i] = toShort(sample * 0.4);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Warm, expansive gong with long sustain
     */
    private short[] generateTempleGong() {
        double duration = 3.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 98.0; // G2
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getGongEnvelope(t, 0.02, 3.0);
            
            double sample = 0;
            // Fundamental
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            // Inharmonic overtones typical of gongs
            sample += 0.5 * Math.sin(2.0 * Math.PI * baseFreq * 2.4 * t);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 3.8 * t);
            sample += 0.2 * Math.sin(2.0 * Math.PI * baseFreq * 5.3 * t);
            sample += 0.1 * Math.sin(2.0 * Math.PI * baseFreq * 7.1 * t);
            
            // Add complexity with slight frequency wobble
            double wobble = 1.0 + 0.01 * Math.sin(2.0 * Math.PI * 2.5 * t);
            sample *= envelope * wobble;
            
            samples[i] = toShort(sample * 0.35);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Gentle electronic pulse - clean and modern
     */
    private short[] generateSoftPulse() {
        double duration = 0.8;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 440.0; // A4
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getSoftEnvelope(t, 0.05, 0.15, 0.5);
            
            // Smooth, warm waveform using additive synthesis
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t);
            sample += 0.1 * Math.sin(2.0 * Math.PI * baseFreq * 3 * t);
            
            // Low-pass filter effect by reducing high harmonics
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Smooth synth pad - ambient and calming
     */
    private short[] generateAmbientWave() {
        double duration = 2.0;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 261.63; // C4
        double fifthFreq = 392.0; // G4
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getPadEnvelope(t, 0.3, 1.5);
            
            // Layered sine waves for pad-like sound
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.7 * Math.sin(2.0 * Math.PI * fifthFreq * t);
            sample += 0.4 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t);
            sample += 0.2 * Math.sin(2.0 * Math.PI * fifthFreq * 2 * t);
            
            // Subtle chorus effect
            double chorus = 0.1 * Math.sin(2.0 * Math.PI * (baseFreq * 1.003) * t);
            sample += chorus;
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.35);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Clean modern chime - digital clarity
     */
    private short[] generateDigitalChime() {
        double duration = 1.2;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        // Major chord arpeggio effect
        double[] freqs = {523.25, 659.25, 783.99}; // C5, E5, G5
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            
            double sample = 0;
            for (int n = 0; n < freqs.length; n++) {
                double noteDelay = n * 0.03;
                double noteT = t - noteDelay;
                if (noteT > 0) {
                    double noteEnv = getExponentialDecay(noteT, 0.005, 0.8);
                    sample += noteEnv * Math.sin(2.0 * Math.PI * freqs[n] * noteT);
                    // Add subtle harmonic
                    sample += noteEnv * 0.3 * Math.sin(2.0 * Math.PI * freqs[n] * 2 * noteT);
                }
            }
            
            samples[i] = toShort(sample * 0.4);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Ethereal, shimmering aurora sound
     */
    private short[] generateAurora() {
        double duration = 2.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 349.23; // F4
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getPadEnvelope(t, 0.4, 2.0);
            
            // Shimmering effect with detuned oscillators
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.8 * Math.sin(2.0 * Math.PI * baseFreq * 1.002 * t);
            sample += 0.6 * Math.sin(2.0 * Math.PI * baseFreq * 0.998 * t);
            sample += 0.5 * Math.sin(2.0 * Math.PI * baseFreq * 2.001 * t);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 3 * t);
            
            // Slow filter sweep effect
            double filterMod = 0.5 + 0.5 * Math.sin(2.0 * Math.PI * 0.3 * t);
            sample *= envelope * (0.7 + 0.3 * filterMod);
            
            samples[i] = toShort(sample * 0.3);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Warm wooden marimba tone
     */
    private short[] generateMarimba() {
        double duration = 1.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 392.0; // G4
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getPercussiveEnvelope(t, 0.003, 1.2);
            
            // Marimba has weak fundamental and strong 4th harmonic
            double sample = 0;
            sample += 0.7 * Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t);
            sample += 0.15 * Math.sin(2.0 * Math.PI * baseFreq * 3 * t);
            sample += 1.0 * Math.sin(2.0 * Math.PI * baseFreq * 4 * t) * Math.exp(-t * 5);
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.45);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Metallic vibraphone with sustained tone
     */
    private short[] generateVibraphone() {
        double duration = 2.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 523.25; // C5
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getExponentialDecay(t, 0.008, 2.0);
            
            // Vibraphone tremolo
            double tremolo = 1.0 + 0.15 * Math.sin(2.0 * Math.PI * 5.5 * t);
            
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.4 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t);
            sample += 0.2 * Math.sin(2.0 * Math.PI * baseFreq * 4 * t);
            sample += 0.1 * Math.sin(2.0 * Math.PI * baseFreq * 5 * t);
            
            sample *= envelope * tremolo;
            
            samples[i] = toShort(sample * 0.45);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Plucky thumb piano (kalimba) tone
     */
    private short[] generateKalimba() {
        double duration = 2.0;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 587.33; // D5
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getKalimbaEnvelope(t, 0.001, 1.5);
            
            double sample = 0;
            // Kalimba has strong fundamental with quick high-frequency transient
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.5 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t) * Math.exp(-t * 3);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 3 * t) * Math.exp(-t * 5);
            sample += 0.2 * Math.sin(2.0 * Math.PI * baseFreq * 5 * t) * Math.exp(-t * 8);
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Airy wind chimes with random-ish harmonics
     */
    private short[] generateWindChimes() {
        double duration = 2.2;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        // Multiple chime frequencies
        double[] chimeFreqs = {880.0, 1108.73, 1318.51, 1567.98, 1760.0};
        double[] delays = {0.0, 0.08, 0.15, 0.22, 0.35};
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            
            double sample = 0;
            for (int c = 0; c < chimeFreqs.length; c++) {
                double chimeT = t - delays[c];
                if (chimeT > 0) {
                    double env = getExponentialDecay(chimeT, 0.002, 1.0 + c * 0.2);
                    double freq = chimeFreqs[c];
                    sample += env * 0.6 * Math.sin(2.0 * Math.PI * freq * chimeT);
                    sample += env * 0.3 * Math.sin(2.0 * Math.PI * freq * 2 * chimeT);
                }
            }
            
            samples[i] = toShort(sample * 0.35);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Pure, clear water droplet sound
     */
    private short[] generateWaterDroplet() {
        double duration = 0.8;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double startFreq = 2000.0;
        double endFreq = 600.0;
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getPercussiveEnvelope(t, 0.001, 0.5);
            
            // Pitch bend down like a water drop
            double freqT = t / 0.15;
            double freq;
            if (freqT < 1.0) {
                freq = startFreq + (endFreq - startFreq) * freqT * freqT;
            } else {
                freq = endFreq;
            }
            
            double sample = Math.sin(2.0 * Math.PI * freq * t);
            sample += 0.3 * Math.sin(2.0 * Math.PI * freq * 2 * t);
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Hollow bamboo knock sound
     */
    private short[] generateBambooKnock() {
        double duration = 0.6;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 280.0;
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getPercussiveEnvelope(t, 0.002, 0.4);
            
            // Hollow resonance with inharmonic partials
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.6 * Math.sin(2.0 * Math.PI * baseFreq * 2.76 * t) * Math.exp(-t * 8);
            sample += 0.3 * Math.sin(2.0 * Math.PI * baseFreq * 5.4 * t) * Math.exp(-t * 15);
            
            // Add click transient
            if (t < 0.01) {
                sample += 0.5 * (1.0 - t / 0.01);
            }
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.55);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Gentle cascading rain stick effect
     */
    private short[] generateRainStick() {
        double duration = 2.0;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        java.util.Random random = new java.util.Random(42); // Consistent seed for repeatability
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getSwellEnvelope(t, 0.3, 1.5);
            
            double sample = 0;
            
            // High-frequency rustling
            sample += 0.15 * (random.nextDouble() * 2 - 1);
            
            // Occasional bright pings
            for (int ping = 0; ping < 15; ping++) {
                double pingT = t - (ping * 0.12 + 0.05);
                if (pingT > 0 && pingT < 0.3) {
                    double pingFreq = 1500 + ping * 200;
                    double pingEnv = Math.exp(-pingT * 12);
                    sample += 0.2 * pingEnv * Math.sin(2.0 * Math.PI * pingFreq * pingT);
                }
            }
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        // Apply smoothing filter
        return applySmoothing(applyFades(samples));
    }
    
    /**
     * Soft attention-getting alert
     */
    private short[] generateGentleAlert() {
        double duration = 0.6;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double freq1 = 698.46; // F5
        double freq2 = 880.0;  // A5
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            
            double sample = 0;
            
            // Two-note gentle alert
            if (t < 0.3) {
                double env1 = getSoftEnvelope(t, 0.02, 0.1, 0.25);
                sample += env1 * Math.sin(2.0 * Math.PI * freq1 * t);
                sample += env1 * 0.3 * Math.sin(2.0 * Math.PI * freq1 * 2 * t);
            }
            if (t > 0.15) {
                double t2 = t - 0.15;
                double env2 = getSoftEnvelope(t2, 0.02, 0.1, 0.35);
                sample += env2 * Math.sin(2.0 * Math.PI * freq2 * t2);
                sample += env2 * 0.3 * Math.sin(2.0 * Math.PI * freq2 * 2 * t2);
            }
            
            samples[i] = toShort(sample * 0.45);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Classic meditation timer bell - clean and resonant
     */
    private short[] generateMindfulBell() {
        double duration = 3.0;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 528.0; // "Solfeggio" frequency, C5-ish
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getExponentialDecay(t, 0.01, 2.5);
            
            double sample = 0;
            // Pure, clear bell with minimal harmonics
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.35 * Math.sin(2.0 * Math.PI * baseFreq * 2 * t);
            sample += 0.15 * Math.sin(2.0 * Math.PI * baseFreq * 3 * t);
            sample += 0.08 * Math.sin(2.0 * Math.PI * baseFreq * 4 * t);
            
            // Subtle beating
            sample += 0.1 * Math.sin(2.0 * Math.PI * (baseFreq * 1.003) * t);
            
            sample *= envelope;
            
            samples[i] = toShort(sample * 0.5);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Pleasant success/confirmation tone
     */
    private short[] generateSuccessTone() {
        double duration = 0.8;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        // Ascending major third
        double freq1 = 523.25; // C5
        double freq2 = 659.25; // E5
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            
            double sample = 0;
            
            // First note
            if (t < 0.4) {
                double env1 = getSoftEnvelope(t, 0.02, 0.15, 0.35);
                sample += env1 * Math.sin(2.0 * Math.PI * freq1 * t);
                sample += env1 * 0.4 * Math.sin(2.0 * Math.PI * freq1 * 2 * t);
            }
            
            // Second note (overlapping)
            if (t > 0.12) {
                double t2 = t - 0.12;
                double env2 = getSoftEnvelope(t2, 0.02, 0.15, 0.5);
                sample += env2 * Math.sin(2.0 * Math.PI * freq2 * t2);
                sample += env2 * 0.4 * Math.sin(2.0 * Math.PI * freq2 * 2 * t2);
            }
            
            samples[i] = toShort(sample * 0.45);
        }
        
        return applyFades(samples);
    }
    
    /**
     * Subtle soft gong - warm and non-intrusive
     */
    private short[] generateSoftGong() {
        double duration = 2.5;
        int numSamples = (int) (SAMPLE_RATE * duration);
        short[] samples = new short[numSamples];
        
        double baseFreq = 130.81; // C3
        
        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / SAMPLE_RATE;
            double envelope = getGongEnvelope(t, 0.05, 2.0);
            
            double sample = 0;
            sample += Math.sin(2.0 * Math.PI * baseFreq * t);
            sample += 0.4 * Math.sin(2.0 * Math.PI * baseFreq * 2.0 * t);
            sample += 0.2 * Math.sin(2.0 * Math.PI * baseFreq * 3.2 * t);
            sample += 0.1 * Math.sin(2.0 * Math.PI * baseFreq * 4.5 * t);
            
            // Slow modulation for warmth
            double mod = 1.0 + 0.02 * Math.sin(2.0 * Math.PI * 3.0 * t);
            sample *= envelope * mod;
            
            samples[i] = toShort(sample * 0.4);
        }
        
        return applyFades(samples);
    }
    
    // ========================
    // Envelope Functions
    // ========================
    
    private double getExponentialDecay(double t, double attack, double decay) {
        if (t < attack) {
            return t / attack;
        }
        return Math.exp(-(t - attack) / decay);
    }
    
    private double getPercussiveEnvelope(double t, double attack, double decay) {
        if (t < attack) {
            return t / attack;
        }
        double decayT = t - attack;
        return Math.exp(-decayT / decay) * (1.0 - 0.3 * (1.0 - Math.exp(-decayT * 10)));
    }
    
    private double getSoftEnvelope(double t, double attack, double hold, double release) {
        if (t < attack) {
            return smoothstep(0, attack, t);
        } else if (t < attack + hold) {
            return 1.0;
        } else {
            double releaseT = t - attack - hold;
            return 1.0 - smoothstep(0, release, releaseT);
        }
    }
    
    private double getPadEnvelope(double t, double attack, double total) {
        double release = total * 0.3;
        if (t < attack) {
            return smoothstep(0, attack, t);
        } else if (t < total - release) {
            return 1.0;
        } else {
            return 1.0 - smoothstep(0, release, t - (total - release));
        }
    }
    
    private double getGongEnvelope(double t, double attack, double decay) {
        if (t < attack) {
            return smoothstep(0, attack, t);
        }
        // Gong has a slight swell after initial hit
        double decayT = t - attack;
        double swell = 1.0 + 0.15 * Math.sin(Math.PI * decayT / 0.3) * Math.exp(-decayT * 2);
        return swell * Math.exp(-decayT / decay);
    }
    
    private double getKalimbaEnvelope(double t, double attack, double decay) {
        if (t < attack) {
            return t / attack;
        }
        double decayT = t - attack;
        // Quick initial decay, then sustain
        return 0.7 * Math.exp(-decayT / (decay * 0.3)) + 0.3 * Math.exp(-decayT / decay);
    }
    
    private double getSwellEnvelope(double t, double attack, double total) {
        double fadeOut = 0.4;
        if (t < attack) {
            return smoothstep(0, attack, t);
        } else if (t < total - fadeOut) {
            return 1.0;
        } else {
            return 1.0 - smoothstep(0, fadeOut, t - (total - fadeOut));
        }
    }
    
    private double smoothstep(double edge0, double edge1, double x) {
        double t = Math.max(0, Math.min(1, (x - edge0) / (edge1 - edge0)));
        return t * t * (3 - 2 * t);
    }
    
    // ========================
    // Audio Utility Functions
    // ========================
    
    private short toShort(double sample) {
        // Soft clipping for smoother distortion if overdriven
        if (sample > 1.0) {
            sample = 1.0 - Math.exp(-(sample - 1.0));
        } else if (sample < -1.0) {
            sample = -1.0 + Math.exp(-(-sample - 1.0));
        }
        return (short) (sample * Short.MAX_VALUE * 0.9);
    }
    
    private short[] applyFades(short[] samples) {
        int fadeLength = Math.min(SAMPLE_RATE / 100, samples.length / 10); // 10ms or 10% of length
        
        // Fade in
        for (int i = 0; i < fadeLength; i++) {
            double factor = (double) i / fadeLength;
            samples[i] = (short) (samples[i] * factor);
        }
        
        // Fade out
        for (int i = 0; i < fadeLength; i++) {
            double factor = (double) i / fadeLength;
            int idx = samples.length - 1 - i;
            samples[idx] = (short) (samples[idx] * factor);
        }
        
        return samples;
    }
    
    private short[] applySmoothing(short[] samples) {
        short[] smoothed = new short[samples.length];
        smoothed[0] = samples[0];
        smoothed[samples.length - 1] = samples[samples.length - 1];
        
        for (int i = 1; i < samples.length - 1; i++) {
            smoothed[i] = (short) ((samples[i - 1] + samples[i] * 2 + samples[i + 1]) / 4);
        }
        
        return smoothed;
    }
    
    private void playAudioSamples(short[] samples) {
        stopPlayback();
        
        int bufferSize = AudioTrack.getMinBufferSize(
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT
        );
        
        audioTrack = new AudioTrack.Builder()
            .setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build())
            .setAudioFormat(new AudioFormat.Builder()
                .setSampleRate(SAMPLE_RATE)
                .setEncoding(AUDIO_FORMAT)
                .setChannelMask(CHANNEL_CONFIG)
                .build())
            .setBufferSizeInBytes(Math.max(bufferSize, samples.length * 2))
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build();
        
        audioTrack.write(samples, 0, samples.length);
        audioTrack.play();
        isPlaying = true;
        
        // Set up completion listener to release resources
        audioTrack.setNotificationMarkerPosition(samples.length);
        audioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
            @Override
            public void onMarkerReached(AudioTrack track) {
                stopPlayback();
            }
            
            @Override
            public void onPeriodicNotification(AudioTrack track) {}
        });
    }
    
    /**
     * Stop any current playback and release resources.
     */
    public void stopPlayback() {
        if (audioTrack != null) {
            try {
                if (audioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
                    audioTrack.stop();
                }
                audioTrack.release();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
            audioTrack = null;
        }
        isPlaying = false;
    }
    
    /**
     * Release all resources.
     */
    public void release() {
        stopPlayback();
    }
}
