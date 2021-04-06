package ija;

import ija.store.Shelf;
import javafx.scene.paint.Color;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Warehouse {

    private List<Shelf> shelves;
    private List<Street> streets;
    private List<Vehicle> vehicles;
    private List<String> rady;
    private int sirka_police = 20;
    private int dlzka_police = 20;
    private MainController controller = null;
    private boolean generatedWarehouse = false;

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

    public void fillWarehouse(String filename){
        if (generatedWarehouse == false){
            System.err.println("Nemozno naplnit sklad, ktory sa este nevygeneroval");
            System.exit(-1);
        }
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        }
        catch(FileNotFoundException exception)
        {
            System.err.println("Subor nebol najdeny");
        }
        catch(IOException exception){
            System.err.println("Chyba pri citani dat");
        }
        catch (CsvException exception){
            System.err.println("Chyba pri citani dat z csv suboru");
        }
    }


    public List<Drawable> generateWarehouse(){
        if (generatedWarehouse == false) {
            List<Drawable> elements = new ArrayList<>();
            int regal = 0;
            for (String rada : rady) {
                Street shelf_street = findStreet(rada);
                if (shelf_street == null) {
                    System.err.println("Zadana ulica nebola najdena");
                    System.exit(-1);
                }
                if (rada == "A") {
                    for (int i = 2; i < 22; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street);
                        elements.add(polica);
                        polica.setController(controller);
                        polica.clickedOnShelf();
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                } else if (rada == "I") {
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                } else {
                    for (int i = 2; i < 22; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                }
                regal++;
            }
            generatedWarehouse = true;
            return elements;
        }
        System.err.println("Sklad bol uz vygenerovany");
        System.exit(-1);
        return null;
    }

    private Street findStreet(String nameOfStreet){
        Street found = null;
        for (Street street : streets) {
            if (street.getName().equals(nameOfStreet)){
                found = street;
                break;
            }
        }
        return found;
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
