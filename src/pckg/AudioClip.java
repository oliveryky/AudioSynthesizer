package pckg;

public class AudioClip {
    //duration of the data
    private double duration;

    //samples per second
    private int sampleRate;

    //stores each short as 2 bytes
    private byte[] data;

    /**
     * Constructor
     */
    public AudioClip() {
        duration = 1.0;
        sampleRate = 44100;
        data = new byte[2 * (sampleRate * (int) duration)];
    }

    /**
     * @return duration (sec)
     */
    public double getDuration() {
        return duration;
    }

    /**
     * @return samples/sec
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * @return array storing bytes
     */
    public byte[] getBytes() {
        return data;
    }

    /**
     * constructs an int in the range of short MIN/MAX from 2 bytes
     * little endian
     *
     * @param idx
     * @return
     */
    public int getSample(int idx) {
        return data[2 * idx + 1] << 8 | 0xFF & data[2 * idx];

    }

    /**
     * splits an int into 2 bytes
     * little endian
     *
     * @param idx
     * @param val
     */
    public void setSample(int idx, int val) {
        if (val > Short.MAX_VALUE) val = Short.MAX_VALUE;
        if (val < Short.MIN_VALUE) val = Short.MIN_VALUE;

        data[2 * idx] = (byte) (val);
        data[2 * idx + 1] = (byte) (val >> 8);
    }
}
