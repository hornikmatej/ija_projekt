
package ija.store;


import ija.Coordinate;
import ija.Drawable;
import ija.MainController;
import ija.Street;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.*;


/**
 * Trieda, ktorá sa zaoberá suradnicami, implementuje Drawable
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class Shelf implements Drawable {
    private final Map<Goods, ArrayList<GoodsItem>> shelf;
    private List<Shape> gui;
    private String name;
    private MainController mainController = null;
    private Coordinate pos;
    private Street street;
    private int kapacita_regalu;
    private int zaplnenost = 0;
    private Map<String, Integer> to_pick_up;


    /**
     * Konstruktor nastavi suradnice nazov, suradnice, vysku, sirku, ulicu a kapacitu regalu
     * @param name nazov regalu
     * @param pos suradnice regalu
     * @param height vyska regalu
     * @param width sirka regalu
     * @param street ulica na ktorej je regal
     * @param kap kapacita regalu
     */
    public Shelf(String name, Coordinate pos, double height, double width, Street street, int kap) {
        this.name = name;
        this.kapacita_regalu = kap;
        this.shelf = new HashMap<>();
        this.to_pick_up = new HashMap<>();
        gui = new ArrayList<>();
        this.pos = pos;
        this.street = street;
        Rectangle rectangle = new Rectangle(pos.getX(), pos.getY(), width, height);
        Text text = new Text(pos.getX() + 3, pos.getY() + 14, name);
        text.setStrokeWidth(0);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8.5));
        gui.add(rectangle);
        gui.add(text);
    }

    /**
     * Funkcia
     * @return
     */
    public Map<Goods, ArrayList<GoodsItem>> getShelf() {
        return shelf;
    }

    /**
     * Funkcia vrati aktualnu zaplnenost regalu
     * @return int vrati aktualnu zaplnenost regalu
     */
    public int getZaplnenost() {
        return zaplnenost;
    }

    /**
     * Funkcia vrati nazov regalu
     * @return String vrati nazov regalu
     */
    public String getName() {
        return name;
    }

    /**
     * Funkcia zisti ci je regal plny alebo nie
     * @return boolean vrati ci je regal plny alebo nie
     */
    public boolean isFull(){
        if (kapacita_regalu == zaplnenost){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Funkcia nastavi ovladac
     * @param controller nastavi ovladac
     */
    public void setController(MainController controller) {
        mainController = controller;
    }

    /**
     * Funkcia zisti ci bol dany produkt vlozeny do regalu alebo nie
     * @param goodsItem typ tovaru
     * @return boolean vrati ci bol dany produkt vlozeny do regalu alebo nie
     */
    public boolean put(GoodsItem goodsItem) {
        if (zaplnenost >= kapacita_regalu){
            return false;
        }
        Goods goods = goodsItem.goods();
        if (this.shelf.containsKey(goods)) {
            (this.shelf.get(goods)).add(goodsItem);
        } else {
            ArrayList<GoodsItem> goods_list = new ArrayList<>();
            goods_list.add(goodsItem);
            this.shelf.put(goods, goods_list);
        }
        zaplnenost++;
        fillShelfs();
        return true;
    }

    /**
     * Funkcia zisti ci zoznam obsahuje tovar
     * @param goods typ tovaru
     * @return boolean vrati ci zoznam obsahuje tovar
     */
    public boolean containsGoods(Goods goods) {
        List<GoodsItem> list_goods = this.shelf.get(goods);
        if (list_goods == null){
            for(Map.Entry<Goods, ArrayList<GoodsItem>> m : this.shelf.entrySet()){
                Goods tmp = m.getKey();
                if (tmp.getName().equals(goods.getName())){
                    return !tmp.empty();
                }
            }
        }
        return list_goods != null && !list_goods.isEmpty();
    }

    /**
     * Funkcia vymaze dany tovar n krat z regalu
     * @param goods_name nazov tovaru
     * @param n pocet vymazani
     */
    public void remove_pruduct_n(String goods_name, Integer n){
        Goods tovar = new Goods(goods_name);
        for (int i = 0; i < n; i++){
            this.removeAny(tovar);
        }
    }


    /**
     * Funkcia vymaze zo zoznamu produktov dany produkt
     * @param goods typ produktu
     * @return GoodsItem vrati vymazany produkt
     */
    public GoodsItem removeAny(Goods goods) {
        List<GoodsItem> list_goods = this.shelf.get(goods);
        if (list_goods == null) {
            return null;
        } else {
            GoodsItem removed =  list_goods.isEmpty() ? null : list_goods.remove(0);
            if (list_goods.isEmpty()){
                shelf.remove(goods);
            }
            if (!to_pick_up.containsKey(goods.getName()))
                to_pick_up.put(goods.getName(), 1);
            else
                to_pick_up.put(goods.getName(), to_pick_up.get(goods.getName()) + 1);
            zaplnenost--;
            this.fillShelfs();
            return removed;
        }
    }

    /**
     * Funkcia pocet produktov
     * @param goods typ produktu
     * @return int vrati pocet produktov
     */
    public int size(Goods goods) {
        List<GoodsItem> list_goods = this.shelf.get(goods);
        return list_goods == null ? 0 : list_goods.size();
    }


    /**
     * Funkcia, ktora vyvola akciu po kliknuti na regal
     * vypise informacie o regali na lavu stranu okna
     */
    public void clickedOnShelf() {
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    List <Shape> items_gui = new ArrayList<>();
                    // TODO prejst policu a vyplnit zoznam vecami
                    int y_ax = 60;
                    Text shelf_name = new Text(50, 25, getName()+"\nzaplnenost: "+getZaplnenost());
                    items_gui.add(shelf_name);
                    shelf_name.setStroke(Color.GREY);
                    for (Map.Entry<Goods, ArrayList<GoodsItem>> entry : shelf.entrySet()) {
                        Text item_name = new Text(50, y_ax, entry.getKey().getName() + ", " + size(entry.getKey()));
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }
                    Text pick = new Text(50, y_ax + 15, "Bude vyzdvihnute");
                    items_gui.add(pick);
                    pick.setStroke(Color.GREY);
                    y_ax = y_ax + 35;
                    for (Map.Entry<String, Integer> entry : to_pick_up.entrySet()) {
                        Text item_name = new Text(50, y_ax, entry.getKey() + ", " + entry.getValue());
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }

                    mainController.printShelf(items_gui);

                }
            }
        });
        gui.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    List <Shape> items_gui = new ArrayList<>();
                    // TODO prejst policu a vyplnit zoznam vecami
                    int y_ax = 60;
                    Text shelf_name = new Text(50, 25, getName()+"\nzaplnenost: "+getZaplnenost());
                    items_gui.add(shelf_name);
                    shelf_name.setStroke(Color.GREY);
                    for (Map.Entry<Goods, ArrayList<GoodsItem>> entry : shelf.entrySet()) {
                        Text item_name = new Text(50, y_ax, entry.getKey().getName() + ", " + size(entry.getKey()));
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }
                    Text pick = new Text(50, y_ax + 15, "Bude vyzdvihnute");
                    items_gui.add(pick);
                    pick.setStroke(Color.GREY);
                    y_ax = y_ax + 35;
                    for (Map.Entry<String, Integer> entry : to_pick_up.entrySet()) {
                        Text item_name = new Text(50, y_ax, entry.getKey() + ", " + entry.getValue());
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }

                    mainController.printShelf(items_gui);

                }
            }
        });
    }

    /**
     * Funkcia, ktora zafarbi regali podla ich aktualnej zaplnenosti
     */
    public void fillShelfs(){
        int percenta;
        if (zaplnenost != 0 ) {
            percenta = zaplnenost * 100 / kapacita_regalu;
        }
        else{
            percenta = 0;
        }
        if (percenta > 0 && percenta < 25){
            gui.get(0).setFill(Color.rgb(255, 255, 150, 1));
        }
        else if (percenta >= 25 && percenta < 50){
            gui.get(0).setFill(Color.rgb(255, 230, 0, 1));
        }
        else if (percenta >= 50 && percenta < 75){
            gui.get(0).setFill(Color.rgb(255, 150, 0, 1));
        }
        else if (percenta >= 75 && percenta < 100){
            gui.get(0).setFill(Color.rgb(255, 50, 0, 0.7));
        }
        else if (kapacita_regalu == zaplnenost){
            gui.get(0).setFill(Color.rgb(255, 0, 0, 1));
        }
    }

    /**
     * Funkcia vracia GUI
     * @return List<Shape> zoznam regalov v GUI
     */
    @Override
    public List<Shape> getGui() {
        return gui;
    }

}