package ija;

import ija.store.Goods;
import ija.store.GoodsItem;
import ija.store.Shelf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Warehouse {

    private List<Shelf> shelves;
    private List<Street> streets;
    private List<Vehicle> vehicles;
    private List<String> rady;
    private int sirka_police = 20;
    private int dlzka_police = 20;
    private MainController controller = null;
    private boolean generatedWarehouse = false;
    private int kapacita_regalu = 5;

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


        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arrOfStr = line.split(",", 2);
                int pocet_veci = Integer.parseInt(arrOfStr[1]);
                for (int i = 0; i < pocet_veci; i++){
                    //najdenie prazdnej police
                    for (Shelf polica : shelves){
                        if (!polica.isFull()){
                            Goods goods1 = new Goods(arrOfStr[0]);
                            GoodsItem item = goods1.newItem(LocalDate.now());
                            polica.put(item);
                            break;
                        }
                    }
                }
            }
        }
        catch(FileNotFoundException exp){
            System.err.println("Subor nebol najdeny");
        }
        catch(IOException exception){
            System.err.println("Chyba pri citani dat");
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
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street, kapacita_regalu);
                        elements.add(polica);
                        shelves.add(polica);
                        polica.setController(controller);
                        polica.clickedOnShelf();
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                } else if (rada == "I") {
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street, kapacita_regalu);
                        elements.add(polica);
                        shelves.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                } else {
                    for (int i = 2; i < 22; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i - 2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street, kapacita_regalu);
                        elements.add(polica);
                        shelves.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police, shelf_street, kapacita_regalu);
                        elements.add(polica);
                        shelves.add(polica);
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

    public List<Drawable> setShelfLegend(){
        List<Drawable> legend = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Shelf polica = new Shelf(new Coordinate(100, 350 + i*30), 20, 20);
            legend.add(polica);
            if ( i == 0 ){polica.getGui().get(0).setFill(Color.WHITE);}
            else if ( i == 1 ){polica.getGui().get(0).setFill(Color.rgb(255, 255, 150, 1));}
            else if ( i == 2 ){polica.getGui().get(0).setFill(Color.rgb(255, 230, 0, 1));}
            else if ( i == 3 ){polica.getGui().get(0).setFill(Color.rgb(255, 150, 0, 1));}
            else if ( i == 4 ){polica.getGui().get(0).setFill(Color.rgb(255, 50, 0, 0.7));}
            else if ( i == 5 ){polica.getGui().get(0).setFill(Color.rgb(255, 0, 0, 1));}
            polica.getGui().get(0).setStroke(Color.BLACK);
        }
        return legend;
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

    public Map<String, Integer> getMapItems(){
        Map<String, Integer> slovnik = new HashMap<>();
        for (Shelf polica : shelves){
            for(Map.Entry<Goods, ArrayList<GoodsItem>> m : polica.getShelf().entrySet()){
                String goods_name = m.getKey().getName();
                Integer pocet_goods = m.getValue().size();
                if (!slovnik.containsKey(goods_name))
                    slovnik.put(goods_name, pocet_goods);
                else
                    slovnik.put(goods_name, slovnik.get(goods_name) + pocet_goods);
            }
        }
        return slovnik;
    }

    public ObservableList<Map<String, Object>> getTableMap(){
        ObservableList<Map<String, Object>> tablemap = FXCollections.<Map<String, Object>>observableArrayList();
        Map<String, Integer> slovnik = getMapItems();

        for(Map.Entry<String, Integer> m : slovnik.entrySet()){
            Map<String, Object> new_item = new HashMap<>();
            new_item.put("nazov", m.getKey());
            new_item.put("pocet" , m.getValue());
            tablemap.add(new_item);
        }
        return tablemap;
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
