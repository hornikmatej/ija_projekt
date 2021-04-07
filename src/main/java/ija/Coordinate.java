package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;


/**
 * Trieda, ktorá sa zaoberá suradnicami
 * @version 1.0
 * @author Filip Brna, Matej Horník
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Coordinate {
    String name;
    private double x;
    private double y;

    /**
     * Prazdny konstruktor triedy
     */
    private Coordinate () {
    }

    /**
     * Konstruktor nastavi suradnice x,y
     * @param x suradnica X
     * @param y suradnica Y
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Funkcia vrati X suradnicu
     * @return vrati X suradnicu
     */
    public double getX() {
        return x;
    }

    /**
     * Funkcia nastavi X suradnicu
     * @param x nastavi X suradnicu
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Funkcia vrati Y suradnicu
     * @return vrati Y suradnicu
     */
    public double getY() {
        return y;
    }

    /**
     * Funkcia nastavi Y suradnicu
     * @param y nastavi Y suradnicu
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Funkcia nastavi nazov
     * @param name nastavi nazov
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Funkcia vrati nazov
     * @return String nazov
     */
    public String getName() {
        return name;
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
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }
    /**
     * Funkcia
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Funkcia prepise metodu toString
     * @return string repezentujuci tuto triedu
     */
    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
