package agh.ics.oop;

public class GrassField extends AbstractWorldMap{

    public GrassField(int grassCount, int width, int height){
        super(grassCount, width, height);
    }

    public boolean place(Animal animal){
        boolean placed = super.place(animal);
        if (placed){
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition().toString() + " is unavailable for the animal");
    }
    public boolean canMoveTo(Vector2d position){
        return true;
    }
}
