import java.awt.geom.Point2D;
public class Point {
    private Point2D point2D;
    private double directionX;
    private double directionY;

    public Point(Point2D point2D, double directionX, double directionY) {
        this.point2D = point2D;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public double getDirectionX() {
        return directionX;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }
}
