import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private float i;
    private BufferedImage image1;
    private BufferedImage image2;
    private enum action {UP, DOWN}
    private action state = action.UP;
    
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        try {
            image1 = ImageIO.read(this.getClass().getResource("/img.png"));
            image2 = ImageIO.read(this.getClass().getResource("/img_1.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();
        
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.setPaint(new TexturePaint(image2, new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight())));
        AffineTransform tx1 = new AffineTransform();
        AffineTransform tx2 = new AffineTransform();
        tx1.scale(0.8, 0.6);
        tx2.scale(2.9, 2.2);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, i));
        graphics.drawImage(image1, tx1, null);

        if (state == action.UP){
            i += 0.01f;
            if (i >= 0.99){
                state = action.DOWN;
                i = 1;
            }
        }
        if (state == action.DOWN){
            i -= 0.01f;
            if (i <= 0.01){
                state = action.UP;
                i = 0;
            }
        }


        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));


        System.out.println(i);
    }
    

    public void update(double deltaTime) {

    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
