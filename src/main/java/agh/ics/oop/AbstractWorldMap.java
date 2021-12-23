package agh.ics.oop;

import javafx.util.Pair;

import java.util.*;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    Map<Vector2d, List<Animal>> animals = new HashMap<>();
    Map<Vector2d, Grass> grasses = new HashMap<>();

    public boolean place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(canMoveTo(animalPos)){
            animal.addObserver(this);
            if(animals.containsKey(animalPos)){
                int animalEnergy = animal.getEnergy();
                int index = 0;
                for(Animal placedAnimal: animals.get(animalPos)){ //animals should be sorted by their energy
                    if(animalEnergy < placedAnimal.getEnergy()){
                        break;
                    }
                    index++;
                }
                animals.get(animalPos).add(index, animal);
            } else {
                animals.put(animalPos, new LinkedList<Animal>(Arrays.asList(animal)));
            }

            return true;
        }
        throw new IllegalArgumentException(animalPos.toString() + " is unavailable for the animal");
    }

    public boolean isOccupied(Vector2d position){
        if(animals.containsKey(position)){
            return true;
        }

        if(grasses.containsKey(position)){
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if(animals.containsKey(position)){
            return animals.get(position);
        }

        if(grasses.containsKey(position)){
            return grasses.get(position);
        }
        return null;
    }



    public String toString(){
        IWorldMap map = this;
        MapVisualizer visualizer = new MapVisualizer(map);
        Vector2d[] bounds = getBounds();
        return visualizer.draw(bounds[0], bounds[1]);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        List<Animal> animalStack = animals.get(oldPosition);
        int movedAnimalIndex = 10;
        Animal movedAnimal = new Animal(this, new Vector2d(100, 100));
        for (int i = 0; i < animalStack.size(); i++){
            if (animalStack.get(i).getPosition().equals(newPosition)){
                movedAnimalIndex = i;
                movedAnimal = animalStack.get(i);
                break;
            }
        }
        animals.get(oldPosition).remove(movedAnimalIndex);
        if(animals.get(oldPosition).size() == 0){
            animals.remove(oldPosition);
        }
        if(animals.containsKey(newPosition)){
            int animalEnergy = movedAnimal.getEnergy();
            int index = 0;
            for(Animal placedAnimal: animals.get(newPosition)){ //animals should be sorted by their energy
                if(animalEnergy < placedAnimal.getEnergy()){
                    break;
                }
                index++;
            }
            animals.get(newPosition).add(index, movedAnimal);
        } else {
            animals.put(newPosition, new LinkedList<Animal>(Arrays.asList(movedAnimal)));
        }

    }

    public List<Pair<Vector2d, IMapElement>> getDrawables(){
        List<Pair<Vector2d, IMapElement>> drawables = new LinkedList<>();
        for(Vector2d key: grasses.keySet()) {
            drawables.add(new Pair<>(key, grasses.get(key)));
        }
        for(Vector2d key: animals.keySet()){
            if(animals.get(key).size() > 0){
                drawables.add(new Pair<>(key, animals.get(key).get(0)));
            }

        }
        return drawables;
    }

    public void removeDead(){
        for(Vector2d key: animals.keySet()){
            List<Animal> animalStack = animals.get(key);
            List<Integer> deadIndexes = new LinkedList<>();
            for(int i = animalStack.size() - 1; i >= 0; i--){
                if(animalStack.get(i).isDead()){
                    deadIndexes.add(i);
                }
            }
            for(int index: deadIndexes){
                animals.get(key).remove(index);
            }
        }
        List<Vector2d> emptyFields = new LinkedList<>();
        for(Vector2d key: animals.keySet()) {
            if(animals.get(key).size() == 0){
                emptyFields.add(key);
            }
        }
        for(Vector2d key: emptyFields){
            animals.remove(key);
        }
    }

    public void eat(){
        List<Vector2d> eatenGrasses = new LinkedList<>();
        for(Vector2d key: grasses.keySet()){
            if(animals.containsKey(key)){
                eatenGrasses.add(key);
                List<Animal> animalsSet = animals.get(key);
                if(animalsSet.size() > 1){
                    for(int i = animalsSet.size() - 1; i >= 0; i--){

                    }
                } else {
                    animals.get(key).get(0).eat();
                }

            }
        }
        for(Vector2d eatenGrass: eatenGrasses){
            grasses.remove(eatenGrass);
        }
    }
}
