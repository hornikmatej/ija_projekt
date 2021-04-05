
package ija.store;


import ija.Coordinate;
import ija.Drawable;
import ija.MainController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shelf implements Drawable {
    private final Map<Goods, ArrayList<GoodsItem>> shelf;
    private List<Shape> gui;
    private String name;
    private MainController mainController = null;

    public Shelf(String name, Coordinate pos, double height, double width) {
        this.name = name;
        this.shelf = new HashMap<>();
        gui = new ArrayList<>();
        gui.add(new Rectangle(pos.getX(), pos.getY(), width, height));
    }

    public void setController(MainController controller) {
        mainController = controller;
    }

    public void put(GoodsItem goodsItem) {
        Goods goods = goodsItem.goods();
        if (this.shelf.containsKey(goods)) {
            (this.shelf.get(goods)).add(goodsItem);
        } else {
            ArrayList<GoodsItem> goods_list = new ArrayList<>();
            goods_list.add(goodsItem);
            this.shelf.put(goods, goods_list);
        }
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
                    Text item_name = new Text(50, 20+35, name);
                    item_name.setStroke(Color.BLACK);
                    items_gui.add(item_name);
                    mainController.printShelf(items_gui);

                }
            }
        });
    }


    @Override
    public List<Shape> getGui() {
        return gui;
    }
}