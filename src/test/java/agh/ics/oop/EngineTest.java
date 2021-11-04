package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EngineTest {


    @Test
    public void placeTest(){
        MoveDirection[] directions = new OptionsParser().parse("f b");
        IWorldMap map = new RectangularMap(5, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(2,2),
                new Vector2d(2, 2), new Vector2d(10, 10), new Vector2d(-1, -1), new Vector2d(5, 5), new Vector2d(0, 0) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        Assertions.assertEquals(((RectangularMap) map).animals.size(), 3);

    }

    @Test
    public void engineTest(){
        MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f");
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Assertions.assertEquals(((RectangularMap) map).animals.get(0).getPosition(), new Vector2d(2, 0));
        Assertions.assertEquals(((RectangularMap) map).animals.get(1).getPosition(), new Vector2d(3, 5));
    }
}
