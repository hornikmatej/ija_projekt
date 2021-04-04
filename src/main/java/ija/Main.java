
package ija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import ija.store.*;

import javax.swing.tree.VariableHeightLayoutCache;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primarystage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        primarystage.setScene(scene);
        primarystage.show();

        MainController controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();
        elements.add(new Vehicle(new Coordinate(100 , 100), 10 , new Path(Arrays.asList(
                new Coordinate(100, 100),
                new Coordinate(100, 300),
                new Coordinate(300, 300)
        ))));
        elements.add(new Street("sikma ucka", new Coordinate(100 , 100), new Coordinate(100, 300)));
        int sirka_police = 20;
        int dlzka_police = 20;

        //vytvorenie polic
        for (int i = 0; i < 6; i++){
            Shelf polica = new Shelf("Polica" + i, new Coordinate(200 , 100 + i * (sirka_police + 5)), sirka_police, dlzka_police);
            elements.add(polica);
            polica.clickedOnShelf();
            polica.getGui().get(0).setFill(Color.WHITE);
            polica.getGui().get(0).setStroke(Color.BLACK);
        }

        controller.setElements(elements);
        controller.starTime();
    }
}