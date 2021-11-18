package agh.ics.oop;

public class Animal {
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
            this.position = testSum;
        }

    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}
