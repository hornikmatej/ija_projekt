package ija;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.application.Platform;


import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import java.time.Clock;

public class MainController {

    @FXML
    private Pane content;

    @FXML
    private TextField timeScale;

    @FXML
    private Pane items;

    @FXML
    private Label time_label;

    @FXML
    private Button clear_left_side;


    private List<Drawable> elements = new ArrayList<>();
    private List<TimeUpdate> updates = new ArrayList<>();
    private Data data;

    private Timer timer;
    private LocalTime time = LocalTime.of(0,00,00);
    private LocalTime maxTime = LocalTime.of(23,59,59);


    @FXML
    private void  onTimeScaleChange() {
        try {
            float scale = Float.parseFloat(timeScale.getText());
            if (scale <= 0){
                Alert alert =new Alert(Alert.AlertType.ERROR, "Invalid timeScale");
                alert.showAndWait();
                return;
            }
            timer.cancel();
            starTime(scale);
        }catch(NumberFormatException e){
            Alert alert =new Alert(Alert.AlertType.ERROR, "Invalid timeScale");
            alert.showAndWait();
        }
    }
    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    @FXML
    private void clear_left_side()
    {
        deleteLine();
    }

    public void setElements(List<Drawable> elements) {
        this.elements = elements;
        for (Drawable drawable : elements){
            content.getChildren().addAll(drawable.getGui());
            if (drawable instanceof TimeUpdate){
                updates.add((TimeUpdate) drawable);
            }
        }
    }


    public void printShelf(List<Shape> elements){
        deleteLine();
        for (Shape shape : elements){
            items.getChildren().addAll(shape);
        }
    }

    public void deleteLine() {
        items.getChildren().removeAll(items.getChildren());
    }


    @FXML
    public void setLabel (LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time_label.setText(time.format(formatter));
    }

    public void starTime(float scale){
            timer = new Timer(false);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(()->{setLabel(time);});

                    for (TimeUpdate update : updates)
                    {
                        Platform.runLater(()->{
                            update.update(time);
                        });
                    }
                    time = time.plusSeconds(30);
            }
        }, 0 , (long) (100 / scale));
    }

    public void setMap(Data data){
        this.data = data;
    }

}
