package ija;



import ija.store.Goods;
import ija.store.GoodsItem;
import ija.store.Shelf;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.application.Platform;
import java.time.LocalTime;


/**
 * Trieda, ktorá sa zaoberá ako s GUI samotným tak aj so všetkými s ním spojenými akciami
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
public class MainController {

    @FXML
    private Pane content;

    @FXML
    private TextField timeScale;

    @FXML
    private Pane items;

    @FXML
    private Label time_label;

    @FXML
    private Label kapacita_label;

    @FXML
    private Button clear_left_side;

    @FXML
    private TableView skladtable;

    @FXML
    private TableColumn<Map, String> nazov;

    @FXML
    private TableColumn<Map, Integer> pocet;

    @FXML
    private TextField poziadavka;

    private List<Drawable> elements = new ArrayList<>();
    private List<TimeUpdate> updates = new ArrayList<>();
    private Data data;
    private Warehouse warehouse;

    private Timer timer;
    private LocalTime time = LocalTime.of(1,00,00);
    private LocalTime from = LocalTime.of(1,00,00);
    private LocalTime to = LocalTime.of(8,30,59);



    /**
     *Funkcia,
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @FXML
    private void onRequest(){
        Map<String, Integer> slovnik = this.warehouse.getMapItems();
        String text = poziadavka.getText();
        String[] arrOfStr = text.split(";", 1000);
        for(String vec : arrOfStr) {
            String[] arr = vec.split(",", 2);
            try {
                int pocet_veci = Integer.parseInt(arr[1]);
                String nazov = arr[0];

                if (!slovnik.containsKey(nazov)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Tovar nebol najdeny");
                    alert.showAndWait();
                    return;
                }
                if (pocet_veci > slovnik.get(nazov)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Nedostatocne mnozstvo tovaru");
                    alert.showAndWait();
                    return;
                }
                Request poziadavka = new Request();
                for (Shelf polica : warehouse.getShelves()){
                    for(Map.Entry<Goods, ArrayList<GoodsItem>> m : polica.getShelf().entrySet()){
                        String goods_name = m.getKey().getName();
                        Integer pocet_goods = m.getValue().size();
                        if (goods_name.equals(nazov)){
                            //pridat do poziadavky
                            if (pocet_veci - pocet_goods >= 0){
                                poziadavka.prilozitTovar(polica,goods_name,pocet_goods);
                            }
                            else{
                                poziadavka.prilozitTovar(polica,goods_name,pocet_veci);
                            }

                            pocet_veci = pocet_veci - pocet_goods;
                            break;
                        }
                    }
                    if (pocet_veci <= 0){
                        break;
                    }
                }
                //vypis poziadavku
                // TODO optimalizovat cestu
                for (int i = 0; i < poziadavka.getShelves().size(); i++){
                    System.out.println(poziadavka.getShelves().get(i).getName()+" "+ poziadavka.getTovar().get(i) +" "+
                            poziadavka.getPocet().get(i));
                }

            }
            catch (NumberFormatException exp){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Zle zadane hodnoty");
                alert.showAndWait();
                return;
            }
            catch (ArrayIndexOutOfBoundsException exp){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Zle zadane hodnoty");
                alert.showAndWait();
                return;
            }

        }
        poziadavka.clear();
    }

    /**
     * Funkcia, ktora nastavuje Label vypisany v GUI
     * @param kapacita Integer nasledne pretypovany na String
     */
    public void setKapacita_label(Integer kapacita) {
        kapacita_label.setText(String.valueOf(kapacita));
    }

    /**
     * Funkcia, ktorou je mozne urychlit v aplikacii čas zadanim hodnoty do textoveho pola
     */
    @FXML
    private void  onTimeScaleChange() {
        try {
            float scale = Float.parseFloat(timeScale.getText());
            if (scale <= 0){
                Alert alert =new Alert(Alert.AlertType.ERROR, "Chybná zadaná hodnota ( 0 <= zadaná hodnota <= 1000)");
                alert.showAndWait();
                return;
            }
            timer.cancel();
            starTime(scale);
        }catch(NumberFormatException e){
            Alert alert =new Alert(Alert.AlertType.ERROR, "Chybná zadaná hodnota ( 0 <= zadaná hodnota <= 1000)");
            alert.showAndWait();
        }catch(IllegalArgumentException e){
            Alert alert =new Alert(Alert.AlertType.ERROR, "Chybná zadaná hodnota ( 0 <= zadaná hodnota <= 1000)");
            alert.showAndWait();
        }

    }
    /**
     * Funkcia, vdaka ktorej je mozne sklad priblizovat a oddialovat
     * @param event ScrollEvent - na zaklade koleska mysi vykonava akciu (zoom)
     */
    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }


    /**
     * Funkcia, ktora je aktivovana po stlaceni tlacitka v gui,
     * jej ulohou je vymazat zobrazane udaje o regali.
     */
    @FXML
    private void clear_left_side()
    {
        deleteLine();
    }

    /**
     * Funkcia
     * @param elements List<Drawable>
     */
    public void setElements(List<Drawable> elements) {
        this.elements = elements;
        for (Drawable drawable : elements){
            content.getChildren().addAll(drawable.getGui());
            if (drawable instanceof TimeUpdate){
                updates.add((TimeUpdate) drawable);
            }
        }
    }

    /**
     * Funkcia, ktora vypise legendu o zaplnenosti regalov
     */
    public void setShelfLegend() {
        for (int i = 0; i < 6; i++) {
            Rectangle rectangle = new Rectangle(100, 350 + i*30, 20, 20);
            if ( i == 0 ){rectangle.setFill(Color.WHITE);}
            else if ( i == 1 ){rectangle.setFill(Color.rgb(255, 255, 150, 1));}
            else if ( i == 2 ){rectangle.setFill(Color.rgb(255, 230, 0, 1));}
            else if ( i == 3 ){rectangle.setFill(Color.rgb(255, 150, 0, 1));}
            else if ( i == 4 ){rectangle.setFill(Color.rgb(255, 50, 0, 0.7));}
            else if ( i == 5 ){rectangle.setFill(Color.rgb(255, 0, 0, 1));}
            rectangle.setStroke(Color.BLACK);
            content.getChildren().addAll(rectangle);
        }
    }

    /**
     * Funkcia
     * @param elements List<Shape>
     */
    public void printShelf(List<Shape> elements){
        deleteLine();
        for (Shape shape : elements){
            items.getChildren().addAll(shape);
        }
    }

    /**
     * Funkcia
     */
    public void deleteLine() {
        items.getChildren().removeAll(items.getChildren());
    }

    public void initTable(){
        nazov.setCellValueFactory(new MapValueFactory<>("nazov"));
        pocet.setCellValueFactory(new MapValueFactory<>("pocet"));
    }

    /**
     * Funkcia
     * @param warehouse Warehouse
     */
    @SuppressWarnings("unchecked")
    public void updateTable(Warehouse warehouse){
        ObservableList<Map<String, Object>> tableMap = warehouse.getTableMap();

        skladtable.getItems().removeAll();
        skladtable.getItems().addAll(tableMap);
    }

    /**
     * Funkcia, ktora nastavuje label zobrazujuci cas v GUI
     * @param time LocalTime aktualny cas v aplikacii
     */
    @FXML
    public void setLabel (LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time_label.setText(time.format(formatter));
    }


    // TODO zmenit na (long) (1000 / scale)

    /**
     * Funkcia, ktora nastavuje pociatocny cas v aplikacii
     *  a kazdu sekundu ho obnovuje
     * @param scale float - hodnota zadana uzivatelom, umoznuje urychlit cas v aplikacii
     */
    public void starTime(float scale){
            timer = new Timer(false);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(()->{setLabel(time);});

                    for (TimeUpdate update : updates)
                    {
                        Platform.runLater(()->{
                            update.update(time);
                        });
                    }
                    time = time.plusSeconds(30);
                    if (time.isAfter(to))
                        Platform.runLater(()->{
                            time = from;
                        });
            }
        }, 0 , (long) (100 / scale));
    }

}
