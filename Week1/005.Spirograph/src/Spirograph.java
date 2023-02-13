import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private boolean check = true;
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);

        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        
        topBar.getChildren().add(v1 = new TextField("10"));
        topBar.getChildren().add(v2 = new TextField("10"));
        topBar.getChildren().add(v3 = new TextField("10"));
        topBar.getChildren().add(v4 = new TextField("10"));
                
        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming

        if (check){
            graphics.translate(650, 350);
            check = false;
        }
        graphics.setColor(Color.white);
        graphics.fillRect(-650, -350, 1920, 1920);
        graphics.setColor(Color.black);

        double a = Double.parseDouble(v1.getText());
        double b = Double.parseDouble(v2.getText());
        double c = Double.parseDouble(v3.getText());
        double d = Double.parseDouble(v4.getText());
        double increment = 0.001;
        for (double i = 0; i < Math.PI; i+= increment) {
            int x = (int) (a * Math.cos(b * i) + c * Math.cos(d * i));
            int y = (int) (a * Math.sin(b * i) + c * Math.sin(d * i));
            i+= increment;
            int x2 = (int) (a * Math.cos(b * i) + c * Math.cos(d * i));
            int y2 = (int) (a * Math.sin(b * i) + c * Math.sin(d * i));
            i-= increment;
            graphics.drawLine(x, y, x2, y2);
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
