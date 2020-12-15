package penrose;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Graph {
    HashMap<Long, Point> points;
    ArrayList<Point> listOfPoints;
    ArrayList<Color> listOfColors;
    int xMax;
    int yMax;

    HashMap<Integer, Integer> counters = new HashMap<Integer, Integer>();

    public Graph() {
        this.points = new HashMap<Long, Point>();
        this.listOfPoints = new ArrayList<Point>();
        this.xMax = 0;
        this.yMax = 0;
    }

    public static Graph parseCsv(String path, int width, int height, int distance) {
        Graph graph = new Graph();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        boolean header = true;
        double xMax = 0;
        double yMax = 0;

        double ratio = 0;

        double bestWidth = 0;
        double bestHeight = 0;

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    ratio = Double.parseDouble(line);
                    double temp = height * 1.0 / width;
                    if (ratio > temp) {
                        bestHeight = height;
                        bestWidth = (bestHeight / ratio);
                    } else {
                        bestWidth = width;
                        bestHeight = (ratio * bestWidth);
                    }
                }

                String[] positions = line.split(cvsSplitBy);
                if (positions.length == 5) {
                    int x1 = (int) (Double.parseDouble(positions[0]) * bestWidth);
                    int y1 = (int) (Double.parseDouble(positions[1]) * bestHeight);
                    int x2 = (int) (Double.parseDouble(positions[2]) * bestWidth);
                    int y2 = (int) (Double.parseDouble(positions[3]) * bestHeight);
                    graph.connect(x1, y1, x2, y2, Color.decode(positions[4]), distance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (Map.Entry<Integer, Integer> e : graph.counters.entrySet()) {
            System.out.println("Dist, " + e.getKey() + "," + e.getValue());
        }

        return graph;
    }


    public static Graph parseArray(ArrayList<double[]> values, ArrayList<String> strokes, double ratio, int width, int height, int distance) {
        Graph graph = new Graph();

        double bestWidth;
        double bestHeight;

        double temp = height * 1.0 / width;
        if (ratio > temp) {
            bestHeight = height;
            bestWidth = (bestHeight / ratio);
        } else {
            bestWidth = width;
            bestHeight = (ratio * bestWidth);
        }

        for (int i = 0; i < values.size(); i++) {
            double[] positions = values.get(i);
            int x1 = (int) (positions[0] * bestWidth);
            int y1 = (int) (positions[1] * bestHeight);
            int x2 = (int) (positions[2] * bestWidth);
            int y2 = (int) (positions[3] * bestHeight);
            if(strokes.get(i).equals("lime")){
                strokes.set(i,"#00FF00");
            }
            graph.connect(x1, y1, x2, y2, Color.decode(strokes.get(i)), distance);
        }
//        for (Map.Entry<Integer, Integer> e : graph.counters.entrySet()) {
//            System.out.println("Dist, " + e.getKey() + "," + e.getValue());
//        }
        return graph;
    }


    public void connect(int x1, int y1, int x2, int y2, Color c, int distance) {
        long l1 = Point.getPos(x1, y1);
        long l2 = Point.getPos(x2, y2);

        Point p1 = getPoint(x1, y1, distance);
        Point p2 = getPoint(x2, y2, distance);
        if (p1 == null) {
            p1 = new Point(x1, y1);
            points.put(l1, p1);
            listOfPoints.add(p1);
        }

        if (p2 == null) {
            p2 = new Point(x2, y2);
            points.put(l2, p2);
            listOfPoints.add(p2);
        }
        Point.connect(p1, p2, c);

        if (x1 > xMax) {
            xMax = x1;
        }
        if (x2 > xMax) {
            xMax = x2;
        }

        if (y1 > yMax) {
            yMax = y1;
        }
        if (y2 > yMax) {
            yMax = y2;
        }
    }

    public Point getPoint(int x, int y, int distance) {
        int dist = Integer.MAX_VALUE;
        Point pFinal = null;

        for (int i = 0; i < listOfPoints.size(); i++) {
            Point p = listOfPoints.get(i);
            int dist2 = (p.getX() - x) * (p.getX() - x) + ((p.getY() - y) * (p.getY() - y));
            if (dist2 < dist) {
                dist = dist2;
                pFinal = p;
            }
        }

        Integer c = counters.get(dist);
        if (c == null) {
            counters.put(dist, 1);
        } else {
            counters.put(dist, 1 + c);
        }

        if (dist < distance) {
            return pFinal;
        } else {
            return null;
        }
    }

    public Point getRandomStart(Random random) {
        return listOfPoints.get(random.nextInt(listOfPoints.size()));
    }


}
