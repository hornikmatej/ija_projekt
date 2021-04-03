package ija;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.*;

public class Street implements  Drawable{

    private Coordinate start;
    private Coordinate end;
    private String name;

    public Street(String name, Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.name = name;
    }

    @Override
    public List<Shape> getGui() {
        return Arrays.asList(
                new Line(start.getX(), start.getY(), end.getX(), end.getY())
        );
    }
}
