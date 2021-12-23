package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements IEngine, Runnable{

    IWorldMap map;
    Vector2d[] initialPositions;
    App app;
    int moveDelay;
    private List<Animal> animals = new LinkedList<Animal>();
    private List<Vector2d> animalPositions = new LinkedList<>();
    public SimulationEngine (IWorldMap map, Vector2d[] initialPositions, App app, int moveDelay){
        this.map = map;
        this.app = app;
        this.initialPositions = initialPositions;
        this.moveDelay = moveDelay;
        for(Vector2d position : initialPositions){
            Animal animal = new Animal(map, position);
            animal.addObserver(app);
            boolean placed = map.place(animal);
            if(placed){
                animals.add(animal);
                animalPositions.add(position);
            }
        }
    }

    @Override
    public void run(){
        System.out.println("Thread started.");

        app.updateGrid();
        for(int i = 0; i < 1000; i++){
            map.removeDead();
            handleDead();
            moveAnimals();
            map.eat();
            try{
                Thread.sleep(moveDelay);
            } catch (InterruptedException e){
                System.out.println(e);
            }
            app.updateGrid();
        }

        //System.out.println(map.toString());

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
