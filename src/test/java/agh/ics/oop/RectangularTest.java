package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularTest {

    @Test
    public void placeTest(){
        MoveDirection[] directions = new OptionsParser().parse("f b");
        IWorldMap map = new RectangularMap(5, 5);
        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(4, 4), new Vector2d(0, 0) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        Assertions.assertEquals(engine.getPositions().size(), 3);
    }

    @Test
    public void movementTest(){
        MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f");
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Assertions.assertEquals(engine.getPositions().get(0), new Vector2d(2, 0));
        Assertions.assertEquals(engine.getPositions().get(1), new Vector2d(3, 4));
    }
}
