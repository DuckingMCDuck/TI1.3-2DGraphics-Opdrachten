import java.awt.geom.Point2D;

public class Character {
    private Point2D location;

    public Character(Point2D location) {
        this.location = location;
    }

    public Point2D getLocation(){
        return location;
    }
    public void setLocation(Point2D location){
        this.location = location;
    }
}
