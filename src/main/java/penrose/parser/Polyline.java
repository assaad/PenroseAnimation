package penrose.parser;

import java.util.ArrayList;

public class Polyline extends Shape {
    public static String header = "Type,Closed, points";
    public ArrayList<Point> points = new ArrayList<Point>();
    public boolean closed = false;

    @Override
    public String toCsv() {
        StringBuilder line = new StringBuilder("Polyline,");
        if (closed) {
            line.append("true,");
        } else {
            line.append("false,");
        }
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            line.append(p.x).append(",").append(p.y);
            if (i < points.size() - 1) {
                line.append(",");
            }
        }
        return line.toString();
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
