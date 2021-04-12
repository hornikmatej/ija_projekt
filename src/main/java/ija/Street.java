package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.*;


/**
 * Trieda reprezentujuca cesty v sklade
 *
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
@JsonDeserialize(converter = Street.StreetConstructor.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Street implements  Drawable{

    private String name;
    private Coordinate start;
    private Coordinate end;
    private boolean closed = false;

    /**
     * Prazdny konstruktor potrebny pre deserializaciu suboru YML
     */
    private Street() {
    }

    @JsonIgnore
    private List<Shape> gui;
    /**
     * Fukncia nastavi nazov jednotlivej ulicky skladu
     * @param name nastavi nazov jednotlivej ulicky skladu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Fukncia nastavi pociatocne suradnice ulicky skladu
     * @param start nastavi pociatocne suradnice ulicky skladu
     */
    public void setStart(Coordinate start) {
        this.start = start;
    }

    /**
     * Fukncia nastavi koncove suradnice ulicky skladu
     * @param end nastavi koncove suradnice ulicky skladu
     */
    public void setEnd(Coordinate end) {
        this.end = end;
    }

    /**
     * Konstruktor pre ulicu skladu
     * @param name nazov ulice
     * @param start suradnice zaciatku ulice
     * @param end suradnice konca ulice
     * @param closed ak je dana ulica skladu uzavreta (true), otvorena (false)
     */
    public Street(String name, Coordinate start, Coordinate end, boolean closed) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.closed = closed;
        setGui();
    }

    /**
     * Funkcia nastavuje GUI
     */
    public void setGui(){
        gui = new ArrayList<>();
        gui.add(new Line(this.start.getX(), this.start.getY(), this.end.getX(), this.end.getY()));
        gui.get(0).setStroke(Color.DARKSLATEBLUE);
        gui.get(0).setStrokeWidth(1.5);
    }
    /**
     * Funkcia vracia True ak je ulicka uzavreta, False ak je otvorena
     * @return boolean vracia True ak je ulicka uzavreta, False ak je otvorena
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Funkcia vracia GUI
     * @return List<Shape> zoznam ulic v GUI
     */
    @JsonIgnore
    @Override
    public List<Shape> getGui() {
        return this.gui;
    }

    /**
     * Funkcia vrati pociatocne suradnice ulice
     * @return Coordinate vrati pociatocne suradnice ulice
     */
    public Coordinate getStart() {
        return start;
    }

    /**
     * Funkcia vrati koncove suradnice ulice
     * @return Coordinate vrati koncove suradnice ulice
     */
    public Coordinate getEnd() {
        return end;
    }

    /**
     * Funkcia vrati nazov ulice
     * @return String vrati nazov ulice
     */
    public String getName() {
        return name;
    }


    /**
     * Funkcia volana po kliknuti na ulicku v sklade, pokial je mozne ju uzavriet tak ju uzavrie
     */
    public void clickedOnStreet()
    {
        //TODO realne uzatvorit ulicku, nie iba zafarbit
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if( !name.equals("VYKLAD/NAKLAD") && !name.equals("HIGHWAY UP") && !name.equals("HIGHWAY DOWN")) {
                        gui.get(0).setStroke(Color.DARKRED);
                        gui.get(0).setStrokeWidth(2.5);
                        closed = true;
                    }
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if( !name.equals("VYKLAD/NAKLAD") && !name.equals("HIGHWAY UP") && !name.equals("HIGHWAY DOWN")) {
                        closed = false;
                        gui.get(0).setStroke(Color.DARKSLATEBLUE);
                        gui.get(0).setStrokeWidth(1.5);
                    }
                }
            }
        });
    }

    /**
     * Funkcia
     * @return VehicleConstructorCall
     */
    static class StreetConstructor extends StdConverter<Street, Street> {
        @Override
        public Street convert(Street value) {
            value.setGui();
            value.clickedOnStreet();
            return value;
        }
    }
}
