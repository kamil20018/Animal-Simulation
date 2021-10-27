package agh.ics.oop;

import java.util.*;

public class OptionsParser {
    public MoveDirection[] parse(String instructions){

        String[] instructionsArray = instructions.split(" ");
        List<MoveDirection> convertedInstructions = new LinkedList<MoveDirection>();
        for (String s: instructionsArray) {
            switch(s){
                case "f":
                case "forward":
                    convertedInstructions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                case "backward":
                    convertedInstructions.add(MoveDirection.BACKWARD);
                    break;
                case "l":
                case "left":
                    convertedInstructions.add(MoveDirection.LEFT);
                    break;
                case "r":
                case "right":
                    convertedInstructions.add(MoveDirection.RIGHT);
                    break;
            }
        }

        MoveDirection[] array = convertedInstructions.toArray(MoveDirection[]::new);
        return array;

    }
}
