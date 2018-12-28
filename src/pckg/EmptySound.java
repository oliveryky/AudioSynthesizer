package pckg;

public class EmptySound implements Sound {
    private AudioClip ret;
    private double freq;

    public EmptySound(double freq) {
        ret = new AudioClip();
        this.freq = freq;
        for(int i = 0; i < ret.getSampleRate(); i++) {
            ret.setSample(i, (int) this.freq);
        }
    }
    @Override
    public AudioClip getAudio() {
        return ret;
    }
}
