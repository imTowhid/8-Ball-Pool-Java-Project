package BallandCue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;


public class ScrollBar {

    Group root;
    Circle circle;

    ScrollBar(Group root){
        this.root = root;
        Stop[] stop = new Stop[] {new Stop(0, Color.ORANGE), new Stop(1, Color.BLACK)};
        LinearGradient linearGradient = new LinearGradient(0,.5,1,.5, true, CycleMethod.REFLECT,stop);
        circle = new Circle(8);
        circle.setLayoutX(26.3);
        circle.setLayoutY(250);
        circle.setFill(linearGradient);

        root.getChildren().addAll(circle);

    }

    public Circle getCircle() {
        return circle;
    }
}
