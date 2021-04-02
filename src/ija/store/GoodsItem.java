
package ija.store;


import java.time.LocalDate;

public class GoodsItem {

    protected Goods belongs_to;
    protected LocalDate localDate;

    public GoodsItem(Goods belongs_to, LocalDate localDate){
        this.belongs_to = belongs_to;
        this.localDate = localDate;
    }

    public Goods goods() {
        return this.belongs_to;
    }

    public boolean sell() {
        return this.belongs_to.remove(this);
    }
}