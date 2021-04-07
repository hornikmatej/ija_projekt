
package ija.store;


import java.time.LocalDate;



/**
 * Trieda, ktorá sa zaoberá danou polozkou z typu tovaru
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class GoodsItem {

    protected Goods belongs_to;
    protected LocalDate localDate;

    /**
     * Konstruktor nastavi suradnice x,y
     * @param localDate datum
     * @param belongs_to patri ku
     */
    public GoodsItem(Goods belongs_to, LocalDate localDate){
        this.belongs_to = belongs_to;
        this.localDate = localDate;
    }

    /**
     * Funkcia vrati kam dany typ produktu patri
     * @return Goods vrati kam typ produktu patri
     */
    public Goods goods() {
        return this.belongs_to;
    }

    /**
     * Funkcia na predanie produktu
     * @return boolean vrati hodnotu ci dany typ produktu bol predany alebo nie
     */
    public boolean sell() {
        return this.belongs_to.remove(this);
    }
}