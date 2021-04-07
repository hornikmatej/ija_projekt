package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.*;


/**
 * Trieda reprezentujuca cesty v sklade
 *
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Street implements  Drawable{

    /**
     * Prazdny konstruktor potrebny pre deserializaciu suboru YML
     */
    private Street() {
    }

    private String name;
    private Coordinate start;
    private Coordinate end;

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
     */
    public Street(String name, Coordinate start, Coordinate end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    /**
     * Funkcia vracia GUI
     * @return List<Shape> zoznam ulic v GUI
     */
    @JsonIgnore
    @Override
    public List<Shape> getGui() {
        return Arrays.asList(
                new Line(start.getX(), start.getY(), end.getX(), end.getY())
        );
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
}
