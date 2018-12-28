package pckg;

public class Wave implements Sound{
    //frequency
    protected int freq;

    //AudioClip
    protected AudioClip clip;

    /**
     * constructor
     * @param freq
     */
    public Wave(int freq) {
        this.freq = freq;
        clip = new AudioClip();
    }


    public void setFreq (int freq) {
        this.freq = freq;
    }

    /**
     * @return modified AudioClip
     */
    @Override
    public AudioClip getAudio() {
        return clip;
    }
}
