package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.GREEN);

        Rectangle box = new Rectangle(width * 0.15, height * 0.2, width * 0.7, height * 0.6);
        box.setFill(Color.ORANGE);

        Rectangle display = new Rectangle(box.getX() * 1.25, box.getY() * 1.25, height * 3/5, height/2);
        display.setFill(Color.GREY);
        display.setArcWidth(arc);
        display.setArcHeight(arc);


        Line l_left = new Line(width/2 - width * 0.1, box.getY() - width * 0.1, width/2, box.getY());
        l_left.setStroke(Color.BLACK);
        Line l_right = new Line(width/2, box.getY(), width/2 + width * 0.1, box.getY() - width * 0.1);
        l_right.setStroke(Color.BLACK);

        root.getChildren().add(box);
        root.getChildren().add(display);
        root.getChildren().add(l_left);
        root.getChildren().add(l_right);

        double button_x = (display.getX() - box.getX() + display.getWidth() - box.getWidth()) / 2 + box.getX() + box.getWidth();
        System.out.println(button_x);
        System.out.println(box.toString());
        System.out.println(display.toString());

        double button_y = height - box.getY() - (display.getY() - box.getY()) - radius;
        for(int i = 0; i < 3; i++){
            root.getChildren().add(new Circle(button_x, button_y - radius * i * 3, radius));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
