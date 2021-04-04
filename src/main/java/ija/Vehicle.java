package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(converter = Vehicle.VehicleConstructorCall.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "name")
public class Vehicle implements Drawable, TimeUpdate {

    private Coordinate position;
    private double speed = 1;
    private double distance = 0;
    private Path path;
    @JsonIgnore
    private List<Shape> gui;

    private  Vehicle(){}

    public Vehicle(Coordinate position, double speed, Path path) {
        this.position = position;
        this.speed = speed;
        this.path = path;
        setGui();
    }

    private void moveGui(Coordinate coordinate){
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    private void setGui(){
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 8 , Color.BLUE));
    }

    public Path getPath() {
        return path;
    }

    @JsonIgnore
    @Override
    public List<Shape> getGui() {
        return gui;
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        if (distance > path.getPathSize()){
            return;
        }
        Coordinate coords = path.getCoordinateByDistance(distance);
        moveGui(coords);
        position = coords;
    }

    public Coordinate getPosition() {
        return position;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    static class VehicleConstructorCall extends StdConverter<Vehicle ,Vehicle>{
        @Override
        public Vehicle convert(Vehicle value){
            value.setGui();
            return value;
        }
    }
}
