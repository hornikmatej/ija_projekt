
package ija.store;


import ija.Coordinate;
import ija.Drawable;
import ija.MainController;
import ija.Street;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

import java.util.*;

public class Shelf implements Drawable {
    private final Map<Goods, ArrayList<GoodsItem>> shelf;
    private List<Shape> gui;
    private String name;
    private MainController mainController = null;
    private Coordinate pos;
    private Street street;
    private int kapacita_regalu;

    private int zaplnenost = 0;

    public Shelf(String name, Coordinate pos, double height, double width, Street street, int kap) {
        this.name = name;
        this.kapacita_regalu = kap;
        this.shelf = new HashMap<>();
        gui = new ArrayList<>();
        this.pos = pos;
        this.street = street;
        Rectangle rectangle = new Rectangle(pos.getX(), pos.getY(), width, height);
        gui.add(rectangle);
    }

    public int getZaplnenost() {
        return zaplnenost;
    }

    public String getName() {
        return name;
    }

    public boolean isFull(){
        if (kapacita_regalu == zaplnenost){
            return true;
        }
        else{
            return false;
        }
    }


    public void setController(MainController controller) {
        mainController = controller;
    }

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

    public GoodsItem removeAny(Goods goods) {
        List<GoodsItem> list_goods = this.shelf.get(goods);
        if (list_goods == null) {
            return null;
        } else {
            return list_goods.isEmpty() ? null : list_goods.remove(0);
        }
    }

    public int size(Goods goods) {
        List<GoodsItem> list_goods = this.shelf.get(goods);
        return list_goods == null ? 0 : list_goods.size();
    }


    public void clickedOnShelf() {
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    List <Shape> items_gui = new ArrayList<>();
                    // TODO prejst policu a vyplnit zoznam vecami
                    int y_ax = 0;
                    Text shelf_name = new Text(50, 25, getName()+"\nzaplnenost: "+getZaplnenost());
                    items_gui.add(shelf_name);
                    shelf_name.setStroke(Color.GREY);
                    for (Map.Entry<Goods, ArrayList<GoodsItem>> entry : shelf.entrySet()) {
                        Text item_name = new Text(50, 60 + y_ax, entry.getKey().getName() + ", " + size(entry.getKey()));
                        item_name.setStroke(Color.BLACK);
                        items_gui.add(item_name);
                        y_ax = y_ax + 15;
                    }
                    mainController.printShelf(items_gui);

                }
            }
        });
    }

    public void fillShelfs(){
        if (zaplnenost == 1){
            gui.get(0).setFill(Color.rgb(255, 255, 150, 1));
        }
        else if (zaplnenost == 2){
            gui.get(0).setFill(Color.rgb(255, 230, 0, 1));
        }
        else if (zaplnenost == 3){
            gui.get(0).setFill(Color.rgb(255, 150, 0, 1));
        }
        else if (zaplnenost == 4){
            gui.get(0).setFill(Color.rgb(255, 50, 0, 1));
        }
        else if (zaplnenost == 5){
            gui.get(0).setFill(Color.rgb(255, 0, 0, 1));
        }
    }

    @Override
    public List<Shape> getGui() {
        return gui;
    }

}