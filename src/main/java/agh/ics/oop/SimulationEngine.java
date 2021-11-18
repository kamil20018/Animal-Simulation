package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements IEngine{
    MoveDirection[] instructions;
    IWorldMap map;
    Vector2d[] initialPositions;
    private List<Animal> animals = new LinkedList<Animal>();
    private List<Vector2d> animalPositions = new LinkedList<>();
    public SimulationEngine (MoveDirection[] instructions, IWorldMap map, Vector2d[] initialPositions){
        this.instructions = instructions;
        this.map = map;
        this.initialPositions = initialPositions;
        for(Vector2d position : initialPositions){
            Animal animal = new Animal(map, position);
            boolean placed = map.place(animal);
            if(placed){
                animals.add(animal);
                animalPositions.add(position);
            }
        }
    }
    public void run(){
        int animalCount = animals.size();
        int instructionCount = instructions.length;
        for(int i = 0; i < instructionCount; i++){
            int index = i % animalCount;
            animals.get(index).move(instructions[i]);
            animalPositions.set(index, animals.get(index).getPosition());
        }

    }

    public List<Vector2d> getPositions(){
        return animalPositions;
    }
}
