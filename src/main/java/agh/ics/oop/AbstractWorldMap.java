package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap{
    protected List<Animal> animals = new LinkedList<Animal>();
    protected List<Grass> grasses = new LinkedList<>();


    public boolean place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(canMoveTo(animalPos)){
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

        for(Grass grass : grasses){
            if(grass.getPosition().equals(position)){
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

        for(Grass grass : grasses){
            if(grass.getPosition().equals(position)){
                return grass;
            }
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
}
