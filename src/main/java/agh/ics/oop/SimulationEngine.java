package agh.ics.oop;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements IEngine{
    MoveDirection[] instructions;
    RectangularMap map;
    Vector2d[] initialPositions;
    public SimulationEngine (MoveDirection[] instructions, IWorldMap map, Vector2d[] initialPositions){
        this.instructions = instructions;
        this.map = ( RectangularMap ) map;
        this.initialPositions = initialPositions;
        for(Vector2d position : initialPositions){
            map.place(new Animal(map, position));
        }
    }
    public void run(){
        int animalCount = map.animals.size();
        int instructionCount = instructions.length;
        for(int i = 0; i < instructionCount; i++){
            map.animals.get(i % animalCount).move(instructions[i]);
        }
    }
}
