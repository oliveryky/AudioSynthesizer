package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pckg.AudioClip;

import java.util.ArrayList;

public class MMixerWidget extends Widget {
    //the mixer's scale
    private double scale;

    //list of all the cables connect to its input
    private ArrayList<Cable> cables;

    /**
     * constructor
     *
     * @param parent
     */
    public MMixerWidget(Synthesizer parent) {
        super("Multiplicative Mixer", parent);
        cables = new ArrayList<>();

        super.setInput();
        scale = 1;
        HBox stats = new HBox();
        stats.setAlignment(Pos.CENTER);

        TextField text = new TextField("1");
        text.setMaxWidth(50);

        Slider slide = new Slider(0, 100, 1);
        slide.setShowTickLabels(true);
        slide.setShowTickMarks(true);
        slide.setMajorTickUnit(10);
        slide.setBlockIncrement(20);
        slide.valueProperty().addListener(e -> {
            System.out.println(slide.getValue());
            this.scale = slide.getValue();
            text.setText(String.format("%.2f", slide.getValue()));
        });


        text.setOnAction(e -> {
            slide.setValue(Double.parseDouble(text.getText()));
        });

        stats.getChildren().addAll(text, slide);
        super.setControl(stats);
    }

    /**
     * must override to also store the cables
     * stores each input cable
     *
     * @param cable
     */
    @Override
    public void setCameFrom(Cable cable) {
        super.setCameFrom(cable);
        cables.add(cable);
    }

    /**
     * must overrider to get the updated version of each audio clip
     * from each cable that is connected to its input jack
     *
     * @return
     */
    @Override
    public AudioClip getAudio() {
        ArrayList<AudioClip> clips = new ArrayList<>();

        //gets the audio from each cable
        //this way all sounds will be updated by its appropriate filters
        for (Cable c : cables) {
            clips.add(c.getAudio());
        }

        AudioClip ret = new AudioClip();
        for(int i = 0; i < ret.getSampleRate(); i++) {
            ret.setSample(i, 1);
        }

        for (AudioClip c : clips) {
            for (int i = 0; i < c.getSampleRate(); i++) {
                ret.setSample(i, (ret.getSample(i) * c.getSample(i)) * (int) scale);
            }
        }

        return ret;
    }
}
