package dhruv8sh;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller{
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider brightness, speed;
    @FXML
    private Button invertButton;
    @FXML
    private MenuButton modeBut;
    @FXML
    public void initialize(){
        applyAction();
        speed.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                applyAction();
            }
        });
        brightness.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                applyAction();
            }
        });
        colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                applyAction();
            }
        });
    }
    private int mode=3;
    private boolean directionLeft=false;
    @FXML
    private void applyAction(){
        StringBuilder sb=new StringBuilder("./src/main/resources/acer-predator-turbo-and-rgb-keyboard-linux-module/facer_rgb.py");
        sb.append(" -m ");
        sb.append(mode);
        sb.append(" -b ");
        sb.append((int)brightness.getValue());
        if(mode==0) {
            staticModeImplementation(sb, 1);
            staticModeImplementation(sb, 2);
            staticModeImplementation(sb, 3);
            staticModeImplementation(sb, 4);
        }
        else{
            sb.append(" -s ");
            sb.append((int) speed.getValue());
        }
        sb.append(" -d ");
        sb.append(directionLeft?2:1);
        if(mode!=2) {
            Color c = colorPicker.getValue();
            sb.append(" -cR ");
            sb.append((int) (c.getRed() * 255));
            sb.append(" -cB ");
            sb.append((int) (c.getBlue() * 255));
            sb.append(" -cG ");
            sb.append((int) (c.getGreen() * 255));
        }
        try {
            executeBashCommand(sb.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void staticModeImplementation(StringBuilder sb, int zone) {
        sb.append(" -z ");
        sb.append(zone);
        sb.append(" -d ");
        sb.append(directionLeft?2:1);
        if(mode!=2) {
            Color c = colorPicker.getValue();
            sb.append(" -cR ");
            sb.append((int) (c.getRed() * 255));
            sb.append(" -cB ");
            sb.append((int) (c.getBlue() * 255));
            sb.append(" -cG ");
            sb.append((int) (c.getGreen() * 255));
        }
        try {
            executeBashCommand(sb.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean executeBashCommand(String cmd) throws IOException, InterruptedException {
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line=buf.readLine())!=null) {
            System.out.println(line);
        }
        return false;
    }
    @FXML
    private void staticMode(){
        mode=0;
        modeBut.setText("Static");
        invertButton.setDisable(true);
        colorPicker.setDisable(false);
        applyAction();
    }
    @FXML
    private void neonMode(){
        mode=2;
        modeBut.setText("Neon");
        invertButton.setDisable(true);
        colorPicker.setDisable(true);
        applyAction();
    }
    @FXML
    private void waveMode(){
        mode=3;
        modeBut.setText("Wave");
        invertButton.setDisable(false);
        colorPicker.setDisable(true);
        applyAction();
    }
    @FXML
    private void zoomMode(){
        mode=5;
        modeBut.setText("Zoom");
        invertButton.setDisable(true);
        colorPicker.setDisable(false);
        applyAction();
    }
    @FXML
    private void shiftingMode(){
        mode=4;
        modeBut.setText("Shifting");
        invertButton.setDisable(false);
        colorPicker.setDisable(false);
        applyAction();
    }
    @FXML
    private void breathingMode(){
        mode=1;
        modeBut.setText("Breathing");
        invertButton.setDisable(true);
        colorPicker.setDisable(false);
        applyAction();
    }
    @FXML
    private void invertDirection(){
        directionLeft=!directionLeft;
        applyAction();
    }
}