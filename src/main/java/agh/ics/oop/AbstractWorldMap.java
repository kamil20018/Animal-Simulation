package agh.ics.oop;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    Map<Vector2d, List<Animal>> animals = new HashMap<>();
    Map<Vector2d, Grass> grasses = new HashMap<>();
    int epoch = 0;
    int grassCount = 0;
    int jungleGrassCount = 0;
    int animalCount = 0;
    private StatHandler statHandler = new StatHandler(this.getClass().getName());
    private List<Integer> deadAnimalAges = new LinkedList<>();
    public final Vector2d lowerLeft = new Vector2d(0, 0);
    public final Vector2d upperRight;
    public final Vector2d jungleLowerLeft;
    public final Vector2d jungleUpperRight;
    public final int area;
    public final int jungleArea;
    private boolean paused = false;

    LineChart<Number, Number> animalChildCountGraph;
    public XYChart.Series animalChildCountSeries = new XYChart.Series();

    LineChart<Number, Number> animalCountGraph;
    public XYChart.Series animalCountSeries = new XYChart.Series();

    LineChart<Number, Number> grassCountGraph;
    public XYChart.Series grassCountSeries = new XYChart.Series();

    LineChart<Number, Number> averageEnergyGraph;
    public XYChart.Series averageEnergySeries = new XYChart.Series();

    public AbstractWorldMap(int grassCount, int width, int height){
        initGraphs();
        area = width * height;
        //initial jungle handling
        double proportion = Math.sqrt(Settings.getJungleRatio());
        int jungleWidth = (int) Math.round(width * proportion);
        int jungleHeight = (int) Math.round(height * proportion);
        jungleArea = jungleHeight * jungleWidth;
        int jungleLeft = (width - jungleWidth) / 2;
        int jungleRight = jungleLeft + jungleWidth - 1;
        int jungleDown = (height - jungleHeight) / 2;
        int jungleUp = jungleDown + jungleHeight - 1;

        jungleLowerLeft = new Vector2d(jungleLeft, jungleDown);
        jungleUpperRight = new Vector2d(jungleRight, jungleUp);
        //initial grass placement
        upperRight = new Vector2d(width - 1, height - 1);
        for(int i = 0; i < grassCount; i++){
            placeGrass(this.lowerLeft, this.upperRight);
        }
    }

    private void initGraphs(){
        animalChildCountGraph = initGraph("Child count", animalChildCountGraph, animalChildCountSeries);
        animalCountGraph = initGraph("Animal count", animalCountGraph, animalCountSeries);
        grassCountGraph = initGraph("Grass count", grassCountGraph, grassCountSeries);
        averageEnergyGraph = initGraph("Average energy", averageEnergyGraph, averageEnergySeries);
    }

    private LineChart<Number, Number> initGraph(String label, LineChart<Number, Number> chart, XYChart.Series series){
        System.out.println("a;lkjfefjwa;lkfekaj;lkjf");
        NumberAxis xAxis= new NumberAxis();//AnimalCount
        xAxis.setLabel("time");
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(false);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        yAxis.setLabel(label);
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.getData().add(series);
        return chart;
    }

    public boolean place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(canMoveTo(animalPos)){
            animal.addObserver(this);
            if(animals.containsKey(animalPos)){
                int animalEnergy = animal.getEnergy();
                int index = 0;
                for(Animal placedAnimal: animals.get(animalPos)){ //animals should be sorted by their energy
                    if(animalEnergy < placedAnimal.getEnergy()){
                        break;
                    }
                    index++;
                }
                this.animalCount++;
                animals.get(animalPos).add(index, animal);
            } else {
                this.animalCount++;
                animals.put(animalPos, new LinkedList<Animal>(Arrays.asList(animal)));
            }

            return true;
        }
        throw new IllegalArgumentException(animalPos.toString() + " is unavailable for the animal");
    }

    public boolean isOccupied(Vector2d position){
        if(animals.containsKey(position)){
            return true;
        }

        if(grasses.containsKey(position)){
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if(animals.containsKey(position)){
            return animals.get(position);
        }

        if(grasses.containsKey(position)){
            return grasses.get(position);
        }
        return null;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        List<Animal> animalStack = animals.get(oldPosition);
        int movedAnimalIndex = 10;
        GeneGenerator generator = new GeneGenerator();
        Animal movedAnimal = null;
        for (int i = 0; i < animalStack.size(); i++){
            if (animalStack.get(i).getPosition().equals(newPosition)){
                movedAnimalIndex = i;
                movedAnimal = animalStack.get(i);
                break;
            }
        }
        animals.get(oldPosition).remove(movedAnimalIndex);
        if(animals.get(oldPosition).size() == 0){
            animals.remove(oldPosition);
        }
        if(animals.containsKey(newPosition) && movedAnimal != null){
            int animalEnergy = movedAnimal.getEnergy();
            int index = 0;
            for(Animal placedAnimal: animals.get(newPosition)){ //animals should be sorted by their energy
                if(animalEnergy < placedAnimal.getEnergy()){
                    break;
                }
                index++;
            }
            animals.get(newPosition).add(index, movedAnimal);
        } else {
            animals.put(newPosition, new LinkedList<Animal>(Arrays.asList(movedAnimal)));
        }

    }

    public List<Pair<Vector2d, IMapElement>> getDrawables(){
        List<Pair<Vector2d, IMapElement>> drawables = new LinkedList<>();

        for(Vector2d key: animals.keySet()){
            if(animals.get(key).size() > 0){
                drawables.add(new Pair<>(key, animals.get(key).get(0)));
            }
        }
        for(Vector2d key: grasses.keySet()) {
            if(!drawables.contains(key)){
                drawables.add(new Pair<>(key, grasses.get(key)));
            }

        }
        return drawables;
    }

    public void removeDead(){
        List<Integer> ages = new LinkedList<>();
        for(Vector2d key: animals.keySet()){
            List<Animal> animalStack = animals.get(key);
            List<Integer> deadIndexes = new LinkedList<>();
            for(int i = animalStack.size() - 1; i >= 0; i--){
                if(animalStack.get(i).isDead()){
                    deadIndexes.add(i);
                }
            }
            for(int index: deadIndexes){
                ages.add(animals.get(key).get(index).getAge());
                this.animalCount--;
                animals.get(key).remove(index);
            }
        }
        List<Vector2d> emptyFields = new LinkedList<>();
        for(Vector2d key: animals.keySet()) {
            if(animals.get(key).size() == 0){
                emptyFields.add(key);
            }
        }
        for(Vector2d key: emptyFields){
            animals.remove(key);
        }
        this.deadAnimalAges = ages;
    }

    public void eat(){
        List<Vector2d> eatenGrasses = new LinkedList<>();
        for(Vector2d key: grasses.keySet()){
            if(animals.containsKey(key)){
                eatenGrasses.add(key);
                List<Animal> animalsSet = animals.get(key);
                if(animalsSet.size() > 1){
                    for(int i = animalsSet.size() - 1; i >= 0; i--){

                    }
                } else {
                    animals.get(key).get(0).eat();
                }

            }
        }
        for(Vector2d eatenGrass: eatenGrasses){
            if(inJungle(eatenGrass)){
                jungleGrassCount--;
            }
            grassCount--;
            grasses.remove(eatenGrass);
        }
    }

    public List<Animal> breed(AnimalTracker tracker){
        List<Animal> toPlace = new LinkedList<>();
        GeneGenerator generator = new GeneGenerator();
        for(Vector2d key: animals.keySet()){
            List<Animal> animalStack = animals.get(key);
            int animalCount = animalStack.size();
            if(animalCount >= 2){
                Animal parent1 = animalStack.get(animalCount - 1);
                Animal parent2 = animalStack.get(animalCount - 2);
                int minEnergy = Settings.getMinBreedEnergy();
                if(parent1.getEnergy() >= minEnergy && parent2.getEnergy() >= minEnergy){
                    String genes = generator.generateChildGenes(parent1, parent2);
                    int energy = parent1.breed() + parent2.breed();
                    Animal newBorn = new Animal(this, key, genes, energy, tracker);
                    toPlace.add(newBorn);
                }
            }
        }
        for(Animal newBorn: toPlace){
            place(newBorn);
        }
        return toPlace;
    }

    public void growGrass(){

        float jungleFill = (float) jungleGrassCount / jungleArea;
        if(jungleGrassCount < jungleArea){
            if(jungleFill < Settings.GRASS_CAP){
                placeGrass(jungleLowerLeft, jungleUpperRight);
            } else {
                List<Vector2d> emptyPositions = emptyPositions(jungleLowerLeft, jungleUpperRight);
                int index = ThreadLocalRandom.current().nextInt(0, emptyPositions.size());
                Vector2d position = emptyPositions.get(index);
                Grass grass = new Grass(position);
                grasses.put(position, grass);
                jungleGrassCount++;
                grassCount++;
            }
        }
        float mapFill = (float) grassCount / area;
        if(grassCount < area){
            if(mapFill < Settings.GRASS_CAP){
                placeGrass(lowerLeft, upperRight);
            } else {
                List<Vector2d> emptyPositions = emptyPositions(lowerLeft, upperRight);
                int index = ThreadLocalRandom.current().nextInt(0, emptyPositions.size());
                Vector2d position = emptyPositions.get(index);
                Grass grass = new Grass(position);
                grasses.put(position, grass);
                if(inJungle(position)){
                    jungleGrassCount++;
                }
                grassCount++;
            }
        }
    }

    private boolean inJungle(Vector2d pos){
        return pos.precedes(jungleUpperRight) && pos.follows(jungleLowerLeft);
    }

    private void placeGrass(Vector2d lowerLeft, Vector2d upperRight){
        boolean taken = false;
        boolean placed = false;
        int minX = lowerLeft.x;
        int minY = lowerLeft.y;
        int maxX = upperRight.x;
        int maxY = upperRight.y;
        Vector2d position;
        while(!placed){
            position = new Vector2d(current().nextInt(minX, maxX + 1), current().nextInt(minY, maxY + 1));
            if(grasses.containsKey(position)){
                taken = true;
            } else {
                taken = false;
            }
            if(!taken){
                Grass grass = new Grass(position);
                grasses.put(position, grass);
                placed = true;
                if(inJungle(position)){
                    jungleGrassCount++;
                }
                grassCount++;
            }
        }
    }

    private List<Vector2d> emptyPositions(Vector2d lowerLeft, Vector2d upperRight){
        List<Vector2d> emptyPositions = new LinkedList<>();
        for(int x = lowerLeft.x; x <= upperRight.x; x++){
            for(int y = lowerLeft.y; y <= upperRight.y; y++){
                Vector2d testPos = new Vector2d(x, y);
                if(!grasses.containsKey(testPos)){
                    emptyPositions.add(testPos);
                }
            }
        }
        return emptyPositions;
    }

    public void nextEpoch(){
        this.epoch++;

        int energySum = 0;
        int childSum = 0;
        for(Vector2d key: animals.keySet()){
            for(Animal animal: animals.get(key)){
                energySum += animal.getEnergy();
                childSum += animal.getChildCount();
            }
        }
        float averageEnergy = (float) energySum / this.animalCount;
        float averageChildren = (float) childSum / this.animalCount;
        Platform.runLater(() -> {
            while(animalChildCountSeries.getData().size() > Settings.GRAPH_DATA_CAP){
                animalChildCountSeries.getData().remove(0);
            }
            animalChildCountSeries.getData().add(new XYChart.Data(epoch, averageChildren));

            while(animalCountSeries.getData().size() > Settings.GRAPH_DATA_CAP){
                animalCountSeries.getData().remove(0);
            }
            animalCountSeries.getData().add(new XYChart.Data(epoch, animalCount));

            while(grassCountSeries.getData().size() > Settings.GRAPH_DATA_CAP){
                grassCountSeries.getData().remove(0);
            }
            grassCountSeries.getData().add(new XYChart.Data(epoch, grassCount));

            while(averageEnergySeries.getData().size() > Settings.GRAPH_DATA_CAP){
                averageEnergySeries.getData().remove(0);
            }
            averageEnergySeries.getData().add(new XYChart.Data(epoch, averageEnergy));
        });
        this.statHandler.addEpoch(this.epoch, this.animalCount, this.grassCount, averageEnergy, averageChildren, this.deadAnimalAges);
    }

    public void file(){
        try {
            this.statHandler.createDataFile();
        } catch (FileNotFoundException e){

        }
    }

    public boolean isPaused(){
        return paused;
    }

    public void changePaused(){
        this.paused = !this.paused;
    }

    public int getEpoch(){
        return this.epoch;
    }

    public LineChart<Number, Number> getAnimalChildCountGraph(){
        return animalChildCountGraph;
    }

    public LineChart<Number, Number> getAnimalCountGraph(){
        return animalCountGraph;
    }

    public LineChart<Number, Number> getGrassCountGraph(){
        return grassCountGraph;
    }

    public LineChart<Number, Number> getAverageEnergyGraph(){
        return averageEnergyGraph;
    }
}
