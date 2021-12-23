package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;



class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    public RectangularMap(int width, int height){
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
    }

    public boolean canMoveTo(Vector2d position){
        if(!position.precedes(upperRight) || !position.follows(lowerLeft)){
            return false;
        }
        return true;
    }

    public Vector2d[] getBounds(){
        return new Vector2d[]{lowerLeft, upperRight};
    };

}