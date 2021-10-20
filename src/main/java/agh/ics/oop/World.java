package agh.ics.oop;
import static java.lang.System.out;



public class World {
    public static void main(String[] args) {
        out.println("start");

        Direction[] directions = stringToEnum(args);
        run(directions);
        out.println("stop");

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
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
            }
        }
    }

    private static Direction[] stringToEnum(String[] stringDirections){
        int dirCount = stringDirections.length;
        Direction[] enumDirections = new Direction[dirCount];
        for(int i = 0; i < dirCount; i++){
            enumDirections[i] = switch(stringDirections[i]){
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "l" -> Direction.LEFT;
                case "r" -> Direction.RIGHT;
                default -> null;
            };
        }
        return enumDirections;
    }
}
