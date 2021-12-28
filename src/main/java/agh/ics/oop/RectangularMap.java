package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;



public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(int grassCount, int width, int height){
        super(grassCount, width, height);
    }

    public boolean canMoveTo(Vector2d position){
        if(!position.precedes(upperRight) || !position.follows(lowerLeft)){
            return false;
        }
        return true;
    }

}