
package ija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import ija.store.*;
import sun.nio.ch.FileChannelImpl;

import javax.swing.tree.VariableHeightLayoutCache;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;


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
        List<String> rady = new ArrayList<String>(){{
            add("A");add("B");add("C");add("D");add("E");
            add("F");add("G");add("H");add("I");
        }};
        Integer regal = 0;

        int sirka_police = 20;
        int dlzka_police = 20;

        //vytvorenie polic

            for (String rada : rady) {
                if ( rada == "A") {
                    for (int i = 2; i < 22; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i-2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                        elements.add(polica);
                        polica.setController(controller);
                        polica.clickedOnShelf();
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                }
                else if( rada == "I") {
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                }
                else{
                    for (int i = 2; i < 22; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(115 + regal * 75, 55 + (i-2) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                    for (int i = 1; i < 20; i = i + 2) {
                        Shelf polica = new Shelf(rada + i, new Coordinate(65 + regal * 75, 55 + (i) / 2 * (sirka_police + 5)), sirka_police, dlzka_police);
                        elements.add(polica);
                        polica.clickedOnShelf();
                        polica.setController(controller);
                        polica.getGui().get(0).setFill(Color.WHITE);
                        polica.getGui().get(0).setStroke(Color.BLACK);
                    }
                }
            }
            regal++;
        }

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(40,40));

//        List<Street> streets = new ArrayList<>();
//        Street vyklad = new Street("VYKLAD/NAKLAD", new Coordinate(40 , 40), new Coordinate(100, 40));

//        elements.add(vyklad);


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        Data data1 = mapper.readValue(new File("test.yml"),Data.class);

        System.out.println(data1.getCoordinates());
        System.out.println(data1.getVehicles());


        elements.addAll(data1.getStreets());
        elements.addAll(data1.getVehicles());


        controller.setElements(elements);
        controller.starTime(1);
//        mapper.writeValue(new File("test.yml"),data);
    }
}