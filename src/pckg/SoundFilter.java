package pckg;

public class SoundFilter implements Filter {

    //scale of the clip
    private double volScale;

    //stores the sound in an AudioClip
    private Sound sound;

    private AudioClip clip;

    /**
     * constructor
     * @param volScale
     */
    SoundFilter(double volScale) {
        this.volScale = volScale;
    }

    /**
     * "setter" for the filter
     * @param input
     */
    @Override
    public void connectInput(Sound input) {
        this.sound = input;
        this.clip = input.getAudio();
    }

    /**
     * @return a scaled version of the AudioClip
     */
    @Override
    public AudioClip getAudio() {
        AudioClip ret = new AudioClip();

        for (int i = 0; i < clip.getSampleRate(); i++) {
            ret.setSample(i, (int) (clip.getSample(i) * volScale));
        }

        return ret;
    }


    public void setScale(int scale) {
        this.volScale = scale;
    }
}
