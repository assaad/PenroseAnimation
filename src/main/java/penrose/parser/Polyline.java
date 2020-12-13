package penrose.parser;

import java.util.ArrayList;

public class Polyline extends Shape {
    public ArrayList<Point> points = new ArrayList<Point>();
    public boolean closed = false;

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String toSvg() {
        StringBuilder line = new StringBuilder("        <!-- Polyline -->\n");
        line.append("        <path d=\"M-");
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (i != 0) {
                line.append(" L");
            }
            line.append(p.x).append(",").append(p.y);
        }
        if (closed) {
            line.append(" Z");
        }
        line.append(this.postProcess());
        return line.toString();
    }
}
