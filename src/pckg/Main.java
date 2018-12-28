package pckg;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
//        SineWave wave = new SineWave();
//        playSound(wave);
//        playFilteredSound(wave, 10);
//        playMixedSound(wave, 10);

        SquareWave sqWave = new SquareWave(250);
        playSound(sqWave);
        playFilteredSound(sqWave, 10);

//        WhiteNoise noise = new WhiteNoise();
//        playSound(noise);
//        playFilteredSound(noise, 20);
    }

    public static void playSound(Sound sound) {
        try (Clip c = AudioSystem.getClip()) {
            AudioFormat format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);

            c.open(format16, sound.getAudio().getBytes(), 0, sound.getAudio().getBytes().length);
            System.out.println("About to play");
            c.start();
            c.loop(2);
            while (c.getFramePosition() < sound.getAudio().getSampleRate() || c.isActive() || c.isRunning()) {
            }
            System.out.println("done");

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playFilteredSound(Sound sound, double val) {
        try (Clip c = AudioSystem.getClip()) {
            AudioFormat format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);

            SoundFilter genF = new SoundFilter(val);
            genF.connectInput(sound);

            AudioClip clip = genF.getAudio();

            c.open(format16, clip.getBytes(), 0, clip.getBytes().length);
            System.out.println("About to play");
            c.start();
            c.loop(2);
            while (c.getFramePosition() < clip.getSampleRate() || c.isActive() || c.isRunning()) {
            }
            System.out.println("done");

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playMixedSound(Sound sound, int sNum) {
        try (Clip c = AudioSystem.getClip()) {
            AudioFormat format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);

            //scales the sound
            SoundFilter scaledSound = new SoundFilter(1 / (double) sNum);
            scaledSound.connectInput(sound);

            SoundMixer mix = new SoundMixer();

            for (int i = 0; i < sNum; i++) {
                mix.addInput(scaledSound);
            }

            AudioClip clip = mix.getAudio();

            c.open(format16, clip.getBytes(), 0, clip.getBytes().length);
            System.out.println("About to play");
            c.start();
            c.loop(2);
            while (c.getFramePosition() < clip.getSampleRate() || c.isActive() || c.isRunning()) {
            }
            System.out.println("done");

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
