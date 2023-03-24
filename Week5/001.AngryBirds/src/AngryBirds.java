
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Body birb = new Body();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);

    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        birb = new Body();
        birb.addFixture(Geometry.createCircle(0.15));
        birb.getTransform().setTranslation(-5,-0.2);
        birb.setMass(MassType.NORMAL);
        birb.getFixture(0).setRestitution(0.75);
        world.addBody(birb);
        gameObjects.add(new GameObject("img.png", birb, new Vector2(0,0), 0.05));

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(20, 1));
        floor.getTransform().setTranslation(0, -1);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);
        gameObjects.add(new GameObject("", floor, new Vector2(0,0), 1));

        Body wall1 = new Body();
        wall1.addFixture(Geometry.createRectangle(0.15, 10));
        wall1.getTransform().setTranslation(10,5.5);
        wall1.setMass(MassType.INFINITE);
        world.addBody(wall1);
        gameObjects.add(new GameObject("", wall1, new Vector2(0,0), 1));

        Body wall2 = new Body();
        wall2.addFixture(Geometry.createRectangle(0.15, 10));
        wall2.getTransform().setTranslation(-10,5.5);
        wall2.setMass(MassType.INFINITE);
        world.addBody(wall2);
        gameObjects.add(new GameObject("", wall2, new Vector2(0,0), 1));

        Body slingshot = new Body();
        slingshot.getTransform().setTranslation(-5,0);
        slingshot.setMass(MassType.INFINITE);
        world.addBody(slingshot);
        gameObjects.add(new GameObject("img_1.png", slingshot, new Vector2(0,0), 0.3));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);

    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
