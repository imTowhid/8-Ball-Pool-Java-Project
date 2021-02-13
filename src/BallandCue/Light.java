package BallandCue;

import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;


public class Light {
   public Light(Group root){
        PointLight light=new PointLight(Color.WHITE);
        light.setTranslateX(500);
        light.setTranslateY(350);
        light.setTranslateZ(-500);
        root.getChildren().addAll(light);
    }
}
