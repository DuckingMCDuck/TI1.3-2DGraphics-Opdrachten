import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    private ArrayList<Point> points = new ArrayList<>();
    private Point lastPoint;
    private Stage stage;
    private int frame;
    private ArrayList<LineSet> allLines = new ArrayList<>();
    private ArrayList<Line2D> currentLines = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        point1 = new Point(new Point2D.Double(50, 50), -0.32045, -0.76528);
        points.add(point1);
        point2 = new Point(new Point2D.Double(100, 50), 0.65128, -0.7234);
        points.add(point2);
        point3 = new Point(new Point2D.Double(50, 100), -0.827364, 0.23475);
        points.add(point3);
        point4 = new Point(new Point2D.Double(100, 100), 0.872361, 0.8243);
        points.add(point4);
        lastPoint = point1;

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
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        currentLines = new ArrayList<>();
            for (Point point : points) {
                graphics.setTransform(new AffineTransform());
                if (point.getPoint2D().getX() >= canvas.getWidth() || point.getPoint2D().getX() <= 0){
                    point.setDirectionX(point.getDirectionX()*-1);
                }
                if (point.getPoint2D().getY() >= canvas.getHeight() || point.getPoint2D().getY() <= 0){
                    point.setDirectionY(point.getDirectionY()*-1);
                }

                graphics.setPaint(Color.white);
                point.setPoint2D(new Point2D.Double(point.getDirectionX() + point.getPoint2D().getX(), point.getDirectionY() + point.getPoint2D().getY()));
                Line2D line = new Line2D.Double((int) point.getPoint2D().getX(), (int) point.getPoint2D().getY(), (int) lastPoint.getPoint2D().getX(), (int) lastPoint.getPoint2D().getY());
                graphics.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
                currentLines.add(line);
                lastPoint = point;
                for (LineSet lineSet : allLines) {
                    for (Line2D currentLine : lineSet.getLines()) {
                        graphics.drawLine((int)currentLine.getX1(), (int)currentLine.getY1(), (int)currentLine.getX2(), (int)currentLine.getY2());
                    }
                }
            }
            frame++;
            if (frame > 20) {
                allLines.add(new LineSet(currentLines));
                try {
                    if (allLines.size() > 7){
                        allLines.remove(0);
                    }
                    System.out.println(allLines.size());
                } catch (Exception e) {
                    System.out.println(allLines.size());
                }
                frame = 0;
            }
    }

    public void init()
    {

    }

    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Screensaver.class);
    }

}
