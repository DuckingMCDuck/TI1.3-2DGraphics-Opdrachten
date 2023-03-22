import java.awt.geom.Line2D;
import java.util.ArrayList;

public class LineSet {
    ArrayList<Line2D> lines;

    public LineSet(ArrayList<Line2D> lines) {
        this.lines = lines;
    }

    public ArrayList<Line2D> getLines() {
        return lines;
    }

}
