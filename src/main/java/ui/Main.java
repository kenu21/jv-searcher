package ui;

import domain.Searching;

import java.util.concurrent.ForkJoinPool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private TextField url;
    private TextField text;
    private Label labelAmountThread;
    private ListView<Integer> amountThread;
    private Label labelUrlDeep;
    private ListView<Integer> urlDeep;
    private volatile ForkJoinPool forkJoinPool;

    public static void main(String[] args) {
        launch(args);
    }

    public Label getResponse() {
        return response;
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
        url = new TextField("URL");
        text = new TextField("Text");
        ObservableList<Integer> amount =
                FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        amountThread = new ListView<>(amount);
        amountThread.getSelectionModel().select(0);
        amountThread.setPrefSize(50, 50);
        labelAmountThread = new Label("AmountThread:");
        urlDeep = new ListView<>(amount);
        urlDeep.getSelectionModel().select(0);
        urlDeep.setPrefSize(50, 50);
        labelUrlDeep = new Label("UrlDeep:");
        rootNode.getChildren().addAll(url, text, labelAmountThread, amountThread,
                labelUrlDeep, urlDeep, start, stop, response);

        start.setOnAction(new StartEventHandler());

        stop.setOnAction((ae) -> {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        });
        stage.show();
    }

    private class StartEventHandler implements EventHandler<ActionEvent>, Runnable {

        @Override
        public void handle(ActionEvent actionEvent) {
            Platform.runLater(this);
        }

        @Override
        public void run() {
            response.setText("");
            forkJoinPool = new ForkJoinPool(amountThread.getSelectionModel().getSelectedItem());
            Searching searching = new Searching(
                    url.getText(), text.getText(), urlDeep.getSelectionModel().getSelectedItem());
            String invoke = forkJoinPool.invoke(searching);
            response.setText(invoke);
        }
    }
}
