package agh.ics.oop;
import agh.ics.oop.gui.App;
import javafx.application.Application;

import static java.lang.System.out;



public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f");
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(2,23) };
            SimulationEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            out.println(map.toString());
            Application.launch(App.class, args);
        } catch (IllegalArgumentException e) {
            out.println(e);
        }

    }
}

