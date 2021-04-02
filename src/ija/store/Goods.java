
package ija.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Goods {

    protected String type;
    protected List<GoodsItem> goods_items;

    public Goods(String type){
        this.type = type;
        this.goods_items = new ArrayList<>();

    }

    public String getName() {
        return this.type;
    }

    public boolean addItem(GoodsItem goodsItem) {
        if (this.goods_items.contains(goodsItem)) {
            return false;
        }
        this.goods_items.add(goodsItem);
        return true;
    }

    public GoodsItem newItem(LocalDate localDate) {
        GoodsItem newItem = new GoodsItem(this, localDate);
        this.addItem(newItem);
        return newItem;
    }

    public boolean remove(GoodsItem goodsItem) {
        if (this.goods_items.contains(goodsItem)) {
            this.goods_items.remove(goodsItem);
            return true;
        }
        return false;
    }

    public boolean empty() {
        return this.goods_items.isEmpty();
    }


    public int size() {
        return this.goods_items.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods that = (Goods) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}