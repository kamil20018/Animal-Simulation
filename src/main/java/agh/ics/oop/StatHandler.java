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
    private float averageLifespan = 0;
    private List<Epoch> epochesData = new LinkedList<>();

    private int animalCountSum = 0;
    private int plantCountSum = 0;
    private float averageEnergySum = 0;
    private float childCountSum = 0;
    private Float averageLifespanSum = 0f;
    private int lastEpoch = 0;
    public void addEpoch(int epochNumber, int animalCount, int plantCount, Float averageEnergy, Float childCount, List<Integer> deadAnimalsAges){
        for(Integer age: deadAnimalsAges){
            deadAnimalsCount++;
            deadAnimalsLifespan += age;
        }
        Float averageLifespan = (float) deadAnimalsLifespan / deadAnimalsCount;
        if(averageLifespan.isNaN()){
            averageLifespan = 0f;
        }
        this.averageLifespan = averageLifespan;
        if(averageEnergy.isNaN()){
            averageEnergy = 0f;
        }
        if(childCount.isNaN()){
            childCount = 0f;
        }
        Epoch epoch = new Epoch(epochNumber, animalCount, plantCount, averageEnergy, averageLifespan, childCount);
        epochesData.add(epoch);

        animalCountSum += animalCount;
        plantCountSum += plantCount;
        averageEnergySum += averageEnergy;
        childCountSum += childCount;
        if(!averageLifespan.isNaN()){
            averageLifespanSum += averageLifespan;
        }

        lastEpoch = epochNumber;
    }

    public void createDataFile() throws FileNotFoundException {
        File csv = new File("epoches-" + this.mapType +".csv");
        PrintWriter contents = new PrintWriter(csv);
        contents.println("epoch, animal count, plant count, avg energy, lifespan, child count");
        for(Epoch epoch: epochesData){
            contents.println(epoch.toString());
        }
        float animalCountAvg = animalCountSum / lastEpoch;
        float plantCountAvg = plantCountSum / lastEpoch;
        float averageEnergyAvg = averageEnergySum / lastEpoch;
        float childCountAvg = childCountSum / lastEpoch;
        float averageLifespanAvg = averageLifespanSum / lastEpoch;
        contents.println("average: animal count, plant count, average energy, average lifespan, child count");
        contents.printf("%f, %f, %f, %f, %f\n", animalCountAvg, plantCountAvg, averageEnergyAvg, averageLifespanAvg, childCountAvg);
        contents.close();
    }

    public Float getAverageLifespan(){
        return averageLifespan;
    }
}
