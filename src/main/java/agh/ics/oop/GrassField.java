package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class GrassField extends AbstractWorldMap{

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    public GrassField(int grassCount){
        boolean taken = false;
        boolean placed = false;
        int grassBound = (int)Math.round(Math.sqrt(grassCount * 10));
        Vector2d position;
        for(int i = 0; i < grassCount; i++){
            while(!placed){
                position = new Vector2d(current().nextInt(0, grassBound + 1), current().nextInt(0, grassBound + 1));
                for(Grass grass:grasses){
                    if(grass.getPosition().equals(position)){
                        taken = true;
                        break;
                    }
                    taken = false;
                }
                if(!taken){
                    grasses.add(new Grass(position));
                    taken = false;
                    placed = true;
                }
            }
            placed = false;
        }
    }

    public boolean canMoveTo(Vector2d position){
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    public Vector2d[] getBounds(){
        setBounds();
        return new Vector2d[]{lowerLeft, upperRight};
    };

    private void setBounds(){
        lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for(Animal animal: animals){
            lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
            upperRight = upperRight.upperRight(animal.getPosition());
        }

        for(Grass grass: grasses){
            lowerLeft = lowerLeft.lowerLeft(grass.getPosition());
            upperRight = upperRight.upperRight(grass.getPosition());
        }
    }
}
