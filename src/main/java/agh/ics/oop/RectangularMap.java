package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

class RectangularMap implements IWorldMap {
    private final int width;
    private final int height;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    public List<Animal> animals = new LinkedList<Animal>();
    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);
    }

    public boolean canMoveTo(Vector2d position){
        if(!position.precedes(upperRight) || !position.follows(lowerLeft)){
            return false;
        }
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return false;
            }
        }

        return true;
    }

    public boolean place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(!isOccupied(animalPos) && animalPos.precedes(upperRight) && animalPos.follows(lowerLeft)){
            animals.add(animal);
            return true;
        }
        return false;
    }

    public boolean isOccupied(Vector2d position){
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return animal;
            }
        }
        return null;
    }

    public String toString(){
        IWorldMap map = this;
        MapVisualizer visualizer = new MapVisualizer(map);
        return visualizer.draw(lowerLeft, upperRight);
    }
}