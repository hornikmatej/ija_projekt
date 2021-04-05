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
    private  List<Vehicle> vehicles;
    private List<Street> streets;

    private Data() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Data(List<Coordinate> coordinates, List<Vehicle> vehicles, List<Street> streets) {
        this.coordinates = coordinates;
//        this.vehicle = vehicle;
        this.streets = streets;
        this.vehicles = vehicles;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }


    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }


    public List<Street> getStreets() {
        return streets;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "Data{" +
                "coordinates=" + coordinates +
                ", vehicles=" + vehicles +
                ", streets=" + streets +
                '}';
    }
}
