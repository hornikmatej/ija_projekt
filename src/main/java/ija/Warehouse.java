package ija;

import ija.store.Shelf;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private List<Shelf> shelves;
    private List<Street> streets;
    private List<Vehicle> vehicles;

    public Warehouse() {
        this.shelves = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void activate_shelves(){
        for (Shelf shelf : shelves){
            shelf.clickedOnShelf();
        }
    }


}
