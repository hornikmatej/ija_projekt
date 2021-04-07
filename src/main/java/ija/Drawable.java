package ija;

import javafx.scene.shape.Shape;

import java.util.List;

/**
 * Rozhranie ktore implementuje vsetky objekty vykreslene v aplikacii
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
public interface Drawable {
    /**
     * Funkcia vrati GUI objektu
     * @return List<Shape>
     */
    List<Shape> getGui();
}
