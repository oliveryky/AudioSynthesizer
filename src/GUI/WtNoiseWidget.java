package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pckg.*;

public class WtNoiseWidget extends Widget {
    //stores a white noise sound
    private WhiteNoise noise;

    public WtNoiseWidget(Synthesizer parent) {
        super("White Noise Generator", parent);
        this.setPadding(new Insets(10, 5, 5, 5));
        this.noise = new WhiteNoise();
        super.setSound(noise);

        HBox stats = new HBox();
        stats.setPrefWidth(75);
        stats.setAlignment(Pos.CENTER);
        super.setMoveableLeft(stats);
    }
}
