import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.swing.text.Position;

public class Mirror extends Application {
    ResizableCanvas canvas;
    Shape shape = new Rectangle2D.Double(-100, 75, 50, 50);
    Point2D center = new Point2D.Double(shape.getBounds().x / 2, shape.getBounds().y / 2);
    float rotation = 0.6f;
    float scale = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.drawLine(-200, 0, 200, 0);
        graphics.drawLine(0, -200, 0, 200);
        double resolutie = 10;
        double y;
        double xLast = -100;
        double yLast = 2.5 * xLast;


        for (double x = -100; x < 100; x += resolutie) {
            y = 2.5 * x;
            graphics.draw(new Line2D.Double(x, y, xLast, yLast));
            xLast = x;
            yLast = y;
        }
        graphics.draw(shape);
        graphics.draw(getTransformedShape());

    }

    public Shape getTransformedShape() {
        return getTransform().createTransformedShape(shape);
    }

    public AffineTransform getTransform() {

        double k = 2.5;
        AffineTransform tx = new AffineTransform();
        double n1 = (2 / (1 + Math.pow(k, 2))) - 1;
        double n2 = (2 * k) / (1 + Math.pow(k, 2));
        double n3 = (2 * k) / (1 + Math.pow(k, 2));
        double n4 = ((2 * Math.pow(k, 2)) / (1 + Math.pow(k, 2))) - 1;
        tx.concatenate(new AffineTransform(n1, n2, n3, n4, 0, 0));
        tx.scale(scale, scale);
        return tx;
    }


    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
