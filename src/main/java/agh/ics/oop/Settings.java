package agh.ics.oop;

public class Settings {
    public final static int GENE_LENGTH = 32;

    private static int ENERGY_LOSS;
    private static int STARTING_ENERGY;
    private static int GRASS_ENERGY_GAIN;
    private static int MIN_BREED_ENERGY;
    private static int MAP_WIDTH;
    private static int MAP_HEIGHT;
    private static int INIT_ANIMAL_COUNT;
    private static float JUNGLE_RATIO;
    public static final int GRAPH_DATA_CAP = 20;
    public static final float GRASS_CAP = 0.90f; //if grass covers >90% of the map we need to change the way it's placed

    private static boolean ENERGY_LOSS_SET = false;
    private static boolean STARTING_ENERGY_SET = false;
    private static boolean GRASS_ENERGY_GAIN_SET = false;
    private static boolean MAP_WIDTH_SET = false;
    private static boolean MAP_HEIGHT_SET = false;
    private static boolean JUNGLE_RATIO_SET = false;
    private static boolean INIT_ANIMAL_COUNT_SET = false;

    public static void setEnergyLoss(int energyLoss){
        if(!ENERGY_LOSS_SET){
            ENERGY_LOSS = energyLoss;
        }
        ENERGY_LOSS_SET = true;
    }

    public static int getEnergyLoss(){
        return ENERGY_LOSS;
    }

    public static void setStartingEnergy(int startingEnergy){
        if(!STARTING_ENERGY_SET){
            STARTING_ENERGY = startingEnergy;
            MIN_BREED_ENERGY = startingEnergy / 2;
        }
        STARTING_ENERGY_SET = true;
    }

    public static int getStartingEnergy() {
        return STARTING_ENERGY;
    }

    public static void setGrassEnergyGain(int grassEnergy){
        if(!GRASS_ENERGY_GAIN_SET){
            GRASS_ENERGY_GAIN = grassEnergy;
        }
        GRASS_ENERGY_GAIN_SET = true;
    }

    public static int getGrassEnergyGain() {
        return GRASS_ENERGY_GAIN;
    }

    public static int getMinBreedEnergy(){
        return MIN_BREED_ENERGY;
    }

    public static void setMapWidth(int mapWidth){
        if(!MAP_WIDTH_SET){
            MAP_WIDTH = mapWidth;
        }
        MAP_WIDTH_SET = true;
    }

    public static int getMapWidth(){
        return MAP_WIDTH;
    }

    public static void setMapHeight(int mapHeight){
        if(!MAP_HEIGHT_SET){
            MAP_HEIGHT = mapHeight;
        }
        MAP_HEIGHT_SET = true;
    }

    public static int getMapHeight(){
        return MAP_HEIGHT;
    }

    public static void setJungleRatio(float jungleRatio){
        if(!JUNGLE_RATIO_SET){
            JUNGLE_RATIO = jungleRatio;
        }
        JUNGLE_RATIO_SET = true;
    }

    public static float getJungleRatio(){
        return JUNGLE_RATIO;
    }

    public static void setInitAnimalCount(int animalCount){
        if(!INIT_ANIMAL_COUNT_SET){
            INIT_ANIMAL_COUNT = animalCount;
        }
        INIT_ANIMAL_COUNT_SET = true;
    }

    public static int getInitAnimalCount(){
        return INIT_ANIMAL_COUNT;
    }

    public static Vector2d getBounds(){
        return new Vector2d(MAP_WIDTH - 1, MAP_HEIGHT - 1);
    }
}
