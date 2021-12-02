package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver{
    private List<IMapElement> ObjectsX = new LinkedList<>();
    private List<IMapElement> ObjectsY = new LinkedList<>();

    public void addObject(IMapElement object){
        int index = 0;
        boolean done = false;
        for(int i = 0; i < ObjectsX.size(); i++){
            if(ObjectsX.get(i).getPosition().x > object.getPosition().x){
                done = true;
                break;
            } else if(ObjectsX.get(i).getPosition().x == object.getPosition().x){
                break;
            }
            if(i == ObjectsX.size()){
                done = true;
            }
            index++;
        }
        if(done){
            ObjectsX.add(index, object);
        } else {
            for(int i = index; i < ObjectsX.size(); i++){
                if(ObjectsX.get(i).getPosition().y > object.getPosition().y){
                    ObjectsX.add(i, object);
                    break;
                } else if(ObjectsX.get(i).getPosition().y == object.getPosition().y){
                    if(object instanceof Animal){
                        ObjectsX.add(i + 1, object);
                    } else {
                        ObjectsX.add(i, object);
                    }
                    break;
                }
                index++;
                if(index == ObjectsX.size()){
                    ObjectsX.add(index, object);
                }
            }
        }


        index = 0;
        done = false;
        for(int i = 0; i < ObjectsY.size(); i++){
            if(ObjectsY.get(i).getPosition().y > object.getPosition().y){
                done = true;
                break;
            } else if(ObjectsY.get(i).getPosition().y == object.getPosition().y){
                break;
            }
            if(i == ObjectsY.size()){
                done = true;
            }
            index++;
        }
        if(done){
            ObjectsY.add(index, object);
        } else {
            for(int i = index; i < ObjectsY.size(); i++){
                if(ObjectsY.get(i).getPosition().x > object.getPosition().x){
                    ObjectsY.add(i, object);
                    break;
                } else if(ObjectsY.get(i).getPosition().x == object.getPosition().x){
                    if(object instanceof Animal){
                        ObjectsY.add(i + 1, object);
                    } else {
                        ObjectsY.add(i, object);
                    }
                    break;
                }
                index++;
                if(index == ObjectsY.size()){
                    ObjectsY.add(index, object);
                }
            }
        }
    }

    public void reposAnimal(Vector2d position){
        int index = 0;
        for(int i = 0; i < ObjectsX.size(); i++){
            if(ObjectsX.get(i) instanceof Animal && ObjectsX.get(i).getPosition().equals(position)){
                index = i;
                break;
            }
        }
        Animal temp = (Animal) ObjectsX.get(index);
        //addObject(animal);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IMapElement obj: ObjectsX){
            if(obj instanceof Animal && obj.getPosition().equals(newPosition)){

            }
        }
    }
}
