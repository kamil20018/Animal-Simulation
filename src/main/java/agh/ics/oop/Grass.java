package agh.ics.oop;

public class Grass {
    private Vector2d grassPos;
    public Grass(Vector2d position){
        grassPos = position;
    }

    public Vector2d getPosition(){
        return grassPos;
    }

    public String toString(){
        return "*";
    }
}
