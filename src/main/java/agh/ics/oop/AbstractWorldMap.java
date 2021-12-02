package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    Map<Vector2d, Animal> animals = new HashMap<>();
    Map<Vector2d, Grass> grasses = new HashMap<>();


    public boolean place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(canMoveTo(animalPos)){
            animal.addObserver(this);
            animals.put(animalPos, animal);
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

    abstract Vector2d[] getBounds();

    public String toString(){
        IWorldMap map = this;
        MapVisualizer visualizer = new MapVisualizer(map);
        Vector2d[] bounds = getBounds();
        return visualizer.draw(bounds[0], bounds[1]);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal movedAnimal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, movedAnimal);
    }
}
