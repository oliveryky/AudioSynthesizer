package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pckg.*;

public class SineWidget extends Widget {
    //stores a sinewave sound
    private SineWave wave;

    /**
     * constructor
     *
     * @param parent
     */
    public SineWidget(Synthesizer parent) {
        super("Sinewave Generator", parent);
        this.wave = new SineWave();
        super.setSound(wave);

        //creates a control panel to be added to the super's control pane
        HBox stats = new HBox();
        stats.setPrefWidth(50);
        stats.setAlignment(Pos.CENTER);

        //text field that displays current freq and any input updates the freq
        TextField text = new TextField("440");

        //can also adjust the freq from the slider
        Slider slide = new Slider(0, 880, 440);
        slide.setShowTickLabels(true);
        slide.setShowTickMarks(true);
        slide.setMajorTickUnit(100);
        slide.setMinorTickCount(5);
        slide.setBlockIncrement(200);
        slide.valueProperty().addListener(e -> {
            System.out.println(slide.getValue());
            wave.setFreq((int) slide.getValue());
            text.setText(String.format("%.2f", slide.getValue()));
        });


        text.setOnAction(e -> {
            slide.setValue(Double.parseDouble(text.getText()));
        });

        stats.getChildren().add(text);
        super.setMoveableLeft(stats);
        super.setControl(slide);
    }

    @Override
    public AudioClip getAudio() {
        return this.wave.getAudio();
    }
}
