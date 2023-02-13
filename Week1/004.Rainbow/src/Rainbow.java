import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(640, 480);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Hello Java 2D");
        primaryStage.show();

    }

    public void draw(FXGraphics2D g) {
        int radiusBinnen = 100;
        int radiusBuiten = 150;
        int positieX = 250;
        int positieY = 250;
        int hoek = 180;
        int x1;
        int y1;
        int x2;
        int y2;
        for(double i = -(Math.PI/180)*hoek; i < 0; i+= 0.001) {
            x1 = (int)Math.round(radiusBinnen * Math.cos(i)) + positieX;
            y1 = (int)Math.round(radiusBinnen * Math.sin(i)) + positieY;
            x2 = (int)Math.round(radiusBuiten * Math.cos(i)) + positieX;
            y2 = (int)Math.round(radiusBuiten * Math.sin(i)) + positieY;
            g.setColor(Color.getHSBColor((float)-i/3, 1, 1));
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
