
package ija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import javafx.application.Platform;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Hlavna trieda aplikacie
 * @version 1.0
 * @author Filip Brna, Matej Hornik
 */
public class Main extends Application {

    /**
     * Funkcia volana pri starte aplikacie, nacitanie dat zo suborov (.yml,.csv)
     * @param primarystage  hlavne okno aplikacie
     * @throws Exception vyhodi vynimky ktore mozu nastat pocas behu aplikacie
     */
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

        Warehouse warehouse = new Warehouse(controller);
        controller.setWarehouse(warehouse);

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(40, 40));


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        Data data1 = mapper.readValue(new File("data/warehouse.yml"), Data.class);

        warehouse.setStreets(data1.getStreets());
        warehouse.setVehicles(data1.getVehicles());
        elements.addAll(warehouse.generateWarehouse());
        String warehousedata = "data/sklad.csv";
        warehouse.fillWarehouse(warehousedata);


        elements.addAll(data1.getStreets());
        elements.addAll(data1.getVehicles());

        controller.setKapacita_label(warehouse.getKapacita_regalu());
        controller.setShelfLegend();
        controller.setElements(elements);
        controller.starTime(1);
        controller.initTable();
        controller.updateTable(warehouse);

        primarystage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        
//        mapper.writeValue(new File("test.yml"),data);
    }
}