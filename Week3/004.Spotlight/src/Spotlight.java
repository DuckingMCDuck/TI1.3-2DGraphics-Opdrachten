import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private Stage stage;
    private double xLocation = 0;
    private double yLocation = 0;


    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }

    private void mouseDragged(MouseEvent e) {
        xLocation = e.getX() - 100;
        yLocation = e.getY() - 100;
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        Shape shape = new Ellipse2D.Double(xLocation, yLocation, 200, 200);
        graphics.draw(shape);
        graphics.clip(shape);


        Random r = new Random();
        for(int i = 0; i < 1000; i++) {
            graphics.setPaint(Color.getHSBColor(r.nextFloat(),1,1));
            graphics.drawLine((int)(r.nextInt() % stage.getWidth()), (int)(r.nextInt() % stage.getHeight()), (int)(r.nextInt() % stage.getWidth()), (int)(r.nextInt() % stage.getHeight()));
        }
        graphics.setClip(null);
    }

    public void init()
    {

    }

    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
