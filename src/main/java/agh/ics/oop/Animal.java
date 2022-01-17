package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements IMapElement {
    private List<IPositionChangeObserver> observers = new LinkedList<>();
    private Vector2d position;
    private IWorldMap map;
    private int rotation;   // enum byłby czytelniejszy
    private final String genes; // String?
    private int currentEnergy;
    private int age = 0;
    private int childCount = 0;
    private AnimalDirection dirManager = new AnimalDirection();

    public Animal(IWorldMap map, Vector2d initialPosition, String genes, int startingEnergy) {
        currentEnergy = startingEnergy;
        this.genes = genes;
        this.rotation = ThreadLocalRandom.current().nextInt(0, 7 + 1); //temporary cuz no gene
        this.map = map;
        this.position = initialPosition;
    }

    public String toString() {
        return String.valueOf(this.rotation);
    }

    public void move() {
        age++;
        Vector2d testSum = null;
        int dir = chooseDir();
        if (dir == 0) {
            testSum = this.position.add(dirManager.getMovementVector(this.rotation));
        } else if (dir == 4) {  // za miesiąc będzie Pan wiedział, czemu akurat 4?
            testSum = this.position.add(dirManager.getMovementVector(this.rotation).opposite());
        } else {
            this.rotation = dirManager.getNewRotation(this.rotation, dir);
        }

        if (testSum != null && map.canMoveTo(testSum)) {
            Vector2d oldPosition = this.position;
            Vector2d upperBound = Settings.getBounds();

            if (testSum.x > upperBound.x) {
                testSum = new Vector2d(testSum.x % upperBound.x, testSum.y);
            }
            if (testSum.y > upperBound.y) {
                testSum = new Vector2d(testSum.x, testSum.y % upperBound.y);
            }

            if (testSum.x < 0) {
                testSum = new Vector2d(upperBound.x, testSum.y);
            }
            if (testSum.y < 0) {
                testSum = new Vector2d(testSum.x, upperBound.y);
            }
            this.position = testSum;
            positionChanged(oldPosition, testSum);
        }
        handleEnergy();
    }

    private int chooseDir() {
        int genePart = ThreadLocalRandom.current().nextInt(0, Settings.GENE_LENGTH);
        return Character.getNumericValue(this.genes.charAt(genePart));
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    private void positionChanged(Vector2d oldPos, Vector2d newPos) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPos, newPos);
        }
    }

    public boolean isDead() {
        return (this.currentEnergy <= 0);
    }

    private void handleEnergy() {
        this.currentEnergy -= Settings.getEnergyLoss();
    }

    public void eat() {
        this.currentEnergy += Settings.getGrassEnergyGain();    // a co jak kilka zwierząt się musi podzielić trawą?
    }

    public int getEnergy() {
        return this.currentEnergy;
    }

    public String getGenes() {
        return genes;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public int getAge() {
        return this.age;
    }

    public int breed() {
        int transferredEnergy = this.currentEnergy / 4;
        this.currentEnergy -= transferredEnergy;
        this.childCount++;
        return transferredEnergy;
    }

    public String getColor() { //returns animal color in hex depending on the energy %. Lower energy -> brighter color  // idealnie by było przerzucić tę metodę do GUI
        if (currentEnergy >= Settings.getStartingEnergy()) {
            return "FFFFFF";
        }
        String output = "ff";
        float energyPart = (float) currentEnergy / Settings.getStartingEnergy();
        String hexCap = "FF";
        int cap = Integer.parseInt(hexCap, 16);
        int currPart = Math.round(cap * (1 - energyPart));
        String opacity = Integer.toHexString(currPart);
        int toFill = 2 - opacity.length();
        String filler = "";
        for (int x = 0; x < toFill; x++) {
            filler += "0";
        }
        String colVal = filler + Integer.toHexString(currPart);
        output = output + colVal + colVal;
        return output;
    }
}
