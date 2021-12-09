package agh.ics.oop;

import com.sun.source.tree.Tree;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class MapBoundary implements IPositionChangeObserver{
    public TreeMap<Vector2d, Animal> animalsX = new TreeMap<>(new XSort());
    public TreeMap<Vector2d, Animal> animalsY = new TreeMap<>(new YSort());
    public TreeMap<Vector2d, Grass> grassesX = new TreeMap<>(new XSort());
    public TreeMap<Vector2d, Grass> grassesY = new TreeMap<>(new YSort());

    public void addObject(IMapElement object){
        if (object instanceof Animal){
            Animal animal = (Animal) object;
            animalsX.put(animal.getPosition(), animal);
            animalsY.put(animal.getPosition(), animal);
        } else if (object instanceof Grass){
            Grass grass = (Grass) object;
            grassesX.put(grass.getPosition(), grass);
            grassesY.put(grass.getPosition(), grass);
        }
    }
    public void removeObject(IMapElement object){
        if (object instanceof Animal){
            Animal animal = (Animal) object;
            animalsX.remove(animal.getPosition());
            animalsY.remove(animal.getPosition());
        } else if (object instanceof Grass){
            Grass grass = (Grass) object;
            grassesX.remove(grass.getPosition());
            grassesY.remove(grass.getPosition());
        }
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal movedAnimal = animalsX.get(oldPosition);

        animalsX.remove(oldPosition);
        animalsY.remove(oldPosition);
        animalsX.put(newPosition, movedAnimal);
        animalsY.put(newPosition, movedAnimal);
    }

    public Vector2d[] getBounds(){
        System.out.println(animalsY.get(animalsY.firstKey()).getPosition());
        int smallestX = Math.min(animalsX.get(animalsX.firstKey()).getPosition().x, grassesX.get(grassesX.firstKey()).getPosition().x);
        int smallestY = Math.min(animalsY.get(animalsY.firstKey()).getPosition().y, grassesY.get(grassesY.firstKey()).getPosition().y);

        int biggestX = Math.max(animalsX.get(animalsX.lastKey()).getPosition().x, grassesX.get(grassesX.lastKey()).getPosition().x);
        int biggestY = Math.max(animalsY.get(animalsY.lastKey()).getPosition().y, grassesY.get(grassesY.lastKey()).getPosition().y);
        return new Vector2d[]{new Vector2d(smallestX, smallestY), new Vector2d(biggestX, biggestY)};
    }
}

class XSort implements Comparator<Vector2d> {
    public int compare(Vector2d pos1, Vector2d pos2)
    {
        if (pos1.x > pos2.x){
            return 1;
        } else if (pos1.x < pos2.x){
            return -1;
        }
        if (pos1.y > pos2.y){
            return 1;
        } else if (pos1.y < pos2.y){
            return -1;
        }
        return 0;
    }
}

class YSort implements Comparator<Vector2d> {
    public int compare(Vector2d pos1, Vector2d pos2)
    {
        if (pos1.y > pos2.y){
            return 1;
        } else if (pos1.y < pos2.y){
            return -1;
        }
        if (pos1.x > pos2.x){
            return 1;
        } else if (pos1.x < pos2.x){
            return -1;
        }
        return 0;
    }
}