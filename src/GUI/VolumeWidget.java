package GUI;

import pckg.*;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class VolumeWidget extends Widget {
    //stores the volume scale
    private double volScale;

    /**
     * constructor
     *
     * @param parent
     */
    public VolumeWidget(Synthesizer parent) {
        super("Volume control", parent);
        super.setInput();
        volScale = 1;
        HBox stats = new HBox();
        stats.setAlignment(Pos.CENTER);

        //sets up the controls and input
        TextField text = new TextField("1");
        text.setMaxWidth(50);

        Slider slide = new Slider(0, 10, 1);
        slide.setShowTickLabels(true);
        slide.setShowTickMarks(true);
        slide.setMajorTickUnit(1);
        slide.setBlockIncrement(1);
        slide.valueProperty().addListener(e -> {
            System.out.println(slide.getValue());
            this.volScale = slide.getValue();
            text.setText(String.format("%.2f", slide.getValue()));
        });


        text.setOnAction(e -> {
            slide.setValue(Double.parseDouble(text.getText()));
        });

        stats.getChildren().addAll(text, slide);
        super.setControl(stats);
    }

    /**
     * must override to get updated version of the audio
     *
     * @return
     */
    @Override
    public AudioClip getAudio() {
        //checks whether to use this audio clip or get its parent
        AudioClip clip = this.cameFrom == null ? this.getAudio() : this.cameFrom.getAudio();
        AudioClip ret = new AudioClip();

        //updates the audio clip
        for (int i = 0; i < clip.getSampleRate(); i++) {
            ret.setSample(i, (int) (clip.getSample(i) * volScale));
        }

        return ret;
    }
}
