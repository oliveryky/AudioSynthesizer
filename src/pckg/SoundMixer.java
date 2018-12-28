package pckg;

import java.util.ArrayList;

public class SoundMixer implements Mixer {
    //arrayList to store all AudioClip objects to be mixed
    private ArrayList<Sound> sounds;
    private int scale;

    /**
     * constructor
     */
    SoundMixer() {
        sounds = new ArrayList<>();
    }

    /**
     * adds an sound to the mix list
     * @param input
     */
    @Override
    public void addInput(Sound input) {
        sounds.add(input);
    }

    public void setFreq(int scale) {
        this.scale = scale;
    }

    /**
     * mixes the audio clips together
     * @return filtered AudioClip
     */
    @Override
    public AudioClip getAudio() {
        AudioClip ret = new AudioClip();
        AudioClip clip;

        for (Sound s : sounds) {
            clip = s.getAudio();
            for (int i = 0; i < clip.getSampleRate(); i++) {
                ret.setSample(i, (ret.getSample(i) + clip.getSample(i)));
            }
        }

        return ret;
    }
}
