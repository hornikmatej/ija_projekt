package ija;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Drawable, TimeUpdate {

    private Coordinate pos;
    private double speed = 1;
    private double distance = 0;
    private Path path;
    private List<Shape> gui;

    public Vehicle(Coordinate pos, double speed, Path path) {
        this.pos = pos;
        this.speed = speed;
        this.path = path;
        gui = new ArrayList<>();
        gui.add(new Circle(pos.getX(), pos.getY(), 8 , Color.BLUE));
    }

    private void moveGui(Coordinate coordinate){
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - pos.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - pos.getY() + shape.getTranslateY());
        }
    }

    @Override
    public List<Shape> getGui() {
        return gui;
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        if (distance > path.getPathSize()){

        }
        Coordinate coordinate = path.getCoordinateByDistance(distance);
        moveGui(coordinate);
        pos = coordinate;
    }
}
