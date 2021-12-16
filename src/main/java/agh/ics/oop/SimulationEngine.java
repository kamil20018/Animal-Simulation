package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements IEngine, Runnable{
    MoveDirection[] instructions;
    IWorldMap map;
    Vector2d[] initialPositions;
    private List<Animal> animals = new LinkedList<Animal>();
    private List<Vector2d> animalPositions = new LinkedList<>();
    public SimulationEngine (MoveDirection[] instructions, IWorldMap map, Vector2d[] initialPositions, App app){
        this.instructions = instructions;
        this.map = map;
        this.initialPositions = initialPositions;
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
        Platform.runLater(()->{
            System.out.println("Thread started.");
            int animalCount = animals.size();
            int instructionCount = instructions.length;
            for(int i = 0; i < instructionCount; i++){
                int index = i % animalCount;
                animals.get(index).move(instructions[i]);
                animalPositions.set(index, animals.get(index).getPosition());
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e){
                    System.out.println(e);
                }
            }
            System.out.println(map.toString());
        });
    }

    public List<Vector2d> getPositions(){
        return animalPositions;
    }
}
