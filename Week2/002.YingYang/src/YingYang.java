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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area mainCircle = new Area(new Ellipse2D.Double(-25, 0, 100, 100));
        Area bottomCircle = new Area(new Ellipse2D.Double(0, 50, 50, 50));
        Area topCircle = new Area(new Ellipse2D.Double(0, 0, 50, 50));
        Area smallBottomCircle = new Area(new Ellipse2D.Double(15, 65, 20, 20));
        Area smallTopCircle = new Area(new Ellipse2D.Double(15, 15, 20, 20));
        Area square = new Area(new Rectangle2D.Double(25, 0, 100, 100));

        Area area = new Area(mainCircle);
        area.subtract(square);
        area.subtract(topCircle);
        area.add(bottomCircle);
        area.add(smallTopCircle);
        area.subtract(smallBottomCircle);

        graphics.translate(200, 200);
        graphics.draw(mainCircle);
        graphics.setColor(Color.black);
        graphics.draw(area);
        graphics.fill(area);
    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
