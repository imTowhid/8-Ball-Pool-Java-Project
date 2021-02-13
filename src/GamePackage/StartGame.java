package GamePackage;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class StartGame {
  public   Button gameplay;
    public Button gamepause;
    Button start;
   public Text text;
    TextField name;
    TextField id;
    String username;
    String userid;
    Image backg;
    Button Player = new Button();

    public  StartGame(Group root){
        backg=new Image("Extras\\open.jpg");
        ImageView view=new ImageView(backg);
        view.setFitHeight(655);
        view.setFitWidth(1100);

        gameplay =new Button();
        gameplay.setText("Play");
        gameplay.setLayoutX(950+50);
        gameplay.setLayoutY(278);
        gameplay.setPrefWidth(92);
        gameplay.setPrefHeight(31);
        gameplay.getStylesheets().add(getClass().getResource("btnDesing.css").toExternalForm());

        gamepause =new Button();
        gamepause.setText("Pause");
        gamepause.setLayoutX(950+50);
        gamepause.setLayoutY(357);
        gamepause.setPrefWidth(92);
        gamepause.setPrefHeight(31);
        gamepause.setMouseTransparent(true);
        gamepause.getStylesheets().add(getClass().getResource("btnDesing.css").toExternalForm());

        text=new Text();
        text.setLayoutX(470);
        text.setLayoutY(70);
        text.setFont(Font.font("Calibri",FontWeight.BOLD,12));
        text.setText("Press 'Play' button\n\tto play");

        start=new Button("Start");
        start.setLayoutX(771+50);
        start.setLayoutY(416);
        start.setFont(Font.font(25));
        start.setPrefHeight(50);
        start.setPrefWidth(159);
        start.getStylesheets().add(getClass().getResource("btnDesing.css").toExternalForm());

        name=new TextField();
        name.setFont(Font.font(18));
        name.promptTextProperty().setValue("User Name");
        name.setLayoutX(705+50);
        name.setLayoutY(270);
        name.setPrefHeight(39);
        name.setPrefWidth(291);

        id=new TextField();
        id.promptTextProperty().setValue("Enter Id");
        id.setLayoutX(705+50);
        id.setLayoutY(337);
        id.setPrefWidth(291);
        id.setPrefHeight(39);
        id.setFont(Font.font(18));

        root.getChildren().addAll(view,name,id,start);

        start.setOnMouseClicked(event -> {
            username=name.getText();
            userid=id.getText();
            Player.setText("Player: "+username);
            Player.setLayoutX(980);
            Player.setLayoutY(8);
            Player.setPrefWidth(110);
            Player.setPrefHeight(32);
            Paint value0 = Paint.valueOf("#033D6C");
            text.setFont(Font.font("Calibri",FontWeight.BOLD,12));
            Player.getStylesheets().add(getClass().getResource("btnDesing.css").toExternalForm());

            root.getChildren().removeAll(name,id,start,view);
            root.getChildren().addAll(gameplay,gamepause, text, Player);
        });
    }

    public void  start(Timeline game){
        gameplay.setOnMouseClicked(event -> {
            game.play();
        });
        gamepause.setOnMouseClicked(event -> {
            game.pause();
        });
    }

    public void setText(String tex){
        text.setText(tex);
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
