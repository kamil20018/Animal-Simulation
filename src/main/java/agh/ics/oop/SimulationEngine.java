package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements IEngine, Runnable{

    IWorldMap map;
    Vector2d[] initialPositions;
    App app;
    int moveDelay;
    public boolean paused = false;
    private List<Animal> animals = new LinkedList<Animal>();
    private List<Vector2d> animalPositions = new LinkedList<>();
    public SimulationEngine (IWorldMap map, Vector2d[] initialPositions, App app, int moveDelay){
        this.map = map;
        this.app = app;
        this.initialPositions = initialPositions;
        this.moveDelay = moveDelay;
        for(Vector2d position : initialPositions){
            GeneGenerator generator = new GeneGenerator();

            Animal animal = new Animal(map, position, generator.generateRandomGenes(), Settings.getStartingEnergy());
            boolean placed = map.place(animal);
            if(placed){
                animals.add(animal);
                animalPositions.add(position);
            }
        }
    }

    @Override
    public void run(){
        while(true) {
            while(!map.isPaused()){
                List<Animal> toPlace = new LinkedList<>();
                map.removeDead();
                handleDead();
                moveAnimals();
                map.eat();
                toPlace = map.breed();
                for (Animal animal : toPlace) {
                    animals.add(animal);
                    animalPositions.add(animal.getPosition());
                }


                if(animals.size() == 5 && map.getMagicalEvoCount() < 3 && // magical evolution
                        ((map instanceof GrassField && Settings.getMagicalEvo()) ||
                        (map instanceof RectangularMap && Settings.getMagicalEvoBounded()))) {
                    List<Animal> clones = new LinkedList<>();
                    for(Animal animal: animals){
                        boolean goodPos = false;
                        Vector2d animalPos = null;
                        while (!goodPos) {
                            int x = ThreadLocalRandom.current().nextInt(0, Settings.getMapWidth());
                            int y = ThreadLocalRandom.current().nextInt(0, Settings.getMapHeight());
                            animalPos = new Vector2d(x, y);
                            if (!animalPositions.contains(animalPos)) {
                                animalPositions.add(animalPos);
                                goodPos = true;
                            }
                        }
                        Animal clone = new Animal(map, animalPos, animal.getGenes(), Settings.getStartingEnergy());
                        clones.add(clone);
                    }
                    for(Animal clone: clones){
                        animals.add(clone);
                        map.place(clone);
                        animalPositions.add(clone.getPosition());
                    }
                    map.magicalEvo();
                    System.out.println(map.getClass().getName());
                    System.out.println("magical evo happened");

                    if(map instanceof GrassField){
                        String text = "magical evolution happened in the unbounded map";
                        app.popup(text);
                    } else if(map instanceof RectangularMap){
                        String text = "magical evolution happened int the bounded map";
                        app.popup(text);
                    }
                }
                map.growGrass();
                if(map instanceof GrassField){
                    app.updateGrid();
                } else if(map instanceof RectangularMap){
                    app.updateBoundedGrid();
                }

                map.nextEpoch();
                try {
                    Thread.sleep(moveDelay);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public List<Vector2d> getPositions(){
        return animalPositions;
    }

    private void handleDead(){
        List<Integer> deadIndexes = new LinkedList<>();
        for(int i = animals.size() - 1; i >= 0; i--){
            if(animals.get(i).isDead()){
                deadIndexes.add(i);
            }
        }
        for(int deadIndex: deadIndexes){
            animalPositions.remove(deadIndex);
            animals.remove(deadIndex);
        }
    }

    private void moveAnimals(){
        int animalCount = animals.size();
        for(int animalIndex = 0; animalIndex < animalCount; animalIndex++){
            animals.get(animalIndex).move();
            animalPositions.set(animalIndex, animals.get(animalIndex).getPosition());
        }
    }
}
