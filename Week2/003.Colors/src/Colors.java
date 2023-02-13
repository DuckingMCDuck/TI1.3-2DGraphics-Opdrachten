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

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area rectangle = new Area(new Rectangle2D.Double(0, 0, 25, 25));
        graphics.translate(50, 200);
        Color color;

        for (int i = 0; i < 13; i++) {
            switch (i){
                case 0:
                    graphics.setColor(Color.black);
                    break;
                case 1:
                    graphics.setColor(Color.blue);
                    break;
                case 2:
                    graphics.setColor(Color.cyan);
                    break;
                case 3:
                    graphics.setColor(Color.darkGray);
                    break;
                case 4:
                    graphics.setColor(Color.gray);
                    break;
                case 5:
                    graphics.setColor(Color.green);
                    break;
                case 6:
                    graphics.setColor(Color.lightGray);
                    break;
                case 7:
                    graphics.setColor(Color.magenta);
                    break;
                case 8:
                    graphics.setColor(Color.orange);
                    break;
                case 9:
                    graphics.setColor(Color.pink);
                    break;
                case 10:
                    graphics.setColor(Color.red);
                    break;
                case 11:
                    graphics.setColor(Color.white);
                    break;
                case 12:
                    graphics.setColor(Color.yellow);
                    break;
            }
            graphics.translate(30, 0);
            graphics.draw(rectangle);
            graphics.fill(rectangle);
        }
    }


    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
