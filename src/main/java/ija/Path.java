package ija;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Path {
    private List<Coordinate> path;

    private Path(){
    }

    public Path(List<Coordinate> path) {
        this.path = path;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public void setPath(List<Coordinate> path) {
        this.path = path;
    }

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

    @JsonIgnore
    public double getPathSize() {
        double size = 0;
        for (int i = 0; i < path.size() - 1; i++){
            size += getDistanceBetweenCoordinates(path.get(i), path.get((i+1)));
        }
        return size;
    }
}
