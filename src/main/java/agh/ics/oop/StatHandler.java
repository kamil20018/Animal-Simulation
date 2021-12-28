package agh.ics.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class StatHandler {
    private String mapType;
    public StatHandler(String mapType){
        this.mapType = mapType;
    }
    private class Epoch {
        private int epochNumber;
        private int animalCount;
        private int plantCount;
        private float averageEnergy;
        private float averageLifespan;
        private float childCount;

        Epoch(int epochNumber, int animalCount, int plantCount, float averageEnergy, float averageLifespan, float childCount){
            this.epochNumber = epochNumber;
            this.animalCount = animalCount;
            this.plantCount = plantCount;
            this.averageEnergy = averageEnergy;
            this.averageLifespan = averageLifespan;
            this.childCount = childCount;
        }

        public String toString(){
            return String.format("%d, %d, %d, %f, %f, %f", epochNumber, animalCount, plantCount, averageEnergy, averageLifespan, childCount);
        }

    }
    private int deadAnimalsCount = 0;
    private int deadAnimalsLifespan = 0;
    private List<Epoch> epochesData = new LinkedList<>();

    public void addEpoch(int epochNumber, int animalCount, int plantCount, float averageEnergy, float childCount, List<Integer> deadAnimalsAges){
        for(Integer age: deadAnimalsAges){
            deadAnimalsCount++;
            deadAnimalsLifespan += age;
        }
        float averageLifespan = (float) deadAnimalsLifespan / deadAnimalsCount;
        Epoch epoch = new Epoch(epochNumber, animalCount, plantCount, averageEnergy, averageLifespan, childCount);
        epochesData.add(epoch);
    }

    public void createDataFile() throws FileNotFoundException {
        File csv = new File("epoches-" + this.mapType +".csv");
        PrintWriter contents = new PrintWriter(csv);
        contents.println("epoch, animal count, plant count, avg energy, child count, lifespan");
        for(Epoch epoch: epochesData){
            contents.println(epoch.toString());
        }
        contents.close();
    }

    //animal count
    //plant count
    //average energy
    //average lifespan for dead ones
    //average kid count for alive ones
}
