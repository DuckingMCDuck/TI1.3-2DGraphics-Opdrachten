import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
// Omtrek
        graphics.draw(new Line2D.Double(500, 500, 500 ,300));
        graphics.draw(new Line2D.Double(500, 300, 650 ,100));
        graphics.draw(new Line2D.Double(650, 100, 800 ,300));
        graphics.draw(new Line2D.Double(800, 300, 800 ,500));
        graphics.draw(new Line2D.Double(800, 500, 500 ,500));
// Deur
        graphics.draw(new Line2D.Double(550, 500, 550 ,375));
        graphics.draw(new Line2D.Double(550, 375, 615 ,375));
        graphics.draw(new Line2D.Double(615, 375, 615 ,500));
// Raam
        graphics.draw(new Line2D.Double(650, 425, 650 ,350));
        graphics.draw(new Line2D.Double(650, 350, 775 ,350));
        graphics.draw(new Line2D.Double(775, 350, 775 ,425));
        graphics.draw(new Line2D.Double(775, 425, 650 ,425));





    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
