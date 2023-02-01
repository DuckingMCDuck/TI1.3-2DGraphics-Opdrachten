import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    private Canvas canvas = new Canvas(1000, 1000);
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1000, 1000);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        graphics.scale( 0.5, -0.5);

        graphics.setColor(Color.red);
        graphics.drawLine(-1000,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,-1000,0,1000);
        graphics.setColor(Color.black);

        double resolution = 0.01;
        double scale = 50.0;
        double lastY = 1000;
        double n = 1;

        for(double r = 0; r < 100; r += resolution)
        {
            double Ø = n * r;
            double x = r * Math.cos(Ø);
            double y = r * Math.sin(Ø);
            double lastØ = n * (r-resolution);
            double lastX = (r-resolution) * Math.cos(lastØ);

            graphics.draw(new Line2D.Double(x*scale, y*scale, lastX*scale, lastY*scale));
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
