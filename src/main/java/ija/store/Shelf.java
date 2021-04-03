
package ija.store;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shelf  {
    private final Map<Goods, ArrayList<GoodsItem>> shelf;

    public Shelf() {
        this.shelf = new HashMap<>();
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
}