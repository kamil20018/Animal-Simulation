package agh.ics.oop;

public class AnimalDirection {  // ta klasa nie reprezentuje kierunku
    public Vector2d getMovementVector(int rotation){
        switch (rotation){
            case 0:
                return new Vector2d(0, 1);  // nowy wektor co wywołanie
            case 1:
                return new Vector2d(1, 1);
            case 2:
                return new Vector2d(1, 0);
            case 3:
                return new Vector2d(1, -1);
            case 4:
                return new Vector2d(0, -1);
            case 5:
                return new Vector2d(-1, -1);
            case 6:
                return new Vector2d(-1, 0);
            default:
                return new Vector2d(-1, 1); // jak rotation będzie 20 to też?
        }
    }

    public int getNewRotation(int old, int rotationBy){
        return (old + rotationBy) % 8;
    }
}
