package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class GrassField extends AbstractWorldMap{

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private MapBoundary boundary = new MapBoundary();
    public GrassField(int grassCount){
        boolean taken = false;
        boolean placed = false;
        int grassBound = (int)Math.round(Math.sqrt(grassCount * 10));
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
                    boundary.addObject(grass);
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
            animal.addObserver(boundary);
            boundary.addObject(animal);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition().toString() + " is unavailable for the animal");
    }
    public boolean canMoveTo(Vector2d position){
        if(animals.containsKey(position)){
            return false;
        }
        return true;
    }

    public Vector2d[] getBounds(){
        return boundary.getBounds();
        //updateBounds();
        //return new Vector2d[]{lowerLeft, upperRight};
    };

    private void updateBounds(){
        lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for(Animal animal: animals.values()){
            lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
            upperRight = upperRight.upperRight(animal.getPosition());
        }

        for(Grass grass: grasses.values()){
            lowerLeft = lowerLeft.lowerLeft(grass.getPosition());
            upperRight = upperRight.upperRight(grass.getPosition());
        }
    }
}
