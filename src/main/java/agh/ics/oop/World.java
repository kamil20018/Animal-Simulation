package agh.ics.oop;
import static java.lang.System.out;



public class World {
    public static void main(String[] args) {

        MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f");
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

    }
}

