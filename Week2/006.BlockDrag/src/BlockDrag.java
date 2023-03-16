import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    private Block red;
    private Block green;
    private Block blue;
    private Block black;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Block> pressedBlocks = new ArrayList<>();
    private Point2D mousePosition;
    private Point2D newMousePosition;
    private boolean mouseIsPressed = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        red = new Block(Color.red, new Point2D.Double(200, 200), 50);
        green = new Block(Color.green, new Point2D.Double(250, 200), 50);
        blue = new Block(Color.blue, new Point2D.Double(300, 200), 50);
        black = new Block(Color.black, new Point2D.Double(350, 200), 50);
        blocks.add(red);
        blocks.add(green);
        blocks.add(blue);
        blocks.add(black);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        for (Block block : blocks) {
            graphics.setColor(block.getColor());
            graphics.fill(block.getShape());
        }
        for (Block pressedBlock : pressedBlocks) {
            graphics.setColor(pressedBlock.getColor());
            graphics.fill(pressedBlock.getShape());
            double newX = newMousePosition.getX()-(Math.abs(pressedBlock.getPosition().getX()-mousePosition.getX()));
            double newY = newMousePosition.getY()-(Math.abs(pressedBlock.getPosition().getY()-mousePosition.getY()));
            pressedBlock.setPosition(new Point2D.Double(newX, newY));
        }
    }


    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        mousePosition = new Point2D.Double(e.getX(), e.getY());
        if (!mouseIsPressed)
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getShape().contains(mousePosition)) {
                if (!pressedBlocks.contains(blocks.get(i))) {
                    pressedBlocks.add(blocks.get(i));
                    mouseIsPressed = true;
                }

            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        pressedBlocks.clear();
        mouseIsPressed = false;
    }


    private void mouseDragged(MouseEvent e) {
        newMousePosition = new Point2D.Double(e.getX(), e.getY());
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        mousePosition = new Point2D.Double(e.getX(), e.getY());

    }

}
