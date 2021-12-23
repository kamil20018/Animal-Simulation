package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;

import static java.lang.String.valueOf;
import static java.lang.System.out;

public class App extends Application implements IPositionChangeObserver{
    private GridPane grid;
    private IWorldMap map;
    private Thread engineThread;
    public App app = this;
    public Settings settings;
    public App(){
        settings = new Settings(4, 100, 25);
        grid = new GridPane();
        grid.setStyle("-fx-grid-lines-visible: true");
        grid.setPadding(new Insets(20, 20, 20, 20));
    }


    public void init(){


    }

    public void start(Stage primaryStage){
        Button start = new Button("start");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                map = new GrassField(15, 12, 12);
                Vector2d[] positions = { new Vector2d(4,2), new Vector2d(2,2), new Vector2d(3,2)};
                SimulationEngine engine = new SimulationEngine(map, positions, app, 300);
                engineThread = new Thread(engine);
                engineThread.start();
            }
        };
        start.setOnAction(event);


        VBox container = new VBox(grid, start);
        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
        positionChanged(new Vector2d(1,1), new Vector2d(1, 1));

    }

    public void updateGrid(){
        Platform.runLater(() -> {
            grid.getChildren().clear();
            grid.setStyle("-fx-grid-lines-visible: true");
            //out.println(map.toString());
            Vector2d[] bounds = map.getBounds();
            int smallX = bounds[0].x;
            int smallY = bounds[0].y;
            int bigX = bounds[1].x;
            int bigY = bounds[1].y;
            int boardWidth = bigX - smallX + 1;
            int boardHeight = bigY - smallY + 1;
            List<Pair<Vector2d, IMapElement>> drawables = map.getDrawables();
            for(Pair<Vector2d, IMapElement> drawable: drawables){
                GuiElementBox elementBox = null;
                try {
                    elementBox = new GuiElementBox(drawable.getValue());
                } catch (FileNotFoundException e) {
                    out.println(e);
                }
                VBox box = elementBox.getBox();
                GridPane.setConstraints(box, drawable.getKey().x - smallX + 1,boardHeight - (drawable.getKey().y - smallY));
                grid.getChildren().add(box);
            }
            for(int i = 0; i < boardWidth; i++){
                Label l1 = new Label(String.valueOf(i + smallX));
                GridPane.setConstraints(l1, i + 1, 0);
                grid.getChildren().add(l1);
            }
            for(int i = 0; i < boardHeight; i++){
                Label l1 = new Label(String.valueOf(bigY - i));
                GridPane.setConstraints(l1, 0, i + 1);
                grid.getChildren().add(l1);
            }

            Label l1 = new Label("y/x");
            GridPane.setConstraints(l1, 0, 0);
            grid.getChildren().add(l1);
        });
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        //updateGrid();
    }


}
