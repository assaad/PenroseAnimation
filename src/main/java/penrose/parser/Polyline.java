package penrose.parser;

import java.util.ArrayList;

public class Polyline extends Shape{
    public ArrayList<Point> points = new ArrayList<Point>();
    public boolean closed = false;

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Polyline -->\n";
        return line;
    }
}
