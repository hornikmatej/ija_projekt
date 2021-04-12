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



/**
 * Trieda, ktorá sa zaoberá Skladom a vsetkymi vecami s nim spojenymi
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class Warehouse {

    private List<Shelf> shelves;
    private List<Street> streets;
    private List<Vehicle> vehicles;
    private List<String> rady;
    private List<Request> poziadavky;
    private int sirka_police = 20;
    private int dlzka_police = 20;
    private MainController controller = null;
    private boolean generatedWarehouse = false;
    private int kapacita_regalu = 5;

    /**
     * Konstruktor pre triedu Warehouse (sklad)
     * @param controller hlavny controller z ktoreho je tato funkcia volana
     */

    public Warehouse(MainController controller) {
        this.controller = controller;
        this.shelves = new ArrayList<>();
        this.streets = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.poziadavky = new ArrayList<>();
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

    /**
     * Funckia vracia list s poziadavkami ktore sa maju vykonat
     * @return List<Request> vracia list s poziadavkami
     */
    public List<Request> getPoziadavky() {
        return poziadavky;
    }

    /**
     * Funckia prida poziadavku do zoznamu poziadaviek
     * @param req poziadavka
     */
    public void appendRequest(Request req){
        this.poziadavky.add(req);
    }


    /**
     * Funckia vrati vozidla v sklade
     * @return List<Vehicle> list vozdiel
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Funkcia vrati list polic v sklade
     * @return List<Shelf> zoznam policiek
     */
    public List<Shelf> getShelves() {
        return shelves;
    }

    /**
     * Funckia vytvori cestu ktorou pojde vozidlo pre veci v sklade
     * @param auto vozdilo v sklade
     */
    public void createPath (Vehicle auto){
        Request poz = poziadavky.remove(0);
        List<Coordinate> coordinates = new ArrayList<>();
        Coordinate start = null;
        Coordinate start_vyklad = null, end_vyklad = null;
        //najdenie pociatocnej cesty
        for (Street ulica : this.streets){
            if (ulica.getName().equals("VYKLAD/NAKLAD")){
                coordinates.add(start_vyklad = new Coordinate(ulica.getStart().getX(), ulica.getStart().getY()));
                coordinates.add(end_vyklad = new Coordinate(ulica.getEnd().getX(), ulica.getEnd().getY()));
            } else if (ulica.getName().equals("HIGHWAY DOWN")){
                start = new Coordinate(ulica.getStart().getX(), ulica.getStart().getY());
            }
        }
        double x = poz.getShelves().get(0).getStreet().getStart().getX();
        double y = poz.getShelves().get(0).getStreet().getStart().getY();
        coordinates.add(new Coordinate(x, y));
        Shelf prev_street = null;
        for (int i = 0; i < poz.getShelves().size(); i++) {
            if (prev_street != null){
                if (!prev_street.getStreet().equals(poz.getShelves().get(i).getStreet())){
                    double new_x = prev_street.getStreet().getStart().getX();
                    double new_y = prev_street.getStreet().getStart().getY();
                    coordinates.add(new Coordinate(new_x,new_y));

                    double new1_x = poz.getShelves().get(i).getStreet().getStart().getX();
                    double new1_y = poz.getShelves().get(i).getStreet().getStart().getY();
                    coordinates.add(new Coordinate(new1_x,new1_y));
                }
            }
            prev_street = poz.getShelves().get(i);
            String shelfname = poz.getShelves().get(i).getName().substring(1);
            double x_shelf = poz.getShelves().get(i).getStreet().getStart().getX(), y_shelf = 0;
            switch (shelfname) {
                case "1":
                case "2":
                    y_shelf = 65;
                    break;
                case "3":
                case "4":
                    y_shelf = 91;
                    break;
                case "5":
                case "6":
                    y_shelf = 117;
                    break;
                case "7":
                case "8":
                    y_shelf = 142;
                    break;
                case "9":
                case "10":
                    y_shelf = 166;
                    break;
                case "11":
                case "12":
                    y_shelf = 191;
                    break;
                case "13":
                case "14":
                    y_shelf = 215;
                    break;
                case "15":
                case "16":
                    y_shelf = 240;
                    break;
                case "17":
                case "18":
                    y_shelf = 267;
                    break;
                case "19":
                case "20":
                    y_shelf = 290;
                    break;
            }
            coordinates.add(new Coordinate(x_shelf, y_shelf));
        }
        //cesta domov
        coordinates.add(new Coordinate(prev_street.getStreet().getEnd().getX(), prev_street.getStreet().getEnd().getY()));
        coordinates.add(start);
        coordinates.add(end_vyklad);
        coordinates.add(start_vyklad);
        
        auto.setPath(new Path(coordinates));
        auto.setPoziadavka(poz);
    }



    /**
     * Funkcia, pomocou ktorej je inicializovany sklad zo suboru
     * @param filename nazov suboru z ktoreho bude sklad inicializovany
     */
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

    /**
     * Funkcia vygeneruje jednotlive regali v sklade
     * @return List<Drawable> zoznam jednotlivych elementov, ktore budu vykreslene
     */
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


    /**
     * Funkcia prehladava ci je zadana ulica medzi definovanymi
     * @param nameOfStreet hladana ulica
     * @return najdenu ulicu
     */
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

    /**
     * Funkcia funckia vrati slovnik s polozkami v sklade
     * @return Mapu s vecami v sklade a ich poctom
     */
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

    /**
     * Funckia vytvori mapu potrebnu pre vypisanie tabulky v gui
     * @return Mapa veci v sklade
     */
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

    /**
     * Funkcia nastavy aktualny objekt na streets
     * @param streets
     */
    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    /**
     * Funkcia nastavy aktualny objekt na vehicles
     * @param vehicles
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        for (Vehicle auto : this.vehicles){
            auto.setController(this.controller);
            auto.clickedOnVehicle();
        }
    }

    /**
     * Funkcia pomocou ktorej zistime aku kapacitu ma regal
     * @return int - hodnotu kapacity regalu
     */
    public int getKapacita_regalu() {
        return kapacita_regalu;
    }

}
