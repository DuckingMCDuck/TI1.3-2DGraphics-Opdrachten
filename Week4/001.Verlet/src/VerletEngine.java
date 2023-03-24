
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class VerletEngine extends Application {

    private ResizableCanvas canvas;
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Constraint> constraints = new ArrayList<>();
    private PositionConstraint mouseConstraint = new PositionConstraint(null);
    private ArrayList<DistanceConstraint> distanceConstraints = new ArrayList<>();
    private boolean isDragged = false;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
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

        // Mouse Events

        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnMouseClicked(e -> mouseClicked(e));
        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Verlet Engine");
        stage.show();
        draw(g2d);
    }

    public void init() {
        int length = 5;
        int width = 5;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                particles.add(new Particle(new Point2D.Double(50 + (50 * j), 50 + (50 * i))));
            }
        }

        for (int i = 0; i < width; i++) {
            try {
                for (int j = 0; j < length; j++) {
                    if (j < length-1) {
                        constraints.add(new DistanceConstraint(particles.get(j + (length * i)), particles.get((j + (length * i)) + 1)));
                        distanceConstraints.add(new DistanceConstraint(particles.get(j + (length * i)), particles.get((j + (length * i)) + 1)));
                    }
                    if (i > 0){
                        constraints.add(new DistanceConstraint(particles.get(j + (length * i) -length), particles.get((j + (length * i)))));
                        distanceConstraints.add(new DistanceConstraint(particles.get(j + (length * i) -length), particles.get((j + (length * i)))));

                    }
                }

            } catch (Exception e){
            }

        }

        constraints.add(new PositionConstraint(particles.get(0)));
        constraints.add(new PositionConstraint(particles.get(length-1)));
        constraints.add(mouseConstraint);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.red);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (DistanceConstraint distanceConstraint : distanceConstraints) {
            try {
                int stress = (int)(10 * distanceConstraint.getStress());
                if (stress < 0){
                    stress = -stress;
                } else if (stress > 255){
                    stress = 255;
                }
                graphics.setColor(new Color(stress, stress, stress));
                distanceConstraint.draw(graphics);
            } catch (Exception e){

            }

        }
        for (Particle p : particles) {
            p.draw(graphics);
        }

    }

    private void update(double deltaTime) {
        for (Particle p : particles) {
            p.update((int) canvas.getWidth(), (int) canvas.getHeight());
        }

        for (Constraint c : constraints) {
            c.satisfy();
        }
        for (DistanceConstraint distanceConstraint : distanceConstraints) {
            distanceConstraint.satisfy();
        }
    }

    private void mouseClicked(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        Particle newParticle = new Particle(mousePosition);

        if (e.getButton() == MouseButton.SECONDARY && e.isControlDown()) {
            particles.add(newParticle);
            constraints.add(new DistanceConstraint(newParticle, nearest, 100));
            distanceConstraints.add(new DistanceConstraint(newParticle, nearest, 100));
            ArrayList<Particle> sorted = new ArrayList<>();
            sorted.addAll(particles);

            //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
            Collections.sort(sorted, new Comparator<Particle>() {
                @Override
                public int compare(Particle o1, Particle o2) {
                    return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                }
            });
            constraints.add(new DistanceConstraint(newParticle, sorted.get(2), 100));
            distanceConstraints.add(new DistanceConstraint(newParticle, sorted.get(2), 100));
        } else if (e.getButton() == MouseButton.SECONDARY && e.isShiftDown()) {
            ArrayList<Particle> sorted = new ArrayList<>();
            sorted.addAll(particles);

            //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
            Collections.sort(sorted, new Comparator<Particle>() {
                @Override
                public int compare(Particle o1, Particle o2) {
                    return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                }
            });
            constraints.add(new DistanceConstraint(sorted.get(0), sorted.get(1)));
            distanceConstraints.add(new DistanceConstraint(sorted.get(0), sorted.get(1)));

        } else if (e.getButton() == MouseButton.SECONDARY) {
            particles.add(newParticle);
            constraints.add(new DistanceConstraint(newParticle, nearest));
            distanceConstraints.add(new DistanceConstraint(newParticle, nearest));
            ArrayList<Particle> sorted = new ArrayList<>();
            sorted.addAll(particles);

            //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
            Collections.sort(sorted, new Comparator<Particle>() {
                @Override
                public int compare(Particle o1, Particle o2) {
                    return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                }
            });

            constraints.add(new DistanceConstraint(newParticle, sorted.get(2)));
            distanceConstraints.add(new DistanceConstraint(newParticle, sorted.get(2)));

        } else if (e.getButton() == MouseButton.PRIMARY && e.isControlDown()) {
            particles.add(newParticle);
            constraints.add(new PositionConstraint(newParticle));
        } else if (e.getButton() == MouseButton.PRIMARY && !isDragged) {
            particles.add(newParticle);
            constraints.add(new DistanceConstraint(newParticle, nearest));
            distanceConstraints.add(new DistanceConstraint(newParticle, nearest));
        } else if (e.getButton() == MouseButton.MIDDLE) {
            // Reset
            particles.clear();
            constraints.clear();
            distanceConstraints.clear();
            init();
        }

    }

    private Particle getNearest(Point2D point) {
        Particle nearest = particles.get(0);
        for (Particle p : particles) {
            if (p.getPosition().distance(point) < nearest.getPosition().distance(point)) {
                nearest = p;
            }
        }
        return nearest;
    }

    private void mousePressed(MouseEvent e) {
        isDragged = false;
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        if (nearest.getPosition().distance(mousePosition) < 10) {
            mouseConstraint.setParticle(nearest);
        }
    }

    private void mouseReleased(MouseEvent e) {
        mouseConstraint.setParticle(null);
    }

    private void mouseDragged(MouseEvent e) {
        mouseConstraint.setFixedPosition(new Point2D.Double(e.getX(), e.getY()));
        isDragged = true;
    }

    public static void main(String[] args) {
        launch(VerletEngine.class);
    }

}
