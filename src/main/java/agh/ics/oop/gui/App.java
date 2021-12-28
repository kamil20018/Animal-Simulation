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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.valueOf;
import static java.lang.System.out;

public class App extends Application {
    private GridPane grid, boundedGrid;
    private IWorldMap map, boundedMap;
    private Thread engineThread, engineBoundedThread;

    public App() {
        grid = new GridPane();
        grid.setStyle("-fx-grid-lines-visible: true");
        grid.setStyle("-fx-background-color: #6E260E;");
        boundedGrid = new GridPane();
        boundedGrid.setStyle("-fx-grid-lines-visible: true");
        boundedGrid.setStyle("-fx-background-color: #6E260E;");
    }

    public void start(Stage primaryStage) {
        TextField mapWidthInput = new TextField("12");
        TextField mapHeightInput = new TextField("12");
        TextField startEnergyInput = new TextField("200");
        TextField moveEnergyInput = new TextField("5");
        TextField plantEnergyInput = new TextField("25");
        TextField jungleRatioInput = new TextField("0.5");
        TextField initialAnimalsInput = new TextField("10");

        Label mapWidthInfo = new Label(" map width: ");
        Label mapHeightInfo = new Label(" map height: ");
        Label startEnergyInfo = new Label(" start energy: ");
        Label moveEnergyInfo = new Label(" move energy: ");
        Label plantEnergyInfo = new Label(" plant energy: ");
        Label jungleRatioInfo = new Label(" jungle ratio: ");
        Label initialAnimalsInfo = new Label(" initial animal count: ");

        HBox mapWidthBox = new HBox(mapWidthInfo, mapWidthInput);
        HBox mapHeightBox = new HBox(mapHeightInfo, mapHeightInput);
        HBox startEnergyBox = new HBox(startEnergyInfo, startEnergyInput);
        HBox moveEnergyBox = new HBox(moveEnergyInfo, moveEnergyInput);
        HBox plantEnergyBox = new HBox(plantEnergyInfo, plantEnergyInput);
        HBox jungleRatioBox = new HBox(jungleRatioInfo, jungleRatioInput);
        HBox initialAnimalsBox = new HBox(initialAnimalsInfo, initialAnimalsInput);

        Label evoTypeInfo = new Label("Choose evolution type for unbounded and bounded maps respectively:");
        ChoiceBox<String> evoType = new ChoiceBox<>();
        evoType.getItems().add("normal");
        evoType.getItems().add("magical");
        evoType.setValue("normal");

        ChoiceBox<String> evoTypeBounded = new ChoiceBox<>();
        evoTypeBounded.getItems().add("normal");
        evoTypeBounded.getItems().add("magical");
        evoTypeBounded.setValue("normal");

        Button start = new Button("start");

        VBox settings = new VBox(
                mapWidthBox, mapHeightBox, startEnergyBox, moveEnergyBox,
                plantEnergyBox, jungleRatioBox, initialAnimalsBox, evoTypeInfo,
                evoType, evoTypeBounded,
                start
        );
        VBox mapContainer = new VBox(new Label("unbounded map"), grid);
        VBox boundedMapContainer = new VBox(new Label("bounded map"),boundedGrid);
        HBox grids = new HBox(new ScrollPane(mapContainer), new ScrollPane(boundedMapContainer));
        grids.setSpacing(10);
        VBox field = new VBox(grids, settings);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Settings.setMapWidth(Integer.parseInt(mapWidthInput.getText()));
                Settings.setMapHeight(Integer.parseInt(mapHeightInput.getText()));
                Settings.setGrassEnergyGain(Integer.parseInt(plantEnergyInput.getText()));
                Settings.setJungleRatio(Float.parseFloat(jungleRatioInput.getText()));
                Settings.setEnergyLoss(Integer.parseInt(moveEnergyInput.getText()));
                Settings.setStartingEnergy(Integer.parseInt(startEnergyInput.getText()));
                Settings.setInitAnimalCount(Integer.parseInt(initialAnimalsInput.getText()));
                Settings.setMagicalEvo(evoType.getValue());
                Settings.setMagicalEvoBounded(evoTypeBounded.getValue());

                map = new GrassField(35, Settings.getMapWidth(), Settings.getMapHeight());
                boundedMap = new RectangularMap(35, Settings.getMapWidth(), Settings.getMapHeight());


                List<Vector2d> animalPositions = new LinkedList<>();
                for (int i = 0; i < Settings.getInitAnimalCount(); i++) {
                    boolean placed = false;
                    while (!placed) {
                        int x = ThreadLocalRandom.current().nextInt(0, Settings.getMapWidth());
                        int y = ThreadLocalRandom.current().nextInt(0, Settings.getMapHeight());
                        Vector2d testPos = new Vector2d(x, y);
                        if (!animalPositions.contains(testPos)) {
                            animalPositions.add(testPos);
                            placed = true;
                        }
                    }
                }

                grid.setStyle("-fx-grid-lines-visible: true");
                grid.setStyle("-fx-background-color: #6E260E;");
                boundedGrid.setStyle("-fx-grid-lines-visible: true");
                boundedGrid.setStyle("-fx-background-color: #6E260E;");
                for (int i = 0; i < Settings.getMapHeight(); i++) {
                    RowConstraints row = new RowConstraints(30);
                    grid.getRowConstraints().add(row);
                    boundedGrid.getRowConstraints().add(row);
                }
                for (int i = 0; i < Settings.getMapHeight(); i++) {
                    ColumnConstraints col = new ColumnConstraints(20);
                    grid.getColumnConstraints().add(col);
                    boundedGrid.getColumnConstraints().add(col);
                }

                Vector2d[] positions = animalPositions.toArray(new Vector2d[0]);
                SimulationEngine engine1 = new SimulationEngine(boundedMap, positions, App.this, 250);
                SimulationEngine engine2 = new SimulationEngine(map, positions, App.this, 250);

                EventHandler<ActionEvent> pause1 = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        boundedMap.changePaused();
                    }
                };

                EventHandler<ActionEvent> pause2 = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        map.changePaused();
                    }
                };


                field.getChildren().remove(settings);//gridPane.prefWidthProperty().bind(root.widthProperty());
                Button boundedPause = new Button("pause");
                Button pause = new Button("pause");
                Button getData = new Button("generate csv");
                getData.setOnAction(getDataEvent);
                Button getBoundedData = new Button("generate csv");
                getBoundedData.setOnAction(getBoundedDataEvent);
                pause.setOnAction(pause2);
                boundedPause.setOnAction(pause1);
                HBox buttons = new HBox(pause,getData);
                HBox boundedButtons = new HBox(boundedPause, getBoundedData);

                mapContainer.getChildren().add(buttons);
                boundedMapContainer.getChildren().add(boundedButtons);

                for(LineChart<Number, Number> chart: map.getGraphs()){
                    mapContainer.getChildren().add(chart);
                }

                for(LineChart<Number, Number> chart: boundedMap.getGraphs()){
                    boundedMapContainer.getChildren().add(chart);
                }

                engineBoundedThread = new Thread(engine1);
                engineBoundedThread.start();
                engineThread = new Thread(engine2);
                engineThread.start();

            }
        };
        start.setOnAction(event);


        Scene scene = new Scene(field);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    EventHandler<ActionEvent> getDataEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            if(map.isPaused()){
                map.file();
            }
        }
    };

    EventHandler<ActionEvent> getBoundedDataEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            if(map.isPaused()){
                boundedMap.file();
            }
        }
    };


    public void updateGrid(){
        Platform.runLater(() -> {
            grid.getChildren().clear();
            List<Pair<Vector2d, IMapElement>> drawables = map.getDrawables();
            for(Pair<Vector2d, IMapElement> drawable: drawables) {
                Button button = new Button();
                button.setMaxSize(10, 10);
                if(drawable.getValue() instanceof Grass){
                    button.setStyle("-fx-background-color: #00ff00;");
                } else {
                    Animal currAnimal = (Animal)drawable.getValue();
                    button.setStyle("-fx-background-color: " + currAnimal.getColor() + ";");
                }
                GridPane.setConstraints(button, drawable.getKey().x, drawable.getKey().y);
                grid.getChildren().add(button);

            }
        });
    }

    public void updateBoundedGrid(){
        Platform.runLater(() -> {
            boundedGrid.getChildren().clear();
            List<Pair<Vector2d, IMapElement>> drawables = boundedMap.getDrawables();
            for(Pair<Vector2d, IMapElement> drawable: drawables) {
                Button button = new Button();
                button.setMaxSize(10, 10);
                if(drawable.getValue() instanceof Grass){
                    button.setStyle("-fx-background-color: #00ff00;");
                } else {
                    Animal currAnimal = (Animal)drawable.getValue();
                    button.setStyle("-fx-background-color: " + currAnimal.getColor() + ";");
                }
                GridPane.setConstraints(button, drawable.getKey().x, drawable.getKey().y);
                boundedGrid.getChildren().add(button);
            }
        });
    }

    public void popup(String text){
        Platform.runLater(() -> {
            Stage window = new Stage();
            window.setTitle("Magical evolution happened");
            Label label = new Label(text);
            Scene scene = new Scene(label);
            window.setMinWidth(100);
            window.setMinHeight(50);
            window.setScene(scene);
            window.show();
        });
    }

}
