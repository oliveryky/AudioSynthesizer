package GUI;

import javafx.scene.shape.Line;
import pckg.*;

public class Cable extends Line {
    //keeps track of which widget the cable came from and the widget it's going to
    private Widget cameFrom, outPut;

    /**
     * constructor
     *
     * @param startX   line start x
     * @param startY   line start y
     * @param endX     line end x
     * @param endY     line end y
     * @param cameFrom the widget that this line is being drawn from
     */
    public Cable(double startX, double startY, double endX, double endY, Widget cameFrom) {
        super(startX, startY, endX, endY);
        this.cameFrom = cameFrom;
    }

    /**
     * setter for where this cable is going
     *
     * @param output
     */
    public void setOutput(Widget output) {
        this.outPut = output;
    }

    /**
     * returns which widget this cable is coming from
     *
     * @return
     */
    public Widget getCameFrom() {
        return cameFrom;
    }

    /**
     * @return the sound from the widget that this cable is extending from
     */
    public Sound getSound() {
        return this.cameFrom.getSound();
    }

    /**
     * @return the audio clip from the widget that this cable is extending from
     */
    public AudioClip getAudio() {
        return this.cameFrom.getAudio();
    }

    /**
     * @return the widget that this cable is extending to
     */
    public Widget getOutPut() {
        return this.outPut;
    }
}
