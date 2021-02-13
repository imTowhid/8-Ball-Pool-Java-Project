package BallandCue;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;


public class Ball {
    public Sphere sphere = new Sphere(10, 40);
    private double angle;
    private double time;
    private double v, vx, vy;
    private double a=2, ax, ay;
    int idofBall;

    Ball(Group root, double x, double y, String imageName, Color color){
        sphere.setLayoutX(x);
        sphere.setLayoutY(y);
        sphere.setRadius(10);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setSpread(40);
        dropShadow.setOffsetX(10);
        dropShadow.setOffsetY(10);
        sphere.setEffect(dropShadow);

        Image image = new Image(imageName);

        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setSpecularColor(Color.WHITE);
        if(color == Color.BLACK){
            phongMaterial.setDiffuseColor(color);
        }
        phongMaterial.setDiffuseMap(image);
        sphere.setMaterial(phongMaterial);

        root.getChildren().add(sphere);

        double vx=0;
        double vy=0;
        double angle=0;
        double time=0;
        double a=2;

    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public int getIdofBall() {
        return idofBall;
    }

    public void setIdofBall(int idofBall) {
        this.idofBall = idofBall;
    }
}
