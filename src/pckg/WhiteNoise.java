package pckg;

import java.util.Random;

public class WhiteNoise extends Wave {

    /**
     * constructor
     */
    public WhiteNoise() {
        this(440);
    }

    /**
     * constructor
     * @param freq
     */
    public WhiteNoise(int freq) {
        super(freq);
    }

    /**
     * @return white noise audio clip
     */
    @Override
    public AudioClip getAudio() {
        int sRate = clip.getSampleRate();
        Random rand = new Random();

        for (int i = 0; i < sRate; i++) {
            clip.setSample(i, rand.nextInt(Short.MAX_VALUE + 1 - Short.MIN_VALUE) + Short.MIN_VALUE);
        }

        return clip;
    }
}
