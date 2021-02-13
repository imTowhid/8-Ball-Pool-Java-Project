package BallandCue;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


public class HandleCue {
    Group root;
    double value = 0;
    boolean angleFlag = true;
    double angle;
    double a=0, b=180;
    double lineLength = 250;
    public double IniVelocity;
    Circle circle;
    Rectangle line;
    ImageView Stick;

    ScrollBar scrollBar;
    public double lineAngle;

    public HandleCue(Group root, Scene scene) {
        this.root = root;

        Image image = new Image("Extras\\Stick.png");
        Stick = new ImageView(image);
        Stick.setFitHeight(15);
        Stick.setFitWidth(350);
        Stick.setLayoutX(0);
        Stick.setLayoutY(-50);
        Stick.setEffect(new DropShadow());

        scrollBar = new ScrollBar(root);
        circle = scrollBar.getCircle();

        line = new Rectangle();
        line.setWidth(1.8);
        line.setHeight(lineLength);
        line.setFill(Color.AQUA);
        DropShadow dropShadow1 = new DropShadow();
        line.setEffect(dropShadow1);
        line.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS));

        scene.setOnKeyTyped(event -> {
            if(event.getCharacter().equals("a")){
                lineLength+=.5;
                line.setHeight(lineLength);
            }
            if(event.getCharacter().equals("d")){
                lineLength-=.5;
                line.setHeight(lineLength);
            }
            if(event.getCharacter().equals("w")){
                lineLength+=5;
                line.setHeight(lineLength);
            }
            if(event.getCharacter().equals("s")){
                lineLength-=5;
                line.setHeight(lineLength);
            }
        });


        root.getChildren().addAll(Stick, line);

    }

    void InitializeStick(double middleX, double middleY ){
        Stick.setLayoutX(middleX);
        Stick.setLayoutY(middleY);
        line.setLayoutX(middleX);
        line.setLayoutY(middleY);
    }

    public void RotateStick(Scene scene,double pivotx,double pivoty){

        if(angleFlag==true) {
            Stick.setOnMouseDragged(event -> {
                Stick.setCursor(Cursor.CLOSED_HAND);
                angle = Math.toDegrees(Math.atan2((event.getSceneY() - pivoty), (event.getSceneX() - pivotx)));
                if (angle < 0) {
                    angle += 360;
                }
                lineAngle=angle+180;
                lineAngle=lineAngle%360;
                Stick.getTransforms().addAll(new Rotate(angle - a, Rotate.Z_AXIS));

                line.getTransforms().addAll(new Rotate(lineAngle-b,Rotate.Z_AXIS));
                a = angle;
                b=lineAngle;

            });
        }
    }

    public  void MoveStick(double pivotx,double pivoty) {
        Stick.setLayoutX(pivotx + value * Math.cos(Math.toRadians(angle)));
        Stick.setLayoutY(pivoty + value * Math.sin(Math.toRadians(angle)));
    }


    public void setRotateStick(Double ang){
        lineAngle=(angle+180)%360;
        Stick.getTransforms().addAll(new Rotate(angle - a, Rotate.Z_AXIS));
        line.getTransforms().addAll(new Rotate(lineAngle- b, Rotate.Z_AXIS));
        a = angle;
        b=lineAngle;
    }



    public void setIniVelocity() {
        circle.setOnMouseDragged(event -> {
            if((event.getSceneY()-250)<200 && (event.getSceneY()-250)>0) {
                circle.setLayoutY(event.getSceneY());
                value = event.getSceneY() - 250;
                value=value*.5;
            }
        });
        circle.setOnMouseReleased(event -> {
            IniVelocity = value / 6.5;
            value=0;
            circle.setLayoutY(250);
        });
    }


    public void setIniVelocity(double iniVelocity){
        this.IniVelocity = iniVelocity;
    }

    public void setMoveStick(double pivotx,double pivoty){
        Stick.setLayoutX(pivotx +value * Math.cos(Math.toRadians(angle)));
        Stick.setLayoutY(pivoty + value * Math.sin(Math.toRadians(angle)));
    }



    public ImageView getStick() {
        return Stick;
    }

    public void setStick(ImageView stick) {
        Stick = stick;
    }

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isAngleFlag() {
        return angleFlag;
    }

    public void setAngleFlag(boolean angleFlag) {
        this.angleFlag = angleFlag;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getIniVelocity() {
        return IniVelocity;
    }

    public ScrollBar getScrollBar() {
        return scrollBar;
    }

    public void setScrollBar(ScrollBar scrollBar) {
        this.scrollBar = scrollBar;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public double getLineAngle() {
        return lineAngle;
    }

    public void setLineAngle(double lineAngle) {
        this.lineAngle = lineAngle;
    }
}
