package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements IMapElement{
    private List<IPositionChangeObserver> observers= new LinkedList<>();
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);
    private IWorldMap map;
    private boolean mapWrap;
    private int rotation;
    private final int maxEnergy;
    private final int energyLoss;
    private final int grassEnergyGain;
    private int currentEnergy = 100;
    private AnimalDirection dirManager = new AnimalDirection();
    public Animal (IWorldMap map, Vector2d initialPosition){
        maxEnergy = Settings.getMaxEnergy();
        energyLoss = Settings.getEnergyLoss();
        grassEnergyGain = Settings.getGrassEnergyGain();
        this.rotation = ThreadLocalRandom.current().nextInt(0, 7 + 1); //temporary cuz no gene
        this.map = map;
        if(map instanceof GrassField) {
            mapWrap = true;
        } else {
            mapWrap = false;
        }
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
    }

    public String toString(){
        return String.valueOf(this.rotation);
    }

    private boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(){
        Vector2d testSum = null;
        int dir = ThreadLocalRandom.current().nextInt(0, 7 + 1); //temporary cuz no gene
        if (dir == 0){
            testSum = this.position.add(dirManager.getMovementVector(this.rotation));
        } else if (dir == 4){
            testSum = this.position.add(dirManager.getMovementVector(this.rotation).opposite());
        } else {
            this.rotation = dirManager.getNewRotation(this.rotation, dir);
        }

        if(testSum != null && map.canMoveTo(testSum)){
            Vector2d oldPosition = this.position;
            Vector2d upperBound = map.getBounds()[1];

            if(testSum.x > upperBound.x){
                testSum = new Vector2d(testSum.x % upperBound.x, testSum.y);
            }
            if(testSum.y > upperBound.y){
                testSum = new Vector2d(testSum.x, testSum.y % upperBound.y);
            }

            if(testSum.x < 0){
                testSum = new Vector2d(upperBound.x, testSum.y);
            }
            if(testSum.y < 0){
                testSum = new Vector2d(testSum.x, upperBound.y);
            }
            this.position = testSum;
            positionChanged(oldPosition, testSum);
        }
        handleEnergy();
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

    public String getImagePath(){
        switch (direction){
            case NORTH:
                return "src/main/resources/up.png";
            case SOUTH:
                return "src/main/resources/down.png";
            case WEST:
                return "src/main/resources/left.png";
            case EAST:
                return "src/main/resources/right.png";
        }
        return "";
    }

    public boolean isDead(){
        if (this.currentEnergy <= 0){
            System.out.println("died");
            return true;
        }
        return false;
    }

    private void handleEnergy(){
        this.currentEnergy -= this.energyLoss;
    }

    public void eat(){
        this.currentEnergy += grassEnergyGain;
        System.out.println("just ate: " + String.valueOf(currentEnergy));
    }

    public int getEnergy(){
        return this.currentEnergy;
    }
}
