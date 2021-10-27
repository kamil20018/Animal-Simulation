package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public String toString(){
        return "Position: " + position.toString() + ", Direction: " + direction.toString();
    }

    private boolean isAt(Vector2d position){
        return this.position == position;
    }

    public void move(MoveDirection direction){
        Vector2d testSum = null;

        switch (direction){
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> testSum = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> testSum = this.position.add(this.direction.toUnitVector().opposite());
        }

        if(testSum != null && testSum.x >= 0 && testSum.x <= 4 && testSum.y >= 0 && testSum.y <= 4){
            this.position = testSum;
        }

    }
}
