package pckg;

public class SquareWave extends Wave {

    /**
     * constructor
     */
    public SquareWave() {
        this(440);
    }

    /**
     * constructor
     * @param freq
     */
    public SquareWave (int freq) {
        super(freq);
    }

    /**
     * @return square wave audio clip
     */
    @Override
    public AudioClip getAudio() {
        double sRate = clip.getSampleRate();
        for(int i = 0; i < sRate; i++) {
            if(((freq * i / sRate) % 1) > 0.5) {
                clip.setSample(i, Short.MAX_VALUE);
            }else {
                clip.setSample(i, Short.MIN_VALUE);
            }
        }

        return clip;
    }

}
