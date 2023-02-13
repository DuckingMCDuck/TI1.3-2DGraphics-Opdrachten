import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.CycleMethod;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import static javafx.scene.paint.CycleMethod.NO_CYCLE;
import static javafx.scene.paint.CycleMethod.REPEAT;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D center;
    private Point2D focus;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        center = new Point2D.Double(canvas.getWidth() / 2, canvas.getHeight() / 2);
        focus = new Point2D.Double(canvas.getWidth() / 2, canvas.getHeight() / 2);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        canvas.setOnMouseDragged(e -> {
            focus.setLocation(e.getX(), e.getY());
//            center = new Point2D.Double(e.getX(), e.getY());
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        float radius = 50;

        Color[] colors = new Color[]{Color.red, Color.green, Color.blue, Color.magenta, Color.CYAN};

        float[] fractions = new float[]{0.2f, 0.4f, 0.6f, 0.8f, 1f};

        RadialGradientPaint paint = new RadialGradientPaint(center, radius, focus, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
        graphics.setPaint(paint);
        Area rectangle = new Area(new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight()));
        graphics.fill(rectangle);
    }


    public static void main(String[] args) {
        launch(GradientPaintExercise.class);
    }

}
