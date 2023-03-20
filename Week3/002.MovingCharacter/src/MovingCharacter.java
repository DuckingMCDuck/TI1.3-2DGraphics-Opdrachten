import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage image;
    private BufferedImage[] tiles;
    private FXGraphics2D g2d;
    private int frame = 0;
    private int i = 0;
    private enum action {Forward, Backward, Jumping}
    private action state = action.Forward;
    private action lastState = state;
    private AffineTransform tx = new AffineTransform();
    private Character character = new Character(new Point2D.Double(200, 200));

    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        canvas.setOnMousePressed(e -> mousePressed(e));
        tx.translate(100, 100);

        try {
            image = ImageIO.read(getClass().getResource("sprite.png"));
            tiles = new BufferedImage[65];
            for(int i = 0; i < 65; i++)
                tiles[i] = image.getSubimage(64 * (i%8), 64 * (i/8), 64, 64);
        } catch (IOException e){
            e.printStackTrace();
        }

        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);

    }

    private void mousePressed(MouseEvent e) {
        if (state != action.Jumping) {
            lastState = state;
        }
            state = action.Jumping;
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(tx);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.drawImage(tiles[frame], tx,null);
        System.out.println(canvas.getWidth() + " " + tx.getTranslateX() + " " + tx.getTranslateY());
        if (state == action.Forward){
            tx.translate(0, 100-tx.getTranslateY());
            tx.translate(1, 0);

            if (tx.getTranslateX() >= 300) {

                state = action.Backward;
            }
        }
        if (state == action.Backward){
            tx.translate(0, 100-tx.getTranslateY());
            tx.translate(-1, 0);
            if (tx.getTranslateX() <= 0){
                state = action.Forward;
            }
        }
        if (state == action.Jumping){
            if (lastState == action.Forward) {
                if (frame <= 45) {
                    tx.translate(1, -1);
                } else {
                    tx.translate(1, 1);
                }
            }
            if (lastState == action.Backward) {
                if (frame <= 45) {
                    tx.translate(-1, -1);
                } else {
                    tx.translate(-1, 1);
                }
            }
        }



    }


    public void update(double deltaTime)
    {
        int startframe = 0;
        int endframe = 0;
        int speed = 0;
        i++;
        if (state == action.Forward || state == action.Backward) {
            startframe = 5;
            endframe = 12;
            speed = 5;
        }
        if (state == action.Jumping) {
            startframe = 43;
            endframe = 49;
            speed = 5;
            if (frame == endframe-1){
                state = lastState;
            }
        }
        if (frame < startframe){
            frame = startframe;
        }
        if (frame < endframe) {
            if (i == speed) {
                frame++;
                i = 0;
            }
        }
        if (frame > endframe-1) {
            frame = startframe;
        }

    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
