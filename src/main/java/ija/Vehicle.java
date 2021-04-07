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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")

/**
 * Trieda spracovavajuca vsetky funkcie spojene s vozikom
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
public class Vehicle implements Drawable, TimeUpdate {

    private Coordinate position;
    private double speed = 1;
    private double distance = 0;
    private Path path;
    private String name;
    @JsonIgnore
    private List<Shape> gui;

    /**
     * Prazdny konstruktor potrebny pre deserializaciu suboru YML
     */
    private  Vehicle(){}


    /**
     * Konstruktor triedy Vehicle (vozik)
     * @param position startovacia poloha vozika
     * @param speed rychlost vozika
     * @param path cesta, ktorou pojde vozidlo
     * @param name nazov vozika
     */
    public Vehicle(Coordinate position, double speed, Path path, String name) {
        this.position = position;
        this.speed = speed;
        this.path = path;
        this.name = name;
        setGui();
    }

    /**
     * Funkcia pohybuje vozikom po mape
     * @param coordinate nove suradnice pre vozidlo
     */
    private void moveGui(Coordinate coordinate){
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    /**
     * Funkcia nastavi meno
     * @param name nastavi meno
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Funkcia nastavi GUI a vozik
     */
    private void setGui(){
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 8 , Color.BLUE));
    }

    /**
     * Funkcia vrati trasu
     * @return vrati trasu
     */
    //@JsonIgnore
    public Path getPath() {
        return path;
    }


    /**
     * Funkcia vrati GUI
     * @return List<Shape> GUI
     */
    @JsonIgnore
    @Override
    public List<Shape> getGui() {
        return gui;
    }


    /**
     * Funkcia aktualizuje poziciu vozidla na Mape
     * @param time aktualizuje poziciu vozidla na Mape v case
     */
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

    /**
     * Funkcia vrati koordinacie pozicie
     * @return Coordinate - vrati koordinacie pozicie
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Funkcia vrati rychlost voziku
     * @return double - vrati rychlost voziku
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Funkcia prepise metodu toString pre citanie z .YML suboru
     * @return vracia string zlozeny z rychlosti a pozicie
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    /**
     * Funkcia
     * @return VehicleConstructorCall
     */
    static class VehicleConstructorCall extends StdConverter<Vehicle ,Vehicle>{
        @Override
        public Vehicle convert(Vehicle value){
            value.setGui();
            return value;
        }
    }
}
