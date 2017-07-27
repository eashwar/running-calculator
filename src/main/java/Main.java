import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main extends Application{

    private Gson gson = new Gson();

    FileChooser fileChooser = new FileChooser();

    //UI ELEMENTS
    private Label name_label;
    private Label minutes_label;
    private Label seconds_label;
    private Label distance_label;

    private final TextField name = new TextField();
    private final TextField minutes = new TextField();
    private final TextField seconds = new TextField();
    private final TextField distance = new TextField();

    private Button calculateBtn = new Button();
    private HBox hbBtn;

    private Button saveBtn = new Button();
    private Button loadBtn = new Button();
    private Button deleteRowBtn = new Button();

    private GridPane AddRunnerPane = new GridPane();

    private GridPane RunnerTablePane = new GridPane();

    private TabPane tabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();


    private Tab runnerTab = new Tab();
    private Tab tableTab = new Tab();

    private final TableView<Runner> runnerTableView = new TableView<Runner>();

    private ObservableList<Runner> runnerObservableList = FXCollections.observableArrayList();

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        primaryStage.setTitle("Pace Calculator");

        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setInitialFileName("runner_table.csv");
        fileChooser.setInitialDirectory(new File((System.getProperty("user.home"))+"/Documents"));

        initializeUIElements(primaryStage);
        constructGrids();

        runnerTab.setText("Add Runners");
        runnerTab.setContent(AddRunnerPane);
        runnerTab.setClosable(false);

        tableTab.setText("Runner Table");
        constructTable(runnerObservableList);
        tableTab.setContent(RunnerTablePane);
        tableTab.setClosable(false);

        Scene scene = new Scene(root, 700, 500);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        tabPane.getTabs().add(tableTab);
        tabPane.getTabs().add(runnerTab);

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.getIcons().add( new Image( Main.class.getResourceAsStream( "running_calculator_icon.ico" )));
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    private void constructTable(ObservableList<Runner> runnerList)
    {
        runnerTableView.setEditable(true);
        runnerTableView.setMinSize(500, 300);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(50);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Runner, String>("nameProperty"));

        TableColumn ppmCol = new TableColumn("Tempo Pace");
        ppmCol.setMinWidth(70);
        ppmCol.setCellValueFactory(
                new PropertyValueFactory<Runner, String>("pacePerMileProperty"));

        runnerTableView.setItems(runnerList);
        runnerTableView.getColumns().setAll(nameCol, ppmCol);


    }
    private void initializeUIElements(Stage primaryStage)
    {
        name_label = new Label("Name: ");
        name.setPromptText("Name of runner");

        minutes_label = new Label("Total Minutes:");
        minutes.setPromptText("minutes");

        seconds_label = new Label("Total Seconds: ");
        seconds.setPromptText("seconds");

        distance_label = new Label("Total Distance: ");
        distance.setPromptText("miles");

        saveBtn.setText("Save to CSV");
        saveBtn.setAlignment(Pos.BOTTOM_LEFT);
        saveBtn.setOnAction(event -> {
            File outfile = fileChooser.showSaveDialog(primaryStage);
            if (outfile != null) {
                List<RunnerDTO> runnerDTOList = new ArrayList<>();
                for (Runner runner : runnerObservableList) {
                    RunnerDTO runnerDTO = new RunnerDTO();
                    runnerDTO.applyChangesFrom(runner);
                    runnerDTOList.add(runnerDTO);
                }
                String runnerJSON = gson.toJson(runnerDTOList);
                List<Map<String, String>> flatJson = JSONFlattener.parseJson(runnerJSON);
                CSVWriter.writeToFile(CSVWriter.getCSV(flatJson), outfile);
            }
        });

        loadBtn.setText("Load from CSV");
        loadBtn.setAlignment(Pos.BOTTOM_CENTER);
        loadBtn.setOnAction(event -> {
            try {
                File infile = fileChooser.showOpenDialog(primaryStage);
                if (infile != null) {
                    Reader in = new FileReader(infile);
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
                    ArrayList<Runner> runners = new ArrayList<Runner>();
                    for (CSVRecord record : records) {
                        String name = record.get(0);
                        String pacePerMile = record.get(1);
                        Runner runner = new Runner(name, pacePerMile);
                        runners.add(runner);
                    }
                    runners.remove(0);
                    runnerObservableList = FXCollections.observableArrayList(runners);
                    constructTable(runnerObservableList);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        calculateBtn.setText("Add Runner to Table");
        calculateBtn.setOnAction(event -> {
            Runner runner = new Runner(name.getText(), Integer.parseInt(seconds.getText()), Integer.parseInt(minutes.getText()), Double.parseDouble(distance.getText()));
            Alert confirmAddRunner = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAddRunner.setTitle("Confirm Add Runner");
            confirmAddRunner.setHeaderText("Add runner " + runner + " to the table?");

            Optional<ButtonType> result = confirmAddRunner.showAndWait();
            if (result.get() == ButtonType.OK){
                runnerObservableList.add(runner);
                name.clear();
                minutes.clear();
                seconds.clear();
                distance.clear();
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

        deleteRowBtn.setText("Remove Selected Row From Table");
        deleteRowBtn.setAlignment(Pos.BOTTOM_RIGHT);
        deleteRowBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                Runner currentRunner = runnerTableView.getSelectionModel().getSelectedItem();
                runnerObservableList.remove(currentRunner);
                runnerTableView.refresh();
            }
        });
    }
    private void constructGrids()
    {
        AddRunnerPane.setAlignment(Pos.CENTER);
        AddRunnerPane.setHgap(10);
        AddRunnerPane.setVgap(10);
        AddRunnerPane.setPadding(new Insets(15, 15, 15, 15));

        AddRunnerPane.add(name_label, 0, 1);
        AddRunnerPane.add(name, 1, 1);

        AddRunnerPane.add(distance_label, 0, 2);
        AddRunnerPane.add(distance, 1, 2);

        AddRunnerPane.add(minutes_label, 0, 3);
        AddRunnerPane.add(minutes, 1, 3);

        AddRunnerPane.add(seconds_label, 0, 4);
        AddRunnerPane.add(seconds, 1, 4);

        AddRunnerPane.add(hbBtn, 0, 5, 2, 1);


        RunnerTablePane.setAlignment(Pos.CENTER);
        RunnerTablePane.setHgap(10);
        RunnerTablePane.setVgap(10);
        RunnerTablePane.add(runnerTableView, 0, 1, 3, 1);
        RunnerTablePane.add(deleteRowBtn, 2, 2);
        RunnerTablePane.add(loadBtn, 1, 2);
        RunnerTablePane.add(saveBtn, 0, 2);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
