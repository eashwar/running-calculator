import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application{

    private static Main app;

    private TabPane tabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();


    private Tab runnerTab = new Tab();
    private Tab tableTab = new Tab();

    private TableView<Runner> runnerTableView = new TableView<Runner>();
    public List<Runner> unobservablerunners = new ArrayList<Runner>();
    ObservableList<Runner> runners = FXCollections.observableArrayList(unobservablerunners);


    public Main() {
    }

    static Main getInstance()
    {
        if (app == null)
        {
            app = new Main();
        }
        return app;
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        primaryStage.setTitle("Pace Calculator");


        runnerTab.setText("Add Runners");
        runnerTab.setContent(AddRunnerPane.getInstance());
        runnerTab.setClosable(false);
        tabPane.getTabs().add(runnerTab);


        tableTab.setText("Runner Table");

        runnerTableView.setItems(runners);
        TableColumn<Runner,String> nameCol = new TableColumn<Runner,String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        runnerTableView.getColumns().setAll(nameCol);
        tableTab.setContent(runnerTableView);
        tableTab.setClosable(false);
        tabPane.getTabs().add(tableTab);

        Scene scene = new Scene(root, 700, 500);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    void addRunner(Runner runner)
    {
        runners.add(runner);
        runnerTableView.refresh();
    }


    public static void main(String[] args) {
        app = new Main();
        app.launch(args);
    }
}
