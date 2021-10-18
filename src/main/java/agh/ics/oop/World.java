package agh.ics.oop;
import static java.lang.System.out;

enum Direction {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT,
    WRONG_INPUT
}

public class World {
    public static void main(String[] args) {
        out.println("start");

        Direction[] directions = StringToEnum(args);
        run(directions);

        out.println("stop");
    }

    private static void run(Direction[] directions){
        int dirCount = directions.length;
        for(int i = 0; i < dirCount; i++){
            switch(directions[i]){
                case FORWARD:
                    out.println("forward");
                    break;
                case BACKWARD:
                    out.println("backward");
                    break;
                case LEFT:
                    out.println("left");
                    break;
                case RIGHT:
                    out.println("right");
                    break;
                case WRONG_INPUT:
                    out.println("wrong input");
            }
        }
    }

    private static Direction[] StringToEnum(String[] stringDirections){
        int dirCount = stringDirections.length;
        Direction[] enumDirections = new Direction[dirCount];
        for(int i = 0; i < dirCount; i++){
            enumDirections[i] = switch(stringDirections[i]){
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "l" -> Direction.LEFT;
                case "r" -> Direction.RIGHT;
                default -> Direction.WRONG_INPUT;
            };
        }
        return enumDirections;
    }
}
