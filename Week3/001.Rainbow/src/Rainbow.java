import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        Font font = new Font("Arial", Font.PLAIN, 100);
        AffineTransform tx = new AffineTransform();
        graphics.translate(200, 200);
        String word = "regenboog";
        char letter;
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.red);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.green);
        colors.add(Color.CYAN);
        colors.add(Color.BLUE);
        colors.add(Color.MAGENTA);
        colors.add(Color.pink);
        colors.add(Color.RED);
        graphics.rotate(calculateRad(-90));
        for (int i = 0; i < word.length(); i++) {
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), (word.charAt(i) + "")).getOutline();
            graphics.draw(shape);
            graphics.setColor(colors.get(i));
            graphics.fill(shape);
            graphics.setColor(Color.black);
            graphics.translate(50, 10);
            graphics.rotate(calculateRad(180/(word.length())));
        }

    }

    public double calculateRad(int degrees){
        return degrees/(180/Math.PI);
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
