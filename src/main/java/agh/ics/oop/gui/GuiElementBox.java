package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private VBox box;
    public GuiElementBox(IMapElement element) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(element.getImagePath()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        this.box = new VBox(5);
        Label text = new Label();
        if(element instanceof Animal){
            text = new Label(((Animal) element).getPosition().toString());
        } else if(element instanceof Grass){
            text = new Label("grass");
        }
        this.box.getChildren().addAll(imageView, text);
    }

    public VBox getBox(){
        return this.box;
    }
}
