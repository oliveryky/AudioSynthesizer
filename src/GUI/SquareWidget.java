package GUI;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pckg.*;

public class SquareWidget extends Widget {
    //stores a square wave sound
    private SquareWave wave;

    /**
     * constructor
     *
     * @param parent
     */
    public SquareWidget(Synthesizer parent) {
        super("Squarewave Generator", parent);
        this.wave = new SquareWave();
        super.setSound(wave);

        //controls
        HBox stats = new HBox();
        stats.setPrefWidth(50);

        //display and update freq
        TextField text = new TextField("440");

        //update freq
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
