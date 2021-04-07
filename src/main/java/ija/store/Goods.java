
package ija.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Trieda, ktorá sa zaoberá produktami
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class Goods {
    protected String type;
    protected List<GoodsItem> goods_items;


    /**
     * Konstruktor pre produkt
     * @param type typ produktu
     */
    public Goods(String type){
        this.type = type;
        this.goods_items = new ArrayList<>();
    }
    /**
     * Funkcia vrati nazov
     * @return String nazov
     */
    public String getName() {
        return this.type;
    }

    /**
     * Funkcia na pridanie novej polozky
     * @param goodsItem datum
     * @return boolean ak dana polozka uz bola pridana vrati false ak nie true
     */
    public boolean addItem(GoodsItem goodsItem) {
        if (this.goods_items.contains(goodsItem)) {
            return false;
        }
        this.goods_items.add(goodsItem);
        return true;
    }

    /**
     * Funkcia na vytvorenie novej polozky
     * @param localDate datum
     * @return GoodsItem vrati vytvorenu polozku
     */
    public GoodsItem newItem(LocalDate localDate) {
        GoodsItem newItem = new GoodsItem(this, localDate);
        this.addItem(newItem);
        return newItem;
    }

    /**
     * Funkcia na vymazanie produktu
     * @return boolean vrati hodnotu ci dany typ produktu bol vymazany alebo nie
     */
    public boolean remove(GoodsItem goodsItem) {
        if (this.goods_items.contains(goodsItem)) {
            this.goods_items.remove(goodsItem);
            return true;
        }
        return false;
    }

    /**
     * Funkcia na urcenie hodnoty ci je dany typ produktu prazdny alebo nie
     * @return boolean vrati hodnotu ci dany typ produktu je prazdny alebo nie
     */
    public boolean empty() {
        return this.goods_items.isEmpty();
    }

    /**
     * Funkcia na vypocet velkosti daneho typu produktu
     * @return int vrati velkost daneho typu produktu
     */
    public int size() {
        return this.goods_items.size();
    }

    /**
     * Funkcia
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods that = (Goods) o;
        return Objects.equals(type, that.type);
    }

    /**
     * Funkcia
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}