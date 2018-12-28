package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import pckg.*;

public class LRWidget extends Widget {
    //stores the volume scale
    private double linearScale, increment;

    /**
     * constructor
     *
     * @param parent
     */
    public LRWidget(Synthesizer parent) {
        super("Linear Ramp", parent);
        super.setInput();
        linearScale = 1;
        increment = 0.25;
        VBox stats = new VBox();
        stats.setAlignment(Pos.CENTER);

        //sets up the controls and input

        Slider scaleAdjuster = new Slider(-880, 880, 440);
        scaleAdjuster.setShowTickLabels(true);
        scaleAdjuster.setShowTickMarks(true);
        scaleAdjuster.setMajorTickUnit(400);
        scaleAdjuster.setMinorTickCount(5);
        scaleAdjuster.setBlockIncrement(400);
        scaleAdjuster.valueProperty().addListener(e -> {
            System.out.println(scaleAdjuster.getValue());
            this.linearScale = scaleAdjuster.getValue();
        });

        Slider incrementAdjuster = new Slider(-0.5, 0.5, 0.25);
        incrementAdjuster.setShowTickLabels(true);
        incrementAdjuster.setShowTickMarks(true);
        incrementAdjuster.setMajorTickUnit(0.1);
        incrementAdjuster.setMinorTickCount(5);
        incrementAdjuster.setBlockIncrement(0.1);
        incrementAdjuster.valueProperty().addListener(e -> {
            System.out.println(incrementAdjuster.getValue());
            this.increment = incrementAdjuster.getValue();
        });


        stats.getChildren().addAll(scaleAdjuster, incrementAdjuster);
        super.setControl(scaleAdjuster);
        super.setControl(incrementAdjuster);
    }

    /**
     * @return returns emptySound of set freq so that linear ramp can be played without an input cable
     */
    @Override
    public Sound getSound() {
        return cameFrom == null ? new EmptySound(linearScale) : cameFrom.getSound();
    }

    /**
     * must override to get updated version of the audio
     *
     * @return
     */
    @Override
    public AudioClip getAudio() {
        //checks whether to use this audio clip or get its parent
        AudioClip clip = this.cameFrom == null ? this.getSound().getAudio() : this.cameFrom.getAudio();
        AudioClip ret = new AudioClip();

        System.out.println(linearScale);

        double phase = 0;
        double tempFreq = linearScale;
        //updates the audio clip
        for (int i = 0; i < clip.getSampleRate(); i++, tempFreq+=increment) {
            ret.setSample(i, clip.getSample(i) + (int) (Short.MAX_VALUE * Math.sin(phase)));
            phase += (2* Math.PI * tempFreq)/clip.getSampleRate();
        }

        return ret;
    }
}
