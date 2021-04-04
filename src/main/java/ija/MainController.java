package ija;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {

    @FXML
    private Pane content;

    @FXML
    private TextField timeScale;


    private List<Drawable> elements = new ArrayList<>();
    private List<TimeUpdate> updates = new ArrayList<>();

    private Timer timer;
    private LocalTime time = LocalTime.now();
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

    public void setElements(List<Drawable> elements) {
        this.elements = elements;
        for (Drawable drawable : elements){
            content.getChildren().addAll(drawable.getGui());
            if (drawable instanceof TimeUpdate){
                updates.add((TimeUpdate) drawable);
            }
        }

    }
    public void starTime(float scale){
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = time.plusSeconds(1);
                for (TimeUpdate update : updates){
                    update.update(time);
                }
            }
        }, 0 , (long) (1000 / scale));
    }

}
