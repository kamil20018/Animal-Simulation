package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class GeneGenerator {
    public String generateRandomGenes(){ //7 losowań liczb od 1 do 31
        int[] divs = new Random().ints(1, Settings.GENE_LENGTH).distinct().limit(7).toArray();
        String genes = "";
        Arrays.sort(divs);
        int index = 0;
        for(int i = 0; i < Settings.GENE_LENGTH; i++){
            if(index < 7 && i == divs[index]){
                index++;
            }
            genes += index;
        }
        return genes;
    }

    public String generateChildGenes(Animal parent1, Animal parent2){ //geneRatio - % of gene1
        String genes = "";
        float geneRatio = (float)parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
        int strongerParent;
        if(geneRatio > 0.5f){
            strongerParent = 1;
        } else {
            strongerParent = 2;
        }
        Random rand = new Random();

        boolean right = rand.nextBoolean(); //0 - left, 1 - right
        int parent1Part = Math.round(geneRatio * Settings.GENE_LENGTH);
        int parent2Part = Settings.GENE_LENGTH - parent1Part;
        String leftPart;
        String rightPart;
        if(strongerParent == 1){
            if(right){
                rightPart = parent1.getGenes().substring(parent2Part);
                leftPart = parent2.getGenes().substring(0, parent2Part);
            } else {
                leftPart = parent1.getGenes().substring(0, parent1Part);
                rightPart = parent2.getGenes().substring(parent1Part);
            }
        } else {
            if(right){
                rightPart = parent2.getGenes().substring(parent1Part);
                leftPart = parent1.getGenes().substring(0, parent1Part);
            } else {
                leftPart = parent2.getGenes().substring(0, parent2Part);
                rightPart = parent1.getGenes().substring(parent2Part);
            }
        }
        return leftPart + rightPart;
    }
}
