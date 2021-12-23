package agh.ics.oop;

public class Settings {
    private static int ENERGY_LOSS;
    private static int MAX_ENERGY;
    private static int GRASS_ENERGY_GAIN;
    //initialize, tylko raz wywoływalna
    public Settings(int energyLoss, int maxEnergy, int grassEnergyGain){
        ENERGY_LOSS = energyLoss;
        MAX_ENERGY = maxEnergy;
        GRASS_ENERGY_GAIN = grassEnergyGain;
    }

    public static int getEnergyLoss(){
        return ENERGY_LOSS;
    }

    public static int getMaxEnergy() {
        return MAX_ENERGY;
    }

    public static int getGrassEnergyGain() {
        return GRASS_ENERGY_GAIN;
    }
}
