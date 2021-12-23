package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class GrassField extends AbstractWorldMap{

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    public GrassField(int grassCount, int width, int height){
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);
        boolean taken = false;
        boolean placed = false;
        int grassBound = upperRight.x;
        Vector2d position;
        for(int i = 0; i < grassCount; i++){
            while(!placed){
                position = new Vector2d(current().nextInt(0, grassBound + 1), current().nextInt(0, grassBound + 1));

                if(grasses.containsKey(position)){
                    taken = true;
                } else {
                    taken = false;
                }

                if(!taken){
                    Grass grass = new Grass(position);
                    grasses.put(position, grass);
                    placed = true;
                }
            }
            placed = false;
        }
    }

    public boolean place(Animal animal){
        boolean placed = super.place(animal);
        if (placed){
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition().toString() + " is unavailable for the animal");
    }
    public boolean canMoveTo(Vector2d position){
        return true;
    }

    public Vector2d[] getBounds(){
        return new Vector2d[] {lowerLeft, upperRight};
    };

}
