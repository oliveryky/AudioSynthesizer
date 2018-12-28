package GUI;

import javafx.geometry.Insets;
import javafx.scene.shape.Circle;

public class Speaker extends Widget {

    /**
     * constructor
     *
     * @param parent
     */
    public Speaker(Synthesizer parent) {
        //makes the widget just one big input jack
        super("Speaker", parent);
        this.setTop(null);
        this.setRight(null);
        this.setCenter(null);
        this.setMaxSize(75, 75);
        this.setPadding(new Insets(0));
        this.setStyle(null);
        this.setInput();
    }

    /**
     * override so that the entire widget is immovable
     */
    @Override
    protected void setInput() {
        this.input = new Circle(75);
        this.setLeft(input);
    }
}
