package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

import pckg.*;

/**
 * Digital Synthesizer with GUI using JavaFX
 *
 * @author Oliver Yu
 * @version October 4, 2018
 */
public class Synthesizer extends Application {
    private final double OFFSET = 25;

    //label for workspace
    private Label titleLabel;

    //box to store play, reset buttons and title
    private HBox bottom, titleBox;

    //box to store library
    private VBox library;

    //various buttons
    private Button play, reset, genS, genF, genM;

    //current cable that is being drawn on the screen
    private Cable currentCable;

    //speak from which sound is produced
    private Speaker speaker;

    //workspace to add and connect widgets
    private Pane workSpace;

    //layout of the GUI
    private BorderPane layout;
    private Scene scene;

    //number of times to loop the sound
    private TextField loopInput;
    private int loopNum;

    //stores all the cables that is present on the screen so it can be updated
    private ArrayList<Cable> cables;

    //stores all the widgets current on the screen
    private ArrayList<Widget> widgets;

    @Override
    public void start(Stage primaryStage) {
        //some initialization
        speaker = new Speaker(this);
        widgets = new ArrayList<>();
        widgets.add(speaker);
        cables = new ArrayList<>();

        //setup
        setUpBot();

        setUpTop();

        initializeWorkSpace();

        setUpLibrary();

        layout = new BorderPane();
        layout.setCenter(workSpace);
        layout.setBottom(bottom);
        layout.setTop(titleBox);
        layout.setRight(library);

        scene = new Scene(layout, 1080, 640);
        primaryStage.setTitle("Synthesizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * initializes the title label/box so it can be added to the layout
     */
    private void setUpTop() {
        titleLabel = new Label("Workspace");

        titleBox = new HBox();
        titleBox.setPadding(new Insets(8, 100, 0, 100));
        titleBox.getChildren().add(titleLabel);
    }

    /**
     * initializes the bottom play and reset buttons so it can be added to the layout
     */
    private void setUpBot() {
        play = new Button();
        play.setText("Play");
        play.setOnAction(e -> {
            if (speaker.getSound() != null) {
                playSound(speaker.getAudio());
            }
        });

        reset = new Button();
        reset.setText("Reset");
        reset.setOnAction(e -> {
            workSpace.getChildren().clear();
            widgets.clear();

            //re-add speaker to the the current widgets on screen
            widgets.add(speaker);
            cables.clear();
        });

        loopNum = 0;
        loopInput = new TextField();
        loopInput.setMaxWidth(50);
        loopInput.setOnAction(e -> {
            loopNum = Integer.parseInt(loopInput.getText());
        });

        bottom = new HBox();
        bottom.setPadding(new Insets(0, 0, 12, 12));
        bottom.getChildren().addAll(play, new Rectangle(10, 10, Color.TRANSPARENT), loopInput,
                new Rectangle(20, 10, Color.TRANSPARENT), reset);
    }

    /**
     * creates and library (combo-boxes) to choose which widgets to add to the screen
     */
    private void setUpLibrary() {
        library = new VBox(5);
        library.setPadding(new Insets(20, 15, 0, 15));
        library.setPrefWidth(200);

        //sound selection
        ComboBox<String> sound = new ComboBox<>();
        sound.setPrefWidth(library.getPrefWidth());
        sound.setPromptText("Select sound");
        sound.getItems().addAll("Sine Wave", "Square Wave", "White Noise");
        sound.setOnAction(e -> {
            generateWidget(sound.getValue());
        });

        genS = new Button("Generate");
        genS.setOnAction(e -> {
            generateWidget(sound.getValue());
        });

        //filter selection
        ComboBox<String> filter = new ComboBox<>();
        filter.setPrefWidth(library.getPrefWidth());
        filter.setPromptText("Select filter");
        filter.getItems().addAll("Volume", "Linear Ramp");
        filter.setOnAction(e -> {
            generateWidget(filter.getValue());
        });

        genF = new Button("Generate");
        genF.setOnAction(e -> {
            generateWidget(filter.getValue());
        });

        //mixer selection
        ComboBox<String> mixer = new ComboBox<>();
        mixer.setPrefWidth(library.getPrefWidth());
        mixer.setPromptText("Select mixer");
        mixer.getItems().addAll("Additive Mixer", "Multiplicative Mixer");
        mixer.setOnAction(e -> {
            generateWidget(mixer.getValue());
        });

        genM = new Button("Generate");
        genM.setOnAction(e -> {
            generateWidget(mixer.getValue());
        });

        //add all option boxes to the library container
        library.getChildren().addAll(sound, genS, new Rectangle(library.getPrefWidth(), 10, Color.TRANSPARENT),
                filter, genF, new Rectangle(library.getPrefWidth(), 10, Color.TRANSPARENT), mixer, genM,
                new Rectangle(library.getPrefWidth(), 50, Color.TRANSPARENT), speaker);
    }

    private void generateWidget(String widgetName) {
        if (widgetName == null) return;

        Widget widget;

        if (widgetName.equals("Sine Wave")) {
            widget = new SineWidget(this);
        } else if (widgetName.equals("Square Wave")) {
            widget = new SquareWidget(this);
        } else if (widgetName.equals("White Noise")) {
            widget = new WtNoiseWidget(this);
        } else if (widgetName.equals("Volume")) {
            widget = new VolumeWidget(this);
        } else if (widgetName.equals("Linear Ramp")) {
            widget = new LRWidget(this);
        } else if (widgetName.equals("Additive Mixer")) {
            widget = new AMixerWidget(this);
        } else if (widgetName.equals("Multiplicative Mixer")) {
            widget = new MMixerWidget(this);
        } else {
            widget = new SineWidget(this);
        }

        workSpace.getChildren().add(widget);
        widgets.add(widget);
    }

    /**
     * IMPORTANT
     * sets up the movement mechanics of the workspace
     */
    private void initializeWorkSpace() {
        workSpace = new Pane();

        //what happens when the mouse button is pressed
        workSpace.setOnMousePressed(e -> {
            for (Widget w : widgets) {
                //check each widget to see if the mouse is inside its output jack circle
                if (w.isOutput(e.getTarget())) {
                    //if its position is valid draw a new line on the screen
                    Cable temp = new Cable(e.getSceneX(), e.getSceneY() - OFFSET, e.getSceneX(), e.getSceneY() - OFFSET, w);
                    //set the current cable on the screen to the new line
                    currentCable = temp;
                    //add line to workspace so it can be displayed
                    workSpace.getChildren().add(currentCable);
                    break;
                }
            }
        });

        workSpace.setOnMouseDragged(e -> {
            if (currentCable != null) {
                //event only recognized if a current cable is being drawn
                currentCable.setEndX(e.getSceneX());
                currentCable.setEndY(e.getSceneY() - OFFSET);
            }
        });

        workSpace.setOnMouseReleased(e -> {
            //only recognize mouse release events if a cable is being drawn
            if (currentCable != null) {
                for (Widget w : widgets) {
                    //go through all widgets and check if the ending position is within a input jack
                    if (w.isInput(e.getSceneX(), e.getSceneY())) {
                        //create a new cable that starts at the center of both jacks
                        Cable cable = new Cable(currentCable.getCameFrom().getOutputPoint().getX(),
                                currentCable.getCameFrom().getOutputPoint().getY() - OFFSET,
                                w.getInputPoint().getX(), w.getInputPoint().getY() - OFFSET, currentCable.getCameFrom());
                        //set update the cable with information of where it came from and where it's going
                        cable.setOutput(w);

                        //remove the cable being drawn
                        workSpace.getChildren().remove(workSpace.getChildren().lastIndexOf(currentCable));
                        //add the new "centered" cable
                        workSpace.getChildren().add(cable);
                        //finally add it to the list keep track of all valid cables in workspace
                        cables.add(cable);
                        w.setSound(cable.getSound());
                        w.setCameFrom(cable);
                        //set current cable to null
                        currentCable = null;
                        return;
                    }
                }

                //only reached if ending position is not valid
                int lastIdx = workSpace.getChildren().lastIndexOf(currentCable);
                if (lastIdx > 0) {
                    //delete cable from screen b/c it's not valid
                    workSpace.getChildren().remove(workSpace.getChildren().lastIndexOf(currentCable));
                }
            }
        });
    }

    /**
     * called when a widget is moved
     * updates the position of the cables so that it follows the widgets
     */
    public void updateCable() {
        for (Cable c : cables) {
            c.setStartX(c.getCameFrom().getOutputPoint().getX());
            c.setStartY(c.getCameFrom().getOutputPoint().getY() - OFFSET);
            c.setEndX(c.getOutPut().getInputPoint().getX());
            c.setEndY(c.getOutPut().getInputPoint().getY() - OFFSET);
        }
    }

    /**
     * plays the audio clip
     */
    private void playSound(AudioClip audio) {
        try (Clip c = AudioSystem.getClip()) {
            AudioFormat format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);

            c.open(format16, audio.getBytes(), 0, audio.getBytes().length);
            System.out.println("About to play");
            c.start();
            c.loop(loopNum);
            while (c.getFramePosition() < audio.getSampleRate() || c.isActive() || c.isRunning()) {
            }
            System.out.println("done");

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
