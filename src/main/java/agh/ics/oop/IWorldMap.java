package agh.ics.oop;

import javafx.scene.chart.LineChart;
import javafx.util.Pair;

import java.util.List;
public interface IWorldMap {

    boolean canMoveTo(Vector2d position);

    boolean place(Animal animal);

    List<Pair<Vector2d, IMapElement>> getDrawables();

    void removeDead();

    void eat();

    List<Animal> breed();

    void growGrass();

    void nextEpoch();

    void file();

    boolean isPaused();

    void changePaused();

    int getMagicalEvoCount();

    void magicalEvo();

    List<LineChart<Number, Number>> getGraphs();
}