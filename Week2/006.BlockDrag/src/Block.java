import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Block{
    private Color color;
    private Point2D position;
    private int scale;

    public Block(Color color, Point2D position, int scale) {
        this.color = color;
        this.position = position;
        this.scale = scale;
    }

    public Shape getShape(){
        return new Rectangle2D.Double(position.getX(), position.getY(), scale, scale);
    }
    public Color getColor(){
        return color;
    }
    public Point2D getPosition(){
        return position;
    }
    public void setPosition(Point2D position){
        this.position = position;
    }

    @Override
    public String toString() {
        return "Block{" +
                "color=" + color +
                ", position=" + position +
                ", scale=" + scale +
                '}';
    }
}
