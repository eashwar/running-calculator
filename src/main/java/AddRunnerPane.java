import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddRunnerPane extends GridPane {
    //UI ELEMENTS
    private Label name_label;
    private Label minutes_label;
    private Label seconds_label;
    private Label distance_label;

    private final TextField name = new TextField();
    private final TextField minutes = new TextField();
    private final TextField seconds = new TextField();
    private final TextField distance = new TextField();
    private final TextField pace = new TextField();

    private Button calculateBtn = new Button();
    private HBox hbBtn;


    public AddRunnerPane(){
        initializeUIElements();
        constructGrid();
    }

    private void initializeUIElements()
    {
        name_label = new Label("Name: ");
        name.setPromptText("Name of runner");

        minutes_label = new Label("Total Minutes:");
        minutes.setPromptText("minutes");

        seconds_label = new Label("Total Seconds: ");
        seconds.setPromptText("seconds");

        distance_label = new Label("Total Distance: ");
        distance.setPromptText("miles");

        pace.setPrefColumnCount(40);
        pace.setPromptText("Pace per mile will appear here.");
        pace.setEditable(false);

        calculateBtn.setText("Calculate");
        calculateBtn.setOnAction(new EventHandler<ActionEvent>() {


            public void handle(ActionEvent event) {
                Runner p1 = new Runner(name.getText(), Integer.parseInt(seconds.getText()), Integer.parseInt(minutes.getText()), Double.parseDouble(distance.getText()));

                pace.setText(p1.getName() + "'s pace was " + p1.getPpmMinutes() + " minutes and " + p1.getPpmSeconds() + " seconds!");
            }
        });
        calculateBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ev) {
                if (ev.getCode() == KeyCode.ENTER) {
                    calculateBtn.fire();
                    ev.consume();
                }
            }
        });
        hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(calculateBtn);

    }

    private void constructGrid()
    {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(name_label, 0, 1);
        add(name, 1, 1);

        add(minutes_label, 0, 2);
        add(minutes, 1, 2);

        add(seconds_label, 0, 3);
        add(seconds, 1, 3);

        add(distance_label, 0, 4);
        add(distance, 1, 4);

        add(hbBtn, 0, 5, 2, 1);

        add(pace, 0, 6, 2, 1);

    }
}
