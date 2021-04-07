
package ija;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import javafx.application.Platform;

import ija.store.*;
import sun.nio.ch.FileChannelImpl;

import javax.swing.tree.VariableHeightLayoutCache;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;


public class Main extends Application {

    @Override
    public void start(Stage primarystage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        primarystage.setTitle("Warehouse");
        primarystage.setScene(scene);
        primarystage.show();

        MainController controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();
        List<Drawable> legend = new ArrayList<>();

        Warehouse warehouse = new Warehouse(controller);


        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(40, 40));

//        List<Street> streets = new ArrayList<>();
//        Street vyklad = new Street("VYKLAD/NAKLAD", new Coordinate(40 , 40), new Coordinate(100, 40));

//        elements.add(vyklad);


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        Data data1 = mapper.readValue(new File("data/warehouse.yml"), Data.class);

        warehouse.setStreets(data1.getStreets());
        warehouse.setVehicles(data1.getVehicles());
        elements.addAll(warehouse.generateWarehouse());
        legend.addAll(warehouse.setShelfLegend());
        String warehousedata = "data/sklad.csv";
        warehouse.fillWarehouse(warehousedata);


        elements.addAll(data1.getStreets());
        elements.addAll(data1.getVehicles());


        controller.setElements(elements);
        controller.setShelfLegend(legend);
        controller.starTime(1);

        primarystage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
//        mapper.writeValue(new File("test.yml"),data);
    }
}