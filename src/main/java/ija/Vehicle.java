package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import ija.store.Goods;
import ija.store.GoodsItem;
import ija.store.Shelf;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @JsonIgnore
    private boolean working = false;
    @JsonIgnore
    private Request poziadavka = null;
    @JsonIgnore
    private MainController controller = null;

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

    public boolean isWorking() {
        return working;
    }



    public void setPath(Path path) {
        this.path = path;
        this.working = true;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }


    public void setPoziadavka(Request poziadavka) {
        this.poziadavka = poziadavka;
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
        if (!working)
            return;
        distance += speed;

        if (distance > path.getPathSize()){
            this.working = false;
            distance = 0;
            return;
        }
        Coordinate coords = path.getCoordinateByDistance(distance);
        for (int i = 0; i < poziadavka.getShelves().size(); i++){
            Shelf polica = poziadavka.getShelves().get(i);
            //blizko police ?
            double vzd = Math.sqrt(Math.pow(coords.getX() - polica.getPos().getX(), 2) + Math.pow(coords.getY() - polica.getPos().getY(), 2));
            if (vzd <= 35.9){
                //vymazem prvok s police
                polica.removePickUp(poziadavka.getTovar().get(i));
            }
        }
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
     * Funckia ktora zobrazi informacie o vozidle, ktore aktualne vykonava poziadavku
     */
    public void clickedOnVehicle() {
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY) && working) {
                    List<Shape> items_gui = new ArrayList<>();
                    int y_ax = 60;
                    Text shelf_name = new Text(50, 25, name + "\nBude vyzdvihnute: ");
                    items_gui.add(shelf_name);
                    shelf_name.setStroke(Color.GREY);
                    for (int i = 0; i < poziadavka.getShelves().size(); i++) {
                        Text item_name = new Text(50, y_ax, poziadavka.getShelves().get(i).getName() + ": " + poziadavka.getTovar().get(i) + ", " + poziadavka.getPocet().get(i));
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }
                    //zvyraznenie cesty
                    List<Shape> cesta = new ArrayList<>();
                    List<Coordinate> zoznam_coor = path.getPath();
                    for (int i = 0; i < zoznam_coor.size() - 1; i++){
                        Line line = new Line();
                        line.setStartX(zoznam_coor.get(i).getX());
                        line.setStartY(zoznam_coor.get(i).getY());
                        line.setEndX(zoznam_coor.get(i + 1).getX());
                        line.setEndY(zoznam_coor.get(i + 1).getY());
                        line.setStroke(Color.BLUE);
                        line.setStrokeWidth(3);
                        line.setFill(Color.BLUE);
                        cesta.add(line);
                    }
                    controller.showPath(cesta);

                    controller.printShelf(items_gui);

                }
            }
        });
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
                ", name=" + name +
                ", working=" + working +
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
