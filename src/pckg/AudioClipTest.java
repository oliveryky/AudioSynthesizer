package pckg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {
    AudioClip clip;

    @BeforeEach
    void setUp() {
        clip = new AudioClip();
    }

    @Test
    void testConstructor() {
        assertEquals(1.0, clip.getDuration());
        assertEquals(44100, clip.getSampleRate());
        assertEquals(88200, clip.getBytes().length);
    }

    @Test
    void testSetSample() {
        clip.setSample(0, Short.MIN_VALUE);
        clip.setSample(1, Short.MAX_VALUE);
        byte[] test = clip.getBytes();
        assertEquals(0, test[0]);
        assertEquals(-128, test[1]);

        assertEquals(-1, test[2]);
        assertEquals(127, test[3]);
    }

    @Test
    void testSetSample2() {
        clip.setSample(5, -16400);
        clip.setSample(40000, 16400);
        byte[] test = clip.getBytes();
        assertEquals(-16, test[10]);
        assertEquals(-65, test[11]);

        assertEquals(16, test[80000]);
        assertEquals(64, test[80001]);
    }

    @Test
    void testGetSample() {
        clip.setSample(0, Short.MIN_VALUE);
        clip.setSample(1, Short.MAX_VALUE);

        assertEquals(Short.MIN_VALUE, clip.getSample(0));
        assertEquals(Short.MAX_VALUE, clip.getSample(1));
        clip.setSample(2, 255);
        assertEquals(255, clip.getSample(2));
    }

    @Test
    void testGetSample2() {
        int num = Short.MIN_VALUE;
        for (int i = 0; i < Short.MAX_VALUE + 1; i++, num++) {
            clip.setSample(i, num);
            assertEquals(num, clip.getSample(i));
        }
    }

    @Test
    void testGetSample3() {
        for (int i = 0; i <= Short.MAX_VALUE; i++) {
            clip.setSample(i, i);
            assertEquals(i, clip.getSample(i));
        }
    }

}