package ija;

import ija.store.Shelf;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private List<Shelf> shelves;
    private List<Street> streets;
    private List<Vehicle> vehicles;
    private List<String> rady;
    private int sirka_police = 20;
    private int dlzka_police = 20;
    private MainController controller = null;

    public Warehouse(MainController controller) {
        this.controller = controller;
        this.shelves = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        rady = new ArrayList<String>(){{
            add("A");
            add("B");
            add("C");
            add("D");
            add("E");
            add("F");
            add("G");
            add("H");
            add("I");
        }};
    }

    public List<Drawable> generateWarehouse(){
        List<Drawable> elements = new ArrayList<>();
        int regal = 0;
        for (String rada : rady) {
            if (rada == "A") {
                for (int i = 2; i < 22; i = i + 2) {
                    Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                    elements.add(polica);
                    polica.setController(controller);
                    polica.clickedOnShelf();
                    polica.getGui().get(0).setFill(Color.WHITE);
                    polica.getGui().get(0).setStroke(Color.BLACK);
                }
            } else if (rada == "I") {
                for (int i = 1; i < 20; i = i + 2) {
                    Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                    elements.add(polica);
                    polica.clickedOnShelf();
                    polica.setController(controller);
                    polica.getGui().get(0).setFill(Color.WHITE);
                    polica.getGui().get(0).setStroke(Color.BLACK);
                }
            } else {
                for (int i = 2; i < 22; i = i + 2) {
                    Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                    elements.add(polica);
                    polica.clickedOnShelf();
                    polica.setController(controller);
                    polica.getGui().get(0).setFill(Color.WHITE);
                    polica.getGui().get(0).setStroke(Color.BLACK);
                }
                for (int i = 1; i < 20; i = i + 2) {
                    Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                    elements.add(polica);
                    polica.clickedOnShelf();
                    polica.setController(controller);
                    polica.getGui().get(0).setFill(Color.WHITE);
                    polica.getGui().get(0).setStroke(Color.BLACK);
                }
            }
            regal++;
        }
        return elements;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
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
