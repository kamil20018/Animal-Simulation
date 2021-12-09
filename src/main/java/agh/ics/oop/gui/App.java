package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import static java.lang.System.out;

public class App extends Application {
    public void start(Stage primaryStage){

        String instructions = getParameters().getRaw().get(0);

        try {
            MoveDirection[] directions = new OptionsParser().parse(instructions);
            GrassField map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(4,2), new Vector2d(4,3) };
            SimulationEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            Vector2d[] bounds = map.getBounds();
            GridPane grid = new GridPane();
            int smallX = bounds[0].x;
            int smallY = bounds[0].y;
            int bigX = bounds[1].x;
            int bigY = bounds[1].y;
            grid.getColumnConstraints().add(new ColumnConstraints(bigX - smallX + 2));
            grid.getRowConstraints().add(new RowConstraints(bigY - smallY + 2));
            out.println(map.toString());
            String[] test = map.toString().split("\\s+");
            for(String s: test){
                out.println(s);
            }

        } catch (IllegalArgumentException e) {
            out.println(e);
        }

        GridPane grid = new GridPane();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                l1 = new Label("a");
                GridPane.setConstraints(l1, i, j);
                grid.getChildren().addAll(l1);
            }
        }
        Scene scene = new Scene(grid);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
