package GUI;

import javafx.event.EventTarget;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pckg.*;

/**
 * abstract widget class is a type of BorderPane
 */
abstract class Widget extends BorderPane {
    //the sound this widget is storing
    protected Sound sound;

    //circle representing the output jack
    private Circle output;

    //circle representing the input jack
    protected Circle input;

    //the cable connected to the input jack (if present)
    protected Cable cameFrom;

    //center control pane
    private GridPane control;

    /*
       IMPORTANT
       the center pane of the widget that records movement
     */
    private BorderPane moveable;

    //the next to add a control slide
    private int row;

    //keeps track of mouse movement
    private double startX, startY, transX, transY;

    /**
     * constructor,
     *
     * @param name
     * @param parent
     */
    protected Widget(String name, Synthesizer parent) {
        super();
        moveable = new BorderPane();
        moveable.setMaxSize(300, 75);
        moveable.setPadding(new Insets(0, 5, 0, 5));

        //records the mouse events on the "moveable" pane
        //bottom layer "Widget" BorderPane does not respond to events
        moveable.setOnMousePressed(e -> {
            //if mouse is pressed on the moveable portion, the move the the Widget's pane
            startX = e.getSceneX();
            startY = e.getSceneY();
            transX = this.getTranslateX();
            transY = this.getTranslateY();
        });

        //drag event
        moveable.setOnMouseDragged(e -> {
            this.setTranslateX(transX + e.getSceneX() - startX);
            this.setTranslateY(transY + e.getSceneY() - startY);

            //once a valid mouse drag even is detected, update the cables connecting it
            parent.updateCable();
        });

        this.setMaxSize(350, 75);
        this.setPadding(new Insets(10, 5, 0, 5));

        Label title = new Label(name);

        //all elements except the input/output jack is stored on the moveable pane
        output = new Circle(20);
        output.setFill(Color.AQUA);
        control = new GridPane();
        moveable.setTop(title);
        moveable.setCenter(control);

        this.setCenter(moveable);
        this.setRight(output);
        this.setStyle("-fx-border-color: black");
    }

    /**
     * @param endX
     * @param endY
     * @return true if the x and y coord are within the bounds of the input jack
     */
    public boolean isInput(double endX, double endY) {
        if (input == null) {
            return false;
        }

        Bounds boundIS = input.localToScene(input.getBoundsInLocal());

        return (boundIS.getMinX() <= endX && endX <= boundIS.getMaxX()) && (boundIS.getMinY() <= endY && endY <= boundIS.getMaxY());
    }

    /**
     * @param target
     * @return truen if the event target is the output jack
     */
    public boolean isOutput(EventTarget target) {
        return output.equals(target);
    }

    /**
     * adds a slider (or any Node) control to the center control pane,
     * and updates the next available row position
     *
     * @param node
     */
    protected void setControl(Node node) {
        this.control.add(node, 0, row++);
        control.setAlignment(Pos.CENTER);
    }

    /**
     * adds a Node to the moeveable pane's left section
     *
     * @param node
     */
    protected void setMoveableLeft(Node node) {
        moveable.setLeft(node);
    }

    /**
     * adds an circle representing the input jack to the left of the Widget
     */
    protected void setInput() {
        this.input = new Circle(20);
        this.setLeft(input);
    }

    /**
     * stores the cable that is connected to this widget's input jack
     *
     * @param cable
     */
    public void setCameFrom(Cable cable) {
        this.cameFrom = cable;
    }

    /**
     * sets sound stored in this widget
     *
     * @param sound
     */
    protected void setSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * @return a Point2D object containing the center coordinates of the input jack
     */
    public Point2D getInputPoint() {
        Bounds boundIS = input.localToScene(input.getBoundsInLocal());

        return new Point2D(boundIS.getMinX() + input.getRadius(), boundIS.getMinY() + input.getRadius());
    }

    /**
     * @return a Point2D object containing the center coordinates of the output jack
     */
    public Point2D getOutputPoint() {
        Bounds boundIS = output.localToScene(output.getBoundsInLocal());

        return new Point2D(boundIS.getMinX() + output.getRadius(), boundIS.getMinY() + output.getRadius());
    }

    /**
     * @return the audio clip of the sound
     */
    public AudioClip getAudio() {
        return cameFrom == null ? this.sound.getAudio() : cameFrom.getAudio();
    }

    /**
     * @return sound that is stored in this widget
     */
    public Sound getSound() {
        return cameFrom == null ? this.sound : cameFrom.getSound();
    }
}
