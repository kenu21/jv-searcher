package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Label response;
    private Button start;
    private Button stop;
    private Button pause;
    private TextField url;
    private TextField text;
    private TextField threads;
    private Label labelAmountThread;
    private ListView<Integer> amountThread;
    private Label labelUrlDeep;
    private ListView<Integer> urlDeep;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Search text");
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 600, 400);
        stage.setScene(scene);
        response = new Label();
        start = new Button("Start");
        stop = new Button("Stop");
        pause = new Button("Pause");
        url = new TextField("URL");
        text = new TextField("Text");
        threads = new TextField("Thread");
        ObservableList<Integer> amount =
                FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        amountThread = new ListView<>(amount);
        amountThread.setPrefSize(50, 50);
        labelAmountThread = new Label("AmountThread:");
        urlDeep = new ListView<>(amount);
        urlDeep.setPrefSize(50, 50);
        labelUrlDeep = new Label("UrlDeep:");
        rootNode.getChildren().addAll(url, text, labelAmountThread, amountThread,
                labelUrlDeep, urlDeep, start, stop, pause, response);
        stage.show();
    }
}
