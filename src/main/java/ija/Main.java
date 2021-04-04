
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

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(100,100));
//        Vehicle vehicle = new Vehicle(coordinates.get(0), 10 , new Path(Arrays.asList(
//                new Coordinate(50, 50),
//                new Coordinate(500, 50)
//        )));
        //elements.add(vehicle);
        elements.add(new Street("VYKLAD/NAKLAD", new Coordinate(50 , 50), new Coordinate(100, 50)));
        elements.add(new Street("HIGHWAY UP", new Coordinate(100 , 50), new Coordinate(500, 50)));
        elements.add(new Street("HIGHWAY DOWN", new Coordinate(100 , 300), new Coordinate(500, 300)));
        elements.add(new Street("A", new Coordinate(100 , 50), new Coordinate(100, 300)));
        elements.add(new Street("B", new Coordinate(150 , 50), new Coordinate(150, 300)));
        elements.add(new Street("C", new Coordinate(200 , 50), new Coordinate(200, 300)));
        elements.add(new Street("D", new Coordinate(250 , 50), new Coordinate(250, 300)));
        elements.add(new Street("E", new Coordinate(300 , 50), new Coordinate(300, 300)));
        elements.add(new Street("F", new Coordinate(350 , 50), new Coordinate(350, 300)));
        elements.add(new Street("G", new Coordinate(400 , 50), new Coordinate(400, 300)));
        elements.add(new Street("H", new Coordinate(450 , 50), new Coordinate(450, 300)));
        elements.add(new Street("I", new Coordinate(500 , 50), new Coordinate(500, 300)));
////        controller.setElements(elements);
//        controller.starTime(1);

//        Data data = new Data(coordinates, vehicle);


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        Data data1 = mapper.readValue(new File("test.yml"),Data.class);
        System.out.println(data1);

        elements.add(data1.getVehicle());

        controller.setElements(elements);
        controller.starTime(1);
        //mapper.writeValue(new File("test.yml"),data);
    }
}