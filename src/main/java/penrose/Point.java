package penrose;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Point {
    private int x;
    private int y;
    private ArrayList<Line> connectedTo;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.connectedTo = new ArrayList<Line>();
    }

    private static void addPoint(Point a, Point b, Color c) {
        for (int i = 0; i < a.connectedTo.size(); i++) {
            if (a.connectedTo.get(i).to == b) {
                return;
            }
        }
        a.connectedTo.add(new Line(b, c));
    }

    public static void connect(Point a, Point b, Color c) {
        addPoint(a, b, c);
        addPoint(b, a, c);
    }

    public static Point from(Point current, Point next, int i, int steps) {
        int x = current.x + (i + 1) * (next.x - current.x) / steps;
        int y = current.y + (i + 1) * (next.y - current.y) / steps;
        return new Point(x, y);
    }

    public static long getPos(int x, int y) {
        return y * 1000000L + x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Line randomWalk(Point from, Random random) {
        ArrayList<Line> temp = new ArrayList<Line>();
        Line self = null;
        for (int i = 0; i < connectedTo.size(); i++) {
            if (connectedTo.get(i).to != from) {
                temp.add(connectedTo.get(i));
            } else {
                self = connectedTo.get(i);
            }
        }
        if (temp.size() == 0) {
            return self;
        } else {
            return temp.get(random.nextInt(temp.size()));
        }
    }

    public long getPos() {
        return getPos(this.x, this.y);
    }

    public String print() {
        return "" + x + "," + y;
    }

}
