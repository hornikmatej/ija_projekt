package ija;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;


/**
 * Trieda reprezentujuca trasu voziku
 *
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
public class Path {
    private List<Coordinate> path;

    /**
     * Prazdny konstruktor potrebny pre deserializaciu suboru YML
     */
    private Path(){
    }

    /**
     * Konstruktor cesty podla suradnic
     * @param path suradnice cesty voziku
     */
    public Path(List<Coordinate> path) {
        this.path = path;
    }

    /**
     * Funkcia vrati trasu voziku
     * @return vrati trasu voziku
     */
    public List<Coordinate> getPath() {
        return path;
    }

    /**
     * Funkcia vrati vzdialenost medzi dvomi suradnicami
     * @param a suradnice 1. bodu
     * @param b suradnice 2. bodu
     * @return vrati hodnotu vzdialenosti
     */
    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    /**
     * Funkcia nastavi cestu pre vozik
     * @param path nastavenie cesty pre vozik
     */
    public void setPath(List<Coordinate> path) {
        this.path = path;
    }

    /**
     * Funkcia na zistenie suradnic z prejdenej vzdialenosti
     * @param distance hodnota prejdenej vzdialenosti
     * @return suradnice prejdenej vzdialenosti
     */
    public Coordinate getCoordinateByDistance(double distance){
        double lenght = 0;
        double currentLenght = 0;


        Coordinate a = null;
        Coordinate b = null;
        for (int i = 0; i < path.size() - 1; i++){
            a = path.get(i);
            b = path.get(i+1);
            currentLenght = getDistanceBetweenCoordinates(a, b);
            if (lenght + currentLenght >= distance)
                break;
            lenght += currentLenght;
        }
        if (a == null || b == null){
            return null;
        }

        double driven = (distance - lenght) / currentLenght;
        return new Coordinate(a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
    }


    /**
     * Funkcia na zistenie velkosti trasy
     * @return velkost trasy
     */
    @JsonIgnore
    public double getPathSize() {
        double size = 0;
        for (int i = 0; i < path.size() - 1; i++){
            size += getDistanceBetweenCoordinates(path.get(i), path.get((i+1)));
        }
        return size;
    }
}
