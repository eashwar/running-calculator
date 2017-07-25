import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application{


    private AddRunnerPane runnerPane = new AddRunnerPane();


    public Main() {
    }


    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        primaryStage.setTitle("Pace Calculator");

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        Tab runnerTab = new Tab();
        runnerTab.setText("Add Runner to Table");

        runnerTab.setContent(runnerPane);
        runnerTab.setClosable(false);
        tabPane.getTabs().add(runnerTab);



        Tab tableTab = new Tab();


        Scene scene = new Scene(root, 700, 500);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);

        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
