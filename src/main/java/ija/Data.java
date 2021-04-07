package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;

/**
 * Trieda ktora obsahuje vsetky informacie ohladom skladu, vozikov, ulickach
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Data {
    private String name;
    private List<Coordinate> coordinates;
    private  List<Vehicle> vehicles;
    private List<Street> streets;

    /**
     * Prazdny konstruktor potrebny pre deserializaciu suboru YML
     */
    private Data() {
    }

    /**
     * Funkcia nastavi nazov
     * @param name nastavi nazov
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Funkcia vrati nazov
     * @return vrati nazov
     */
    public String getName() {
        return name;
    }

    /**
     * Konstruktor inicializujuci zoznamy, ktore udrziavaju informacie
     * @param coordinates zoznam suradnic
     * @param vehicles zoznam vozidiel
     * @param streets zoznam ulic v sklade
     */
    public Data(List<Coordinate> coordinates, List<Vehicle> vehicles, List<Street> streets) {
        this.coordinates = coordinates;
        this.streets = streets;
        this.vehicles = vehicles;
    }

    /**
     * Funkcia vrati zoznam suradnic
     * @return List<Coordinate> zoznam suradnic
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * Funkcia nastavi informacii o suradniciach
     * @param coordinates nastavi zoznam informacii o suradniciach
     */
    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Funkcia nastavi zoznam informacii o ulickach v sklade
     * @param streets nastavi zoznam informacii o ulickach v sklade
     */
    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    /**
     * Funkcia vrati zoznam informacii o ulickach v sklade
     * @return List<Street> informacii o ulickach v sklade
     */
    public List<Street> getStreets() {
        return streets;
    }

    /**
     * Funkcia vrati zoznam informacii o vozidlach
     * @return List<Vehicles> zoznam informacii o vozidlach
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Funkcia nastavi zoznam informacii o vozidlach
     * @param vehicles nastavi zoznam informacii o vozidlach
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Funkcia prepise metodu toString
     * @return string repezentujuci tuto triedu
     */
    @Override
    public String toString() {
        return "Data{" +
                "coordinates=" + coordinates +
                ", vehicles=" + vehicles +
                ", streets=" + streets +
                '}';
    }
}
