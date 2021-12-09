package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement{
    private List<IPositionChangeObserver> observers= new LinkedList<>();
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);
    private IWorldMap map;
    public Animal (IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
    }

    public String toString(){
        return direction.toString();
    }

    private boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction){
        Vector2d testSum = null;

        switch (direction){
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> testSum = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> testSum = this.position.add(this.direction.toUnitVector().opposite());
        }
        if(testSum != null && map.canMoveTo(testSum)){
            Vector2d oldPosition = this.position;
            this.position = testSum;
            System.out.println("animal moved");
            System.out.println(oldPosition);
            System.out.println(this.position);
            positionChanged(oldPosition, testSum);
        }

    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    private void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPos, Vector2d newPos){
        for(IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPos, newPos);
        }
    }
}
