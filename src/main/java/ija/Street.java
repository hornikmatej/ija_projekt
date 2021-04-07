package ija;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.*;


//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "name")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Street implements  Drawable{

    private Street() {
    }

    private String name;
    private Coordinate start;
    private Coordinate end;

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }



    public Street(String name, Coordinate start, Coordinate end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    @JsonIgnore
    @Override
    public List<Shape> getGui() {
        return Arrays.asList(
                new Line(start.getX(), start.getY(), end.getX(), end.getY())
        );
    }
//    @JsonIgnore
//    @Override
//    public List<StackPane> getGuis() {
//        return Arrays.asList(
//                new Line(start.getX(), start.getY(), end.getX(), end.getY())
//        );
//    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }
}
