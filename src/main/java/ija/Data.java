package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Data {
    private String name;
    private List<Coordinate> coordinates;
    private Vehicle vehicle;
    private List<Street> streets;

    private Data() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Data(List<Coordinate> coordinates, Vehicle vehicle, List<Street> streets) {
        this.coordinates = coordinates;
        this.vehicle = vehicle;
        this.streets = streets;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Street> getStreets() {
        return streets;
    }


    @Override
    public String toString() {
        return "Data{" +
                "coordinates=" + coordinates +
                ", vehicle=" + vehicle +
                ", streets=" + streets +
                '}';
    }
}
