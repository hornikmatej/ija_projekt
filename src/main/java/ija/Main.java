
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
                regal++;
            }

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(40,40));
        Vehicle vehicle = new Vehicle(coordinates.get(0), 10 , new Path(Arrays.asList(
                new Coordinate(40, 40),
                new Coordinate(700, 40)
        )));

        List<Street> streets = new ArrayList<>();
        Street vyklad = new Street("VYKLAD/NAKLAD", new Coordinate(40 , 40), new Coordinate(100, 40));
        Street highwayUp = new Street("HIGHWAY UP", new Coordinate(100 , 40), new Coordinate(700, 40));
        Street highwayDown = new Street("HIGHWAY DOWN", new Coordinate(100 , 315), new Coordinate(700, 315));
        Street a = new Street("A", new Coordinate(100 , 40), new Coordinate(100, 315));
        Street b = new Street("B", new Coordinate(175 , 40), new Coordinate(175, 315));
        Street c = new Street("C", new Coordinate(250 , 40), new Coordinate(250, 315));
        Street d = new Street("D", new Coordinate(325 , 40), new Coordinate(325, 315));
        Street e = new Street("E", new Coordinate(400 , 40), new Coordinate(400, 315));
        Street f = new Street("F", new Coordinate(475 , 40), new Coordinate(475, 315));
        Street g = new Street("G", new Coordinate(550 , 40), new Coordinate(550, 315));
        Street h = new Street("H", new Coordinate(625 , 40), new Coordinate(625, 315));
        Street i = new Street("I", new Coordinate(700 , 40), new Coordinate(700, 315));

        elements.add(vyklad);
        elements.add(highwayUp);
        elements.add(highwayDown);
        elements.add(a);
        elements.add(b);
        elements.add(c);
        elements.add(d);
        elements.add(e);
        elements.add(f);
        elements.add(g);
        elements.add(h);
        elements.add(i);
        elements.add(vehicle);

//        streets.add(vyklad);
//        streets.add(highwayUp);
//        streets.add(highwayDown);
//        streets.add(a);
//        streets.add(b);
//        streets.add(c);
//        streets.add(d);
//        streets.add(e);
//        streets.add(f);
//        streets.add(g);
//        streets.add(h);
//        streets.add(i);

////        controller.setElements(elements);
//        controller.starTime(1);

        Data data = new Data(coordinates, vehicle, streets);


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        //Data data1 = mapper.readValue(new File("test.yml"),Data.class);
        //elements.addAll(data1.getStreet());
        //System.out.println(data1);

//        elements.add(data1.getVehicle());


        controller.setElements(elements);
        controller.starTime(1);
//        mapper.writeValue(new File("test.yml"),data);
    }
}