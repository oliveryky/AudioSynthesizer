package pckg;

public class SineWave extends Wave {

    /**
     * constructor
     */
    public SineWave() {
        this(440);
    }

    /**
     * constructor
     * @param freq
     */
    public SineWave(int freq) {
        super(freq);
    }

    /**
     * @return sine wave AudioClip
     */
    @Override
    public AudioClip getAudio() {
        for(int i = 0; i < clip.getSampleRate(); i++) {
            clip.setSample(i, (int) (Short.MAX_VALUE * Math.sin((2 * Math.PI * freq * i)/clip.getSampleRate())));
        }
        return clip;
    }
}
